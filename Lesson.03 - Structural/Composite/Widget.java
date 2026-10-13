package composite.example2.gui;

/**
 * Component (abstract class): common interface for all GUI widgets,
 * whether they are simple controls or containers of other widgets.
 */
public abstract class Widget {

    protected final String name;

    public Widget(String name) {
        this.name = name;
    }

    /** Renders the widget (a container also renders all its children). */
    public void render(String indent) {
        throw new UnsupportedOperationException(name + " cannot be rendered");
    }

    /** Adds a child widget. Only containers support this. */
    public void add(Widget child) {
        throw new UnsupportedOperationException("Cannot add widgets to " + name);
    }

    /** Removes a child widget. Only containers support this. */
    public void remove(Widget child) {
        throw new UnsupportedOperationException("Cannot remove widgets from " + name);
    }
}
