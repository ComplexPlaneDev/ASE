package composite.example2.gui;

import java.util.ArrayList;
import java.util.List;

/** Composite (concrete class): a panel — container widget that holds other widgets. */
public class Panel extends Widget {

    private final List<Widget> children = new ArrayList<>();

    public Panel(String name) {
        super(name);
    }

    @Override
    public void render(String indent) {
        System.out.println(indent + "{Panel}  " + name);
        for (Widget child : children) {
            child.render(indent + "  ");   // delegation to every child widget
        }
    }

    @Override
    public void add(Widget child) {
        children.add(child);
    }

    @Override
    public void remove(Widget child) {
        children.remove(child);
    }
}
