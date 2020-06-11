package com.ntas.FarmUncle;

public class shopitem_model {
    private String Name;
    private int Price;
    private String Image;
    private double value;

    public shopitem_model() {
        //Required by Firebase!
    }

    public shopitem_model(String Name, String Image, int Price, double value) {
        this.Image = Image;
        this.Price = Price;
        this.Name = Name;
        this.value = value;
    }

    public String getName() {
        return Name;
    }

    public int getPrice() {
        return Price;
    }

    public String getImage() {
        return Image;
    }

    public double getValue() {
        return value;
    }
}
