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

    public Product() {}

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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getShop() { return shop; }
    public void setShop(String shop) { this.shop = shop; }
    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }
}