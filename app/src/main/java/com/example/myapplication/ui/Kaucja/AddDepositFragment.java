package com.example.myapplication.ui.Kaucja;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;

/**
 * Fragment odpowiedzialny za dodawanie i edycję nowej kaucji (opakowania).
 */
public class AddDepositFragment extends Fragment {

    private EditText editPackagingType, editDepositValue, editBarcode;
    private Button btnSaveDeposit;
    private DashboardViewModel viewModel;

    private int depositId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_deposit, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        editPackagingType = view.findViewById(R.id.editPackagingType);
        editDepositValue = view.findViewById(R.id.editDepositValue);
        editBarcode = view.findViewById(R.id.editBarcode);
        btnSaveDeposit = view.findViewById(R.id.btnSaveDeposit);

        if (getArguments() != null) {
            depositId = getArguments().getInt("depositId", -1);
            if (depositId != -1) {
                editPackagingType.setText(getArguments().getString("packagingType"));
                editDepositValue.setText(String.valueOf(getArguments().getDouble("depositValue")));
                editBarcode.setText(getArguments().getString("barcode"));
            }
        }

        btnSaveDeposit.setOnClickListener(v -> saveDeposit());

        return view;
    }

    private void saveDeposit() {
        String packagingType = editPackagingType.getText().toString().trim();
        String depositValueStr = editDepositValue.getText().toString().trim();
        String barcode = editBarcode.getText().toString().trim();

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
}
