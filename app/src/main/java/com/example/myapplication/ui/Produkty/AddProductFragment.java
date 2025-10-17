package com.example.myapplication.ui.Produkty;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class AddProductFragment extends Fragment {

    private EditText editName, editPrice, editExpiryDate, editCategory,
            editDescription, editShop, editPurchaseDate;
    private Button btnSave;
    private ProductDatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        dbHelper = new ProductDatabaseHelper(getContext());

        editName = view.findViewById(R.id.editName);
        editPrice = view.findViewById(R.id.editPrice);
        editExpiryDate = view.findViewById(R.id.editExpiryDate);
        editCategory = view.findViewById(R.id.editCategory);
        editDescription = view.findViewById(R.id.editDescription);
        editShop = view.findViewById(R.id.editShop);
        editPurchaseDate = view.findViewById(R.id.editPurchaseDate);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> saveProduct());

        return view;
    }

    private void saveProduct() {
        String name = editName.getText().toString().trim();
        String priceText = editPrice.getText().toString().trim();
        String expiry = editExpiryDate.getText().toString().trim();
        String category = editCategory.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String shop = editShop.getText().toString().trim();
        String purchase = editPurchaseDate.getText().toString().trim();

        // Валидация
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceText) ||
                TextUtils.isEmpty(expiry) || TextUtils.isEmpty(category)) {
            Toast.makeText(getContext(), "Wypełnij wszystkie wymagane pola!", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Niepoprawny format ceny!", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product(name, price, expiry, category, description, shop, purchase);

        boolean inserted = dbHelper.addProduct(product);

        if (inserted) {
            Toast.makeText(getContext(), "Produkt zapisany", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(getContext(), "Błąd podczas zapisu!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editName.setText("");
        editPrice.setText("");
        editExpiryDate.setText("");
        editCategory.setText("");
        editDescription.setText("");
        editShop.setText("");
        editPurchaseDate.setText("");
    }
}