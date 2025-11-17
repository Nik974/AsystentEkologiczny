package com.example.myapplication.ui.Produkty;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;

import java.util.Locale;

/**
 * Aktywność wyświetlająca szczegółowe informacje o produkcie.
 * Dane są przekazywane do tej aktywności za pomocą intencji.
 */
public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Włączenie przycisku "wstecz" na pasku akcji
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Szczegóły produktu");
        }

        // Inicjalizacja pól TextView
        TextView name = findViewById(R.id.detail_name);
        TextView price = findViewById(R.id.detail_price);
        TextView category = findViewById(R.id.detail_category);
        TextView expiryDate = findViewById(R.id.detail_expiry_date);
        TextView description = findViewById(R.id.detail_description);
        TextView shop = findViewById(R.id.detail_shop);
        TextView purchaseDate = findViewById(R.id.detail_purchase_date);

        // Pobranie danych produktu z intencji i ustawienie ich w widokach
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name.setText(extras.getString("name"));
            price.setText("Cena: " + String.format(Locale.getDefault(), "%.2f zł", extras.getDouble("price")));
            category.setText("Kategoria: " + extras.getString("category"));
            expiryDate.setText("Data ważności: " + extras.getString("expiryDate"));
            description.setText("Opis: " + extras.getString("description"));
            shop.setText("Sklep: " + extras.getString("shop"));
            purchaseDate.setText("Data zakupu: " + extras.getString("purchaseDate"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Obsługa kliknięcia przycisku "wstecz"
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
