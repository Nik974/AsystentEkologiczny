package com.example.myapplication.ui.Produkty;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

/**
 * ViewModel do zarządzania danymi produktów.
 * Odpowiada za ładowanie, udostępnianie i usuwanie produktów,
 * komunikując się z bazą danych i udostępniając dane dla widoków.
 */
public class ProductsViewModel extends AndroidViewModel {

    private final ProductDatabaseHelper dbHelper;
    private final MutableLiveData<List<Product>> products;
    private final MutableLiveData<Double> monthlyExpenses = new MutableLiveData<>();

    /**
     * Konstruktor ViewModelu.
     * @param application Aplikacja, która dostarcza kontekst.
     */
    public ProductsViewModel(Application application) {
        super(application);
        dbHelper = new ProductDatabaseHelper(application);
        products = new MutableLiveData<>();
        loadProducts();
        loadMonthlyExpenses();
    }

    /**
     * Zwraca LiveData zawierające listę produktów.
     * Widoki mogą obserwować te dane, aby automatycznie aktualizować UI.
     * @return LiveData z listą produktów.
     */
    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public LiveData<Double> getMonthlyExpenses() {
        return monthlyExpenses;
    }

    /**
     * Ładuje listę produktów z bazy danych i aktualizuje LiveData.
     */
    public void loadProducts() {
        products.setValue(dbHelper.getAllProducts());
        loadMonthlyExpenses(); // Refresh expenses when products change
    }

    public void loadMonthlyExpenses() {
        monthlyExpenses.setValue(dbHelper.getExpensesForCurrentMonth());
    }

    /**
     * Usuwa produkt z bazy danych, a następnie odświeża listę produktów.
     * @param productId ID produktu do usunięcia.
     */
    public void deleteProductAndRefresh(int productId) {
        dbHelper.deleteProduct(productId);
        loadProducts();
    }

    /**
     * Aktualizuje produkt w bazie danych i odświeża listę.
     * @param product Produkt do zaktualizowania.
     */
    public void updateProductAndRefresh(Product product) {
        dbHelper.updateProduct(product);
        loadProducts();
    }
}