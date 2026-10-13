/** Simulates the channel between client and server sides. */
public class ServerConnection {

    private final CalculatorServer server; // would live elsewhere in a real system

    public ServerConnection(CalculatorServer server) {
        this.server = server;
    }

    public Response send(Request request) {
        System.out.println("[Channel]  Sending request: " + request);
        Response response = server.dispatch(request);
        System.out.println("[Channel]  Received response: " + response);
        return response;
    }
}
