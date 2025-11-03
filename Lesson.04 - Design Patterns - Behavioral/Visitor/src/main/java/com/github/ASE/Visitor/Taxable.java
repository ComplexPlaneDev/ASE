package com.github.ASE.Visitor;

public interface Taxable {
    double accept(TaxVisitor visitor);
}
