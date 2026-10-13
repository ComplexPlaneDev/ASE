/**
 * EXAMPLE 2 - Decorator (abstract class).
 * Wraps a StyledText and delegates rendering to it; concrete styles
 * wrap the delegated result with their own markers.
 */
public abstract class TextDecorator implements StyledText {

    protected final StyledText text;

    public TextDecorator(StyledText text) {
        this.text = text;
    }

    @Override
    public String render() { return text.render(); }
}
