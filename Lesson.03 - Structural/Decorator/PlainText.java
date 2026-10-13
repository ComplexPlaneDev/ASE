/** EXAMPLE 2 - ConcreteComponent: unstyled text. */
public class PlainText implements StyledText {

    private final String content;

    public PlainText(String content) {
        this.content = content;
    }

    @Override
    public String render() { return content; }
}
