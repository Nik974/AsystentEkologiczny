package com.example.myapplication.ui.Raporty;

import android.content.Context;

import com.example.myapplication.ui.Kaucja.Deposit;
import com.example.myapplication.ui.Kaucja.DepositDatabaseHelper;
import com.example.myapplication.ui.Produkty.Product;
import com.example.myapplication.ui.Produkty.ProductDatabaseHelper;

import java.util.List;

public class CsvUtils {

    public static String generateCsvData(Context context) {
        ProductDatabaseHelper productDbHelper = new ProductDatabaseHelper(context);
        DepositDatabaseHelper depositDbHelper = new DepositDatabaseHelper(context);

        List<Product> products = productDbHelper.getAllProducts();
        List<Deposit> deposits = depositDbHelper.getAllDeposits();

        StringBuilder csvBuilder = new StringBuilder();

        // Nagłówki dla produktów
        csvBuilder.append("ID Produktu,Nazwa,Cena,Data Ważności,Kategoria,Opis,Sklep,Data Zakupu\n");
        for (Product product : products) {
            csvBuilder.append(product.getId()).append(",");
            csvBuilder.append(product.getName()).append(",");
            csvBuilder.append(product.getPrice()).append(",");
            csvBuilder.append(product.getExpiryDate()).append(",");
            csvBuilder.append(product.getCategory()).append(",");
            csvBuilder.append(product.getDescription()).append(",");
            csvBuilder.append(product.getShop()).append(",");
            csvBuilder.append(product.getPurchaseDate()).append("\n");
        }

        csvBuilder.append("\n\n"); // Przerwa między sekcjami

        // Nagłówki dla kaucji
        csvBuilder.append("ID Kaucji,Typ Opakowania,Wartość Kaucji,Kod Kreskowy,Zwrócono,Data Zwrotu\n");
        for (Deposit deposit : deposits) {
            csvBuilder.append(deposit.getId()).append(",");
            csvBuilder.append(deposit.getPackagingType()).append(",");
            csvBuilder.append(deposit.getDepositValue()).append(",");
            csvBuilder.append(deposit.getBarcode()).append(",");
            csvBuilder.append(deposit.isReturned()).append(",");
            csvBuilder.append(deposit.getReturnDate()).append("\n");
        }

        return csvBuilder.toString();
    }
}