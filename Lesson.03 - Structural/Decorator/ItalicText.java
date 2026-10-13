/** EXAMPLE 2 - ConcreteDecorator: renders the wrapped text in italics. */
public class ItalicText extends TextDecorator {

    public ItalicText(StyledText text) { super(text); }

    @Override
    public String render() { return "/" + text.render() + "\\"; }
}
