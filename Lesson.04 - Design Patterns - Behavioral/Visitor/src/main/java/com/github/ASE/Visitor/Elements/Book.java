package com.github.ASE.Visitor.Elements;

import com.github.ASE.Visitor.TaxVisitor;
import com.github.ASE.Visitor.Taxable;

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

    public double accept(TaxVisitor visitor) {
        return visitor.visit(this);
    }
}
