package com.github.ASE.Plain.Elements;

import com.github.ASE.Plain.Taxable;

public class Book implements Taxable {
    private final String title;
    private final double price;

    public Book(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public double standardTax() {
        System.out.println("Book: No tax");
        return 0;
    }

    @Override
    public double holidayTax() {
        System.out.println("Book (Holiday): 5% tax");
        return getPrice() * 0.05;
    }
}
