package com.example.myapplication.ui.Raporty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartFragment extends Fragment {

    private ChartViewModel chartViewModel;
    private BarChart barChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        chartViewModel = new ViewModelProvider(this).get(ChartViewModel.class);
        barChart = view.findViewById(R.id.bar_chart);

        chartViewModel.getChartData().observe(getViewLifecycleOwner(), this::setupChart);

        return view;
    }

    private void setupChart(Map<String, Float> data) {
        if (data == null || data.isEmpty()) {
            barChart.clear();
            barChart.invalidate();
            return;
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>(data.keySet());

        for (int i = 0; i < labels.size(); i++) {
            String label = labels.get(i);
            entries.add(new BarEntry(i, data.get(label)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Wydatki vs Kaucje");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barChart.invalidate(); // refresh
    }
}