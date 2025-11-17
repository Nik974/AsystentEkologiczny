package com.example.myapplication.ui.Kaucja;

/**
 * Klasa modelu reprezentująca pojedynczą kaucję (opakowanie).
 * Zawiera informacje o typie opakowania, wartości kaucji i kodzie kreskowym.
 */
public class Deposit {
    private int id;                 // Unikalny identyfikator kaucji w bazie danych
    private String packagingType;   // Typ opakowania (np. butelka, puszka)
    private double depositValue;    // Wartość kaucji w złotówkach
    private String barcode;         // Kod kreskowy opakowania

    /**
     * Domyślny konstruktor.
     */
    public Deposit() {}

    /**
     * Konstruktor do tworzenia nowego obiektu kaucji z podanymi danymi.
     * @param packagingType Typ opakowania
     * @param depositValue Wartość kaucji
     * @param barcode Kod kreskowy
     */
    public Deposit(String packagingType, double depositValue, String barcode) {
        this.packagingType = packagingType;
        this.depositValue = depositValue;
        this.barcode = barcode;
    }

    // Gettery i Settery

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPackagingType() { return packagingType; }
    public void setPackagingType(String packagingType) { this.packagingType = packagingType; }
    public double getDepositValue() { return depositValue; }
    public void setDepositValue(double depositValue) { this.depositValue = depositValue; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
}