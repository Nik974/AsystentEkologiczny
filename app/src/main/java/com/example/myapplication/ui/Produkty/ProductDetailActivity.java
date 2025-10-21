package com.example.myapplication.ui.Produkty;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView name = findViewById(R.id.detail_name);
        TextView price = findViewById(R.id.detail_price);
        TextView category = findViewById(R.id.detail_category);
        TextView expiryDate = findViewById(R.id.detail_expiry_date);
        TextView description = findViewById(R.id.detail_description);
        TextView shop = findViewById(R.id.detail_shop);
        TextView purchaseDate = findViewById(R.id.detail_purchase_date);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name.setText(extras.getString("name"));
            price.setText(String.format(Locale.getDefault(), "%.2f zł", extras.getDouble("price")));
            category.setText(extras.getString("category"));
            expiryDate.setText(extras.getString("expiryDate"));
            description.setText(extras.getString("description"));
            shop.setText(extras.getString("shop"));
            purchaseDate.setText(extras.getString("purchaseDate"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
