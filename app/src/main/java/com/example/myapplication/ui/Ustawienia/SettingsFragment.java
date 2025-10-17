package com.example.myapplication.ui.Ustawienia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class SettingsFragment extends Fragment {

    private SwitchCompat switchNotifications; // ← наш выключатель

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Подключаем layout
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Находим switch по ID
        switchNotifications = view.findViewById(R.id.switchNotifications);

        // Обработчик нажатия
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(getContext(), "Powiadomienia włączone", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Powiadomienia wyłączone", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
