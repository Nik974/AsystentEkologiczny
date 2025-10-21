package com.example.myapplication.ui.Ustawienia;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> infoText = new MutableLiveData<>();

    public SettingsViewModel() {
        infoText.setValue("Here you can change app settings.");
    }

    public LiveData<String> getInfoText() {
        return infoText;
    }

    public void updateInfo(String text) {
        infoText.setValue(text);
    }
}
