/** EXAMPLE 2 - ConcreteDecorator: renders the wrapped text in bold. */
public class BoldText extends TextDecorator {

    public BoldText(StyledText text) { super(text); }

    @Override
    public String render() { return "**" + text.render() + "**"; }
}
