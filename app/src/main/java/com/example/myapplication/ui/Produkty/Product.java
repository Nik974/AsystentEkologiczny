package com.example.myapplication.ui.Produkty;

/**
 * Klasa modelu reprezentująca pojedynczy produkt.
 * Zawiera wszystkie informacje o produkcie, takie jak nazwa, cena, data ważności itp.
 */
public class Product {
    private int id;                 // Unikalny identyfikator produktu w bazie danych
    private String name;            // Nazwa produktu
    private double price;           // Cena produktu
    private String expiryDate;      // Data ważności produktu (w formacie YYYY-MM-DD)
    private String category;        // Kategoria produktu
    private String description;     // Opis produktu
    private String shop;            // Sklep, w którym produkt został zakupiony
    private String purchaseDate;    // Data zakupu produktu (w formacie YYYY-MM-DD)

    /**
     * Domyślny konstruktor.
     */
    public Product() {}

    /**
     * Konstruktor do tworzenia nowego produktu z podanymi danymi.
     * @param name Nazwa produktu
     * @param price Cena produktu
     * @param expiryDate Data ważności
     * @param category Kategoria
     * @param description Opis
     * @param shop Sklep
     * @param purchaseDate Data zakupu
     */
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

    // Gettery i Settery

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