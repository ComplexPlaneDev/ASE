package com.github.ASE.Visitor.Visitors;

import com.github.ASE.Visitor.TaxVisitor;
import com.github.ASE.Visitor.Elements.Book;
import com.github.ASE.Visitor.Elements.Electronics;
import com.github.ASE.Visitor.Elements.Food;
import com.github.ASE.Visitor.Elements.Luxury;

public class Standard implements TaxVisitor {
    private double totalTax = 0;

    @Override
    public double visit(Book book) {
        System.out.println("Book: No tax");
        return 0;
    }

    @Override
    public double visit(Food food) {
        System.out.println("Food: 5% tax");
        double tax = food.getPrice() * 0.05;
        totalTax += tax;
        return tax;
    }

    @Override
    public double visit(Electronics electronics) {
        System.out.println("Electronics: 10% tax");
        double tax = electronics.getPrice() * 0.10;
        totalTax += tax;
        return tax;
    }

    @Override
    public double visit(Luxury luxury) {
        System.out.println("Luxury: 20% tax");
        double tax = luxury.getPrice() * 0.20;
        totalTax += tax;
        return tax;
    }

    /*
     * @Override public double visit(Car car) { System.out.println("Car: 30% tax");
     * double tax = car.getPrice() * 0.30; totalTax += tax; return tax; }
     */

    @Override
    public double getTax() {
        return totalTax;
    }
}
