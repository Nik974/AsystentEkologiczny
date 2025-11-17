package com.example.myapplication.ui.Kaucja;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private final DepositDatabaseHelper dbHelper;
    private final MutableLiveData<List<Deposit>> deposits;
    private final MutableLiveData<Double> totalDepositValue = new MutableLiveData<>();

    public DashboardViewModel(Application application) {
        super(application);
        dbHelper = new DepositDatabaseHelper(application);
        deposits = new MutableLiveData<>();
        loadDeposits();
    }

    public LiveData<List<Deposit>> getDeposits() {
        return deposits;
    }

    public LiveData<Double> getTotalDepositValue() {
        return totalDepositValue;
    }

    public void loadDeposits() {
        List<Deposit> depositList = dbHelper.getAllDeposits();
        deposits.setValue(depositList);
        calculateTotalValue(depositList);
    }

    public void addDeposit(Deposit deposit) {
        dbHelper.addDeposit(deposit);
        loadDeposits();
    }

    public void updateDeposit(Deposit deposit) {
        dbHelper.updateDeposit(deposit);
        loadDeposits();
    }

    public void deleteDeposit(int depositId) {
        dbHelper.deleteDeposit(depositId);
        loadDeposits();
    }

    private void calculateTotalValue(List<Deposit> deposits) {
        double total = 0;
        for (Deposit deposit : deposits) {
            total += deposit.getDepositValue();
        }
        totalDepositValue.setValue(total);
    }
}