
import java.util.ArrayList;
import java.util.List;

import com.github.ASE.Visitor.TaxVisitor;
import com.github.ASE.Visitor.Visitors.Holiday;
import com.github.ASE.Visitor.Visitors.Standard;

public class App {

    public static void main(String[] args) {
        // ---- VISITOR ---- //
        List<com.github.ASE.Visitor.Taxable> vCart = new ArrayList<>();
        vCart.add(new com.github.ASE.Visitor.Elements.Book("Design Patterns", 49.99));
        vCart.add(new com.github.ASE.Visitor.Elements.Food("Organic Apples", 10.00));
        vCart.add(new com.github.ASE.Visitor.Elements.Electronics("Smartphone", 899.99));
        vCart.add(new com.github.ASE.Visitor.Elements.Luxury("Designer Watch", 1200.00));

        // Apply standard tax
        TaxVisitor standardVisitor = new Standard();
        double vStandardTax = vCalculateTax(vCart, standardVisitor);
        System.out.println("Total (visitor) Standard Tax: $" + vStandardTax + "\n");

        // Apply holiday tax
        TaxVisitor holidayVisitor = new Holiday();
        double vHolidayTax = vCalculateTax(vCart, holidayVisitor);
        System.out.println("Total (visitor) Holiday Tax: $" + vHolidayTax + "\n");

        // Apply lemon tax
        // TaxVisitor lemonVisitor = new Lemon();
        // double vLemonTax = vCalculateTax(vCart, lemonVisitor);
        // System.out.println("Total (visitor) Lemon Tax: $" + vLemonTax + "\n");
        // ------------------------------------

        // ---- PLAIN ---- //
        List<com.github.ASE.Plain.Taxable> pCart = new ArrayList<>();
        pCart.add(new com.github.ASE.Plain.Elements.Book("Design Patterns", 49.99));
        pCart.add(new com.github.ASE.Plain.Elements.Food("Organic Apples", 10.00));
        pCart.add(new com.github.ASE.Plain.Elements.Electronics("Smartphone", 899.99));
        pCart.add(new com.github.ASE.Plain.Elements.Luxury("Designer Watch", 1200.00));

        // Apply standard tax
        double pStandardTax = pCalculateStandardTax(pCart);
        System.out.println("Total (plain) Standard Tax: $" + pStandardTax + "\n");

        // Apply holiday tax
        double pHolidayTax = pCalculateHolidayTax(pCart);
        System.out.println("Total (plain) Holiday Tax: $" + pHolidayTax + "\n");

        // Apply lemon tax
        // double pLemonTax = pCalculateLemonTax(pCart);
        // System.out.println("Total (plain) Lemon Tax: $" + pLemonTax + "\n");
        // ------------------------------------
    }

    private static double vCalculateTax(List<com.github.ASE.Visitor.Taxable> items, TaxVisitor visitor) {
        double total = 0;
        for (com.github.ASE.Visitor.Taxable item : items) {
            total += item.accept(visitor);
        }
        return total;
    }

    private static double pCalculateStandardTax(List<com.github.ASE.Plain.Taxable> items) {
        double total = 0;
        for (com.github.ASE.Plain.Taxable item : items) {
            total += item.standardTax();
        }
        return total;
    }

    private static double pCalculateHolidayTax(List<com.github.ASE.Plain.Taxable> items) {
        double total = 0;
        for (com.github.ASE.Plain.Taxable item : items) {
            total += item.holidayTax();
        }
        return total;
    }

    /*
     * private static double pCalculateLemonTax(List<com.github.ASE.Plain.Taxable>
     * items, TaxVisitor visitor) { double total = 0; for
     * (com.github.ASE.Plain.Taxable item : items) { total += item.lemonTax(); }
     * return total; }
     */}
