package BranchOfficeNetworks;

import java.io.PrintStream;
import Network.BasicNetwork;

public class Network extends BasicNetwork {
    private final String vpnCapabilities;

    public Network(String vpnCapabilities) {
        this.vpnCapabilities = vpnCapabilities;
    }

    @Override
    public void render(PrintStream overview) {
        overview.println("---Branch Office Network---");
        overview.println("VPN Capabilities: " + vpnCapabilities);
        overview.println("Basic Configuration: " + getBasicInformation());
        overview.println("Network.Network Topology: " + getNetworkTopology());
        overview.println("Security Configuration: "  + getSecurityConfiguration());
        overview.println("Device Settings: " + getDeviceSettings());
        overview.println("Performance Settings: " + getPerformanceSettings());
        overview.println("Monitoring Setup: " + getMonitoringSetup());
    }

    @Override
    public Network clone() {
        return new Network(this.vpnCapabilities);
    }
}
