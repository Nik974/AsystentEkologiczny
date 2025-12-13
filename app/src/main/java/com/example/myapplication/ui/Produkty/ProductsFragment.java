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
import java.util.Locale;

/**
 * Fragment wyświetlający listę produktów.
 * Umożliwia dodawanie nowych produktów i zarządzanie istniejącymi.
 */
public class ProductsFragment extends Fragment implements ProductAdapter.OnProductDeleteListener, ProductAdapter.OnProductEditListener {

    private FragmentHomeBinding binding;
    private ProductsViewModel productsViewModel;
    private ProductAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productsViewModel =
                new ViewModelProvider(this).get(ProductsViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicjalizacja RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter(new ArrayList<>(), this, this);
        binding.recyclerView.setAdapter(adapter);

        // Obserwowanie zmian na liście produktów i aktualizacja adaptera
        productsViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.setProducts(products);
        });

        productsViewModel.getMonthlyExpenses().observe(getViewLifecycleOwner(), expenses -> {
            binding.monthlyExpensesSummary.setText(String.format(Locale.getDefault(), "Wydatki w tym miesiącu: %.2f zł", expenses));
        });

        // Listener dla pływającego przycisku akcji (FAB) do dodawania nowego produktu
        binding.fab.setOnClickListener(view -> {
            NavHostFragment.findNavController(ProductsFragment.this)
                    .navigate(R.id.action_nav_home_to_nav_add_product);
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Odświeżenie listy produktów i wydatków przy starcie fragmentu
        productsViewModel.loadProducts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Metoda wywoływana, gdy użytkownik potwierdzi usunięcie produktu.
     * @param product Produkt do usunięcia.
     */
    @Override
    public void onProductDelete(Product product) {
        productsViewModel.deleteProductAndRefresh(product.getId());
    }

    /**
     * Metoda wywoływana, gdy użytkownik kliknie przycisk edycji produktu.
     * @param product Produkt do edycji.
     */
    @Override
    public void onProductEdit(Product product) {
        Bundle bundle = new Bundle();
        bundle.putInt("productId", product.getId());
        bundle.putString("productName", product.getName());
        bundle.putDouble("productPrice", product.getPrice());
        bundle.putString("productCategory", product.getCategory());
        bundle.putString("productExpiryDate", product.getExpiryDate());
        bundle.putString("productDescription", product.getDescription());
        bundle.putString("productShop", product.getShop());
        bundle.putString("productPurchaseDate", product.getPurchaseDate());

        NavHostFragment.findNavController(ProductsFragment.this)
                .navigate(R.id.action_nav_home_to_nav_add_product, bundle);
    }
}
