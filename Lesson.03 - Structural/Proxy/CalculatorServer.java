/** SKELETON side: lives on the "server machine" and executes incoming requests. */
public class CalculatorServer implements RemoteCalculator {

    @Override
    public int add(int a, int b)      { return a + b; }

    @Override
    public int multiply(int a, int b) { return a * b; }

    /** Entry point used by the connection layer to dispatch incoming requests. */
    public Response dispatch(Request request) {
        try {
            Object result = switch (request.method()) {
                case "add"      -> add((Integer) request.args()[0], (Integer) request.args()[1]);
                case "multiply" -> multiply((Integer) request.args()[0], (Integer) request.args()[1]);
                default         -> throw new IllegalArgumentException("Unknown method: " + request.method());
            };
            return Response.ok(result);
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }
}
