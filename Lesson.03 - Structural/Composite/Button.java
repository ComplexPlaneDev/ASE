package composite.example2.gui;

/** Leaf (concrete class): a button — terminal widget, has no children. */
public class Button extends Widget {

    public Button(String name) {
        super(name);
    }

    @Override
    public void render(String indent) {
        System.out.println(indent + "[Button] " + name);
    }
}
