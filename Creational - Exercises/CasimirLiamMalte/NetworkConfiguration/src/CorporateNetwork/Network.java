package CorporateNetwork;

import java.io.PrintStream;
import Network.BasicNetwork;

public class Network extends BasicNetwork {
    private final String securityZones;

    public Network(String securityZones) {
        this.securityZones = securityZones;
    }

    @Override
    public void render(PrintStream ps) {
        ps.println("---Corporate Networks---");
        ps.println("Security Zones: " + securityZones);
        ps.println("Basic Configuration: " + getBasicInformation());
        ps.println("Network.Network Topology: " + getNetworkTopology());
        ps.println("Security Configuration: "  + getSecurityConfiguration());
        ps.println("Device Settings: " + getDeviceSettings());
        ps.println("Performance Settings: " + getPerformanceSettings());
        ps.println("Monitoring Setup: " + getMonitoringSetup());
    }

    @Override
    public CorporateNetwork.Network clone() {
        return new CorporateNetwork.Network(this.securityZones);
    }
}
