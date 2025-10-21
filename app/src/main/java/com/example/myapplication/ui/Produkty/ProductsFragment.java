package com.example.myapplication.ui.Produkty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class ProductsFragment extends Fragment implements ProductAdapter.OnProductDeleteListener {

    private FragmentHomeBinding binding;
    private ProductsViewModel productsViewModel;
    private ProductAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productsViewModel =
                new ViewModelProvider(this).get(ProductsViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter(new ArrayList<>(), this);
        binding.recyclerView.setAdapter(adapter);

        productsViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.setProducts(products);
        });

        binding.fab.setOnClickListener(view -> {
            NavHostFragment.findNavController(ProductsFragment.this)
                    .navigate(R.id.action_nav_home_to_nav_add_product);
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        productsViewModel.loadProducts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onProductDelete(Product product) {
        productsViewModel.deleteProductAndRefresh(product.getId());
    }
}