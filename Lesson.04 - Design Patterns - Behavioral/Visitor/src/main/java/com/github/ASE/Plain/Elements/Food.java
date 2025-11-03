package com.github.ASE.Plain.Elements;

import com.github.ASE.Plain.Taxable;

public class Food implements Taxable {
    private final String name;
    private final double price;

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public double standardTax() {
        System.out.println("Food: 5% tax");
        return getPrice() * 0.05;
    }

    @Override
    public double holidayTax() {
        System.out.println("Food (Holiday): No tax");
        return 0;
    }
}
