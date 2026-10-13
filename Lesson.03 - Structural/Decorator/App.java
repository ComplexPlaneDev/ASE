public class App {
    public static void main(String[] args) {
        PlainText pt = new PlainText("Hello World");

        StyledText npt = new BoldText(new ItalicText(new BoldText(new UnderlineText(pt))));
        System.out.println(npt.render());

        System.out.println(new ItalicText(new UnderlineText(pt)).render());
    }
}
