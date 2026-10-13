public class RemoteProxyDemo {
    public static void main(String[] args) {
        System.out.println("== REMOTE PROXY: stub / skeleton over a simulated channel ==\n");

        // Server side (would run on another machine in reality)
        CalculatorServer server  = new CalculatorServer();
        ServerConnection channel = new ServerConnection(server);

        // Client side: only ever sees the RemoteCalculator interface
        RemoteCalculator calc = new RemoteCalculatorProxy(channel);

        System.out.println("add(2, 3)      = " + calc.add(2, 3));
        System.out.println("multiply(4, 5) = " + calc.multiply(4, 5));
    }
}
