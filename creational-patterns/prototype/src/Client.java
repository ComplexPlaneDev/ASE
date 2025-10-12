public class Client {
    private Shape shapePrototype;

    public Client(Shape shapePrototype) {
        this.shapePrototype = shapePrototype;
    }

    public void drawShape() {
        shapePrototype.draw();
    }
}
