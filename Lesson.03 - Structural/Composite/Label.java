package composite.example2.gui;

/** Leaf (concrete class): a label — terminal widget, has no children. */
public class Label extends Widget {

    public Label(String name) {
        super(name);
    }

    @Override
    public void render(String indent) {
        System.out.println(indent + "[Label]  " + name);
    }
}
