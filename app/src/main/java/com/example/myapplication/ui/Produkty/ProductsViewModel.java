package com.example.myapplication.ui.Produkty;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

public class ProductsViewModel extends AndroidViewModel {

    private final ProductDatabaseHelper dbHelper;
    private final MutableLiveData<List<Product>> products;

    public ProductsViewModel(Application application) {
        super(application);
        dbHelper = new ProductDatabaseHelper(application);
        products = new MutableLiveData<>();
        loadProducts();
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public void loadProducts() {
        products.setValue(dbHelper.getAllProducts());
    }

    public void deleteProductAndRefresh(int productId) {
        dbHelper.deleteProduct(productId);
        loadProducts();
    }
}