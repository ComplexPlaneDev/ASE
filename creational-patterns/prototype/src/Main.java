public class Main {
    public static void main(String[] args) {
        Shape circle = new Circle("red");
        Client client = new Client(circle);
        client.drawShape();
    }
}
