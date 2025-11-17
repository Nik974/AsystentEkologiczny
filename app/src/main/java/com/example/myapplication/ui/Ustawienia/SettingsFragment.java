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

    private SwitchCompat switchNotifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        switchNotifications = view.findViewById(R.id.switchNotifications);


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
