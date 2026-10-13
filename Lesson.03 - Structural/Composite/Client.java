package composite.example2.gui;

/** Client: builds a login form from widgets and renders it through Widget only. */
public class Client {

    public static void main(String[] args) {
        // A login form built from widgets — the client only sees Widget
        Panel form = new Panel("LoginForm");

        form.add(new Label("Welcome!"));

        Panel fields = new Panel("Fields");
        fields.add(new Label("Username:"));
        fields.add(new Button("Login"));
        form.add(fields);

        form.add(new Button("Cancel"));

        System.out.println("=== Example 2: GUI Widget Tree ===");
        System.out.println();
        form.render("");   // one call renders the whole form, at any nesting depth
    }
}
