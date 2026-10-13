/** EXAMPLE 2 - ConcreteDecorator: renders the wrapped text underlined. */
public class UnderlineText extends TextDecorator {

    public UnderlineText(StyledText text) { super(text); }

    @Override
    public String render() { return "__" + text.render() + "__"; }
}
