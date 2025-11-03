public class ThemeContext {
    private static ThemeContext INSTANCE;
    private CharacterFactory factory;

    private ThemeContext() {}

    public static ThemeContext getInstance() {
        if (INSTANCE == null) INSTANCE = new ThemeContext();
        return INSTANCE;
    }

    public void setFactory(CharacterFactory factory) { this.factory = factory; }
    public CharacterFactory getFactory() { return factory; }
}
