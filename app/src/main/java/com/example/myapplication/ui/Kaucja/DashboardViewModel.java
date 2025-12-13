package com.example.myapplication.ui.Kaucja;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardViewModel extends AndroidViewModel {

    private final DepositDatabaseHelper dbHelper;
    private final MutableLiveData<List<Deposit>> deposits;
    private final MutableLiveData<Double> totalDepositValue = new MutableLiveData<>();
    private final MutableLiveData<Double> returnedThisMonth = new MutableLiveData<>();

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

    public LiveData<Double> getReturnedThisMonth() {
        return returnedThisMonth;
    }

    public void loadDeposits() {
        List<Deposit> depositList = dbHelper.getAllDeposits();
        deposits.setValue(depositList);
        calculateTotalValue(depositList);
        loadReturnedThisMonth();
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

    public void setDepositReturned(Deposit deposit, boolean isReturned) {
        deposit.setReturned(isReturned);
        if (isReturned) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            deposit.setReturnDate(sdf.format(new Date()));
        } else {
            deposit.setReturnDate(null);
        }
        dbHelper.updateDeposit(deposit);
        loadDeposits();
    }

    private void loadReturnedThisMonth() {
        returnedThisMonth.setValue(dbHelper.getReturnedDepositsValueForCurrentMonth());
    }

    private void calculateTotalValue(List<Deposit> deposits) {
        double total = 0;
        for (Deposit deposit : deposits) {
            if (!deposit.isReturned()) { // Only sum up not returned deposits
                total += deposit.getDepositValue();
            }
        }
        totalDepositValue.setValue(total);
    }
}