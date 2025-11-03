package com.github.ASE.Visitor;

import com.github.ASE.Visitor.Elements.Book;
import com.github.ASE.Visitor.Elements.Electronics;
import com.github.ASE.Visitor.Elements.Food;
import com.github.ASE.Visitor.Elements.Luxury;

public interface TaxVisitor {
    double visit(Book book);

    double visit(Food food);

    double visit(Electronics electronics);

    double visit(Luxury luxury);

    /* double visit(Car car); */

    double getTax();
}
