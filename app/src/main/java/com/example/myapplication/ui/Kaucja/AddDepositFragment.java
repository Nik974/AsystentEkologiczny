package com.example.myapplication.ui.Kaucja;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAddDepositBinding;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

/**
 * Fragment odpowiedzialny za dodawanie i edycję nowej kaucji (opakowania).
 */
public class AddDepositFragment extends Fragment {

    private FragmentAddDepositBinding binding;
    private DashboardViewModel viewModel;

    private int depositId = -1;

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    startScanner();
                } else {
                    Toast.makeText(getContext(), "Brak uprawnień do użycia kamery", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    binding.editBarcode.setText(result.getContents());
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddDepositBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        if (getArguments() != null) {
            depositId = getArguments().getInt("depositId", -1);
            if (depositId != -1) {
                binding.editPackagingType.setText(getArguments().getString("packagingType"));
                binding.editDepositValue.setText(String.valueOf(getArguments().getDouble("depositValue")));
                binding.editBarcode.setText(getArguments().getString("barcode"));
            }
        }

        binding.btnSaveDeposit.setOnClickListener(v -> saveDeposit());
        binding.btnScanBarcode.setOnClickListener(v -> checkCameraPermissionAndStartScanner());

        return binding.getRoot();
    }

    private void checkCameraPermissionAndStartScanner() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startScanner();
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA);
        }
    }

    private void startScanner() {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
        options.setPrompt("Skanuj kod kreskowy");
        options.setCameraId(0);
        options.setBeepEnabled(true);
        options.setBarcodeImageEnabled(true);
        barcodeLauncher.launch(options);
    }

    private void saveDeposit() {
        String packagingType = binding.editPackagingType.getText().toString().trim();
        String depositValueStr = binding.editDepositValue.getText().toString().trim();
        String barcode = binding.editBarcode.getText().toString().trim();

        if (TextUtils.isEmpty(packagingType) || TextUtils.isEmpty(depositValueStr)) {
            Toast.makeText(getContext(), "Wypełnij typ opakowania i wartość kaucji!", Toast.LENGTH_SHORT).show();
            return;
        }

        double depositValue;
        try {
            depositValue = Double.parseDouble(depositValueStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Nieprawidłowy format wartości kaucji!", Toast.LENGTH_SHORT).show();
            return;
        }

        Deposit deposit = new Deposit(packagingType, depositValue, barcode);

        if (depositId == -1) {
            viewModel.addDeposit(deposit);
            Toast.makeText(getContext(), "Opakowanie zostało zapisane", Toast.LENGTH_SHORT).show();
        } else {
            deposit.setId(depositId);
            viewModel.updateDeposit(deposit);
            Toast.makeText(getContext(), "Opakowanie zaktualizowane", Toast.LENGTH_SHORT).show();
        }
        NavHostFragment.findNavController(this).navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
