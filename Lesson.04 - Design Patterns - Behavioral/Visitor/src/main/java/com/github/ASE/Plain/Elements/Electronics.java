package com.github.ASE.Plain.Elements;

import com.github.ASE.Plain.Taxable;

public class Electronics implements Taxable {
    private final String name;
    private final double price;

    public Electronics(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public double standardTax() {
        System.out.println("Electronics: 10% tax");
        return getPrice() * 0.10;
    }

    @Override
    public double holidayTax() {
        System.out.println("Electronics (Holiday): 5% tax");
        return getPrice() * 0.05;
    }
}
