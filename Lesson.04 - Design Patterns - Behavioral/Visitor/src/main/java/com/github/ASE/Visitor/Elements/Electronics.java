package com.github.ASE.Visitor.Elements;

import com.github.ASE.Visitor.TaxVisitor;
import com.github.ASE.Visitor.Taxable;

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

    public double accept(TaxVisitor visitor) {
        return visitor.visit(this);
    }
}
