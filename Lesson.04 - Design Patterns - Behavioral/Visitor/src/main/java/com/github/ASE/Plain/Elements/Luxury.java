package com.github.ASE.Plain.Elements;

import com.github.ASE.Plain.Taxable;

public class Luxury implements Taxable {
    private final String name;
    private final double price;

    public Luxury(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public double standardTax() {
        System.out.println("Luxury: 20% tax");
        return getPrice() * 0.20;
    }

    @Override
    public double holidayTax() {
        System.out.println("Luxury (Holiday): 15% tax");
        return getPrice() * 0.15;
    }
}
