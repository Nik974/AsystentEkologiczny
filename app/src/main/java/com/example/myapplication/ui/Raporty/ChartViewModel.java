package com.example.myapplication.ui.Raporty;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.ui.Kaucja.DepositDatabaseHelper;
import com.example.myapplication.ui.Produkty.ProductDatabaseHelper;

import java.util.HashMap;
import java.util.Map;

public class ChartViewModel extends AndroidViewModel {

    private final ProductDatabaseHelper productDbHelper;
    private final DepositDatabaseHelper depositDbHelper;
    private final MutableLiveData<Map<String, Float>> chartData = new MutableLiveData<>();

    public ChartViewModel(Application application) {
        super(application);
        productDbHelper = new ProductDatabaseHelper(application);
        depositDbHelper = new DepositDatabaseHelper(application);
        loadChartData();
    }

    public LiveData<Map<String, Float>> getChartData() {
        return chartData;
    }

    public void loadChartData() {
        Map<String, Float> data = new HashMap<>();
        // This is a simplified example. In a real app, you would query the database
        // for expenses and returned deposits for the last few months.
        data.put("Wydatki", (float) productDbHelper.getExpensesForCurrentMonth());
        data.put("Kaucje", (float) depositDbHelper.getReturnedDepositsValueForCurrentMonth());
        chartData.setValue(data);
    }
}