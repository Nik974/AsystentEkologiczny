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
    private boolean isReturned;     // Czy opakowanie zostało zwrócone
    private String returnDate;      // Data zwrotu opakowania (YYYY-MM-DD)

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
        this.isReturned = false; // Domyślnie nowe opakowanie nie jest zwrócone
        this.returnDate = null;
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
    public boolean isReturned() { return isReturned; }
    public void setReturned(boolean returned) { isReturned = returned; }
    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
}