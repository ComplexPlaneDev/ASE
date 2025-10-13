package DataCenterNetworks;

import java.io.PrintStream;
import Network.BasicNetwork;

public class Network extends BasicNetwork {
    private final String redundancy;

    public Network(String redundancy) {
        this.redundancy = redundancy;
    }

    @Override
    public void render(PrintStream ps) {
        ps.println("---Data Center Networks---");
        ps.println("Redundancy: " + redundancy);
        ps.println("Basic Configuration: " + getBasicInformation());
        ps.println("Network.Network Topology: " + getNetworkTopology());
        ps.println("Security Configuration: "  + getSecurityConfiguration());
        ps.println("Device Settings: " + getDeviceSettings());
        ps.println("Performance Settings: " + getPerformanceSettings());
        ps.println("Monitoring Setup: " + getMonitoringSetup());
    }

    @Override
    public DataCenterNetworks.Network clone() {
        return new DataCenterNetworks.Network(this.redundancy);
    }
}
