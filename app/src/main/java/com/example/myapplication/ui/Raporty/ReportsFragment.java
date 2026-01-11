package com.example.myapplication.ui.Raporty;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotificationsBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ReportsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private ReportsViewModel reportsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnShowChart.setOnClickListener(v -> {
            NavHostFragment.findNavController(ReportsFragment.this)
                    .navigate(R.id.action_navigation_Raporty_to_chartFragment);
        });

        binding.btnSaveCsv.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                generateAndSaveCsv();
            } else {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            generateAndSaveCsv();
        } else {
            Toast.makeText(getContext(), "Brak uprawnień do zapisu pliku", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateAndSaveCsv() {
        String csvData = CsvUtils.generateCsvData(getContext());
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloadsDir, "raport.csv");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(csvData.getBytes());
            Toast.makeText(getContext(), "Raport zapisany w pliku raport.csv", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Błąd podczas zapisu pliku", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}