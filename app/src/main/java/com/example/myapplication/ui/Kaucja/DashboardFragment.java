package com.example.myapplication.ui.Kaucja;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.Locale;

public class DashboardFragment extends Fragment implements DepositAdapter.OnDepositInteractionListener {

    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;
    private DepositAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupRecyclerView();

        dashboardViewModel.getDeposits().observe(getViewLifecycleOwner(), deposits -> {
            adapter.setDeposits(deposits);
        });

        final TextView totalValueTextView = binding.totalDepositValue;
        dashboardViewModel.getTotalDepositValue().observe(getViewLifecycleOwner(), totalValue -> {
            totalValueTextView.setText(String.format(Locale.getDefault(), "Suma kaucji: %.2f zł", totalValue));
        });

        return root;
    }

    private void setupRecyclerView() {
        binding.depositsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DepositAdapter(new ArrayList<>(), this);
        binding.depositsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fabAddDeposit.setOnClickListener(v -> {
            NavHostFragment.findNavController(DashboardFragment.this)
                    .navigate(R.id.action_navigation_Kaucja_to_addDepositFragment);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        dashboardViewModel.loadDeposits();
    }

    @Override
    public void onEditDeposit(Deposit deposit) {
        Bundle bundle = new Bundle();
        bundle.putInt("depositId", deposit.getId());
        bundle.putString("packagingType", deposit.getPackagingType());
        bundle.putDouble("depositValue", deposit.getDepositValue());
        bundle.putString("barcode", deposit.getBarcode());
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_Kaucja_to_addDepositFragment, bundle);
    }

    @Override
    public void onDeleteDeposit(Deposit deposit) {
        dashboardViewModel.deleteDeposit(deposit.getId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
