/** STUB side: looks like a local object to the client, but serializes each call and ships it. */
public class RemoteCalculatorProxy implements RemoteCalculator {

    private final ServerConnection connection;

    public RemoteCalculatorProxy(ServerConnection connection) {
        this.connection = connection;
    }

    @Override
    public int add(int a, int b) {
        return (Integer) invoke("add", a, b);
    }

    @Override
    public int multiply(int a, int b) {
        return (Integer) invoke("multiply", a, b);
    }

    private Object invoke(String method, Object... args) {
        Response response = connection.send(new Request(method, args));
        if (response.hasError()) {
            throw new RuntimeException("Remote call failed: " + response.error());
        }
        return response.value();
    }
}
