package com.example.myapplication.ui.Produkty;

import android.app.DatePickerDialog;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Fragment odpowiedzialny za dodawanie i edycję produktu.
 * Umożliwia użytkownikowi wprowadzenie danych i zapisanie/zaktualizowanie go.
 */
public class AddProductFragment extends Fragment {

    private EditText editName, editPrice, editExpiryDate, editCategory,
            editDescription, editShop, editPurchaseDate;
    private Button btnSave;
    private ProductDatabaseHelper dbHelper;
    private ProductsViewModel productsViewModel;

    private int productId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        productsViewModel = new ViewModelProvider(requireActivity()).get(ProductsViewModel.class);
        dbHelper = new ProductDatabaseHelper(getContext());

        // Inicjalizacja pól formularza
        editName = view.findViewById(R.id.editName);
        editPrice = view.findViewById(R.id.editPrice);
        editExpiryDate = view.findViewById(R.id.editExpiryDate);
        editCategory = view.findViewById(R.id.editCategory);
        editDescription = view.findViewById(R.id.editDescription);
        editShop = view.findViewById(R.id.editShop);
        editPurchaseDate = view.findViewById(R.id.editPurchaseDate);
        btnSave = view.findViewById(R.id.btnSave);

        // Sprawdzenie, czy przekazano dane do edycji
        if (getArguments() != null) {
            productId = getArguments().getInt("productId", -1);
            if (productId != -1) {
                editName.setText(getArguments().getString("productName"));
                editPrice.setText(String.valueOf(getArguments().getDouble("productPrice")));
                editCategory.setText(getArguments().getString("productCategory"));
                editExpiryDate.setText(getArguments().getString("productExpiryDate"));
                editDescription.setText(getArguments().getString("productDescription"));
                editShop.setText(getArguments().getString("productShop"));
                editPurchaseDate.setText(getArguments().getString("productPurchaseDate"));
            }
        }

        // Ustawienie listenerów dla pól daty, aby otwierać kalendarz
        editExpiryDate.setOnClickListener(v -> showDatePickerDialog(editExpiryDate));
        editPurchaseDate.setOnClickListener(v -> showDatePickerDialog(editPurchaseDate));

        // Listener dla przycisku zapisu
        btnSave.setOnClickListener(v -> saveProduct());

        return view;
    }

    /**
     * Wyświetla okno dialogowe z kalendarzem do wyboru daty.
     * @param dateField Pole EditText, które ma zostać zaktualizowane wybraną datą.
     */
    private void showDatePickerDialog(final EditText dateField) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth);
                    dateField.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * Zapisuje lub aktualizuje produkt w bazie danych.
     */
    private void saveProduct() {
        String name = editName.getText().toString().trim();
        String priceText = editPrice.getText().toString().trim();
        String expiry = editExpiryDate.getText().toString().trim();
        String category = editCategory.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String shop = editShop.getText().toString().trim();
        String purchase = editPurchaseDate.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceText) || TextUtils.isEmpty(expiry) ||
                TextUtils.isEmpty(category) || TextUtils.isEmpty(description) || TextUtils.isEmpty(shop) || TextUtils.isEmpty(purchase)) {
            Toast.makeText(getContext(), "Wszystkie pola są wymagane!", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price <= 0) {
                Toast.makeText(getContext(), "Cena musi być większa od 0!", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Niepoprawny format ceny!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            sdf.setLenient(false);
            Date expiryDate = sdf.parse(expiry);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date today = cal.getTime();

            if (expiryDate.before(today)) {
                Toast.makeText(getContext(), "Data ważności nie może być z przeszłości!", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Niepoprawny format daty!", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product(name, price, expiry, category, description, shop, purchase);

        if (productId == -1) {
            // Dodawanie nowego produktu
            boolean inserted = dbHelper.addProduct(product);
            if (inserted) {
                Toast.makeText(getContext(), "Produkt zapisany", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).navigateUp();
            } else {
                Toast.makeText(getContext(), "Błąd podczas zapisu!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Aktualizacja istniejącego produktu
            product.setId(productId);
            productsViewModel.updateProductAndRefresh(product);
            Toast.makeText(getContext(), "Produkt zaktualizowany", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this).navigateUp();
        }
    }
}
