package com.example.myapplication.ui.Produkty;

public class Product {
    private int id;
    private String name;
    private double price;
    private String expiryDate;
    private String category;
    private String description;
    private String shop;
    private String purchaseDate;

    public Product(String name, double price, String expiryDate,
                   String category, String description, String shop, String purchaseDate) {
        this.name = name;
        this.price = price;
        this.expiryDate = expiryDate;
        this.category = category;
        this.description = description;
        this.shop = shop;
        this.purchaseDate = purchaseDate;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getExpiryDate() { return expiryDate; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getShop() { return shop; }
    public String getPurchaseDate() { return purchaseDate; }
}