import Network.Network;
import Network.NetworkPrototype;

public class Main {
    public static void main(String[] args) {
        NetworkPrototype.initializePrototypes();

        Network officeNetwork1 = NetworkPrototype.getNetwork("branch-office-template");
        officeNetwork1.setBasicInformation("Network ID, client name, deployment type, creation date");
        officeNetwork1.setNetworkTopology("IP address ranges, subnet configurations, VLAN settings");
        officeNetwork1.setSecurityConfiguration("Firewall rules, access controls, encryption settings");
        officeNetwork1.setDeviceSettings("Router configurations, switch settings, wireless access point configs");
        officeNetwork1.setPerformanceSettings("Bandwidth allocations, QoS rules, traffic shaping");
        officeNetwork1.setMonitoringSetup("Alert thresholds, logging configurations, health checks");
        officeNetwork1.render(System.out);
        System.out.println("\n");

        Network corporateNetwork1 = NetworkPrototype.getNetwork("corporate-networks-template");
        corporateNetwork1.setBasicInformation("Network ID, client name, deployment type, creation date");
        corporateNetwork1.setNetworkTopology("IP address ranges, subnet configurations, VLAN settings");
        corporateNetwork1.setSecurityConfiguration("Firewall rules, access controls, encryption settings");
        corporateNetwork1.setDeviceSettings("Router configurations, switch settings, wireless access point configs");
        corporateNetwork1.setPerformanceSettings("Bandwidth allocations, QoS rules, traffic shaping");
        corporateNetwork1.setMonitoringSetup("Alert thresholds, logging configurations, health checks");
        corporateNetwork1.render(System.out);
        System.out.println("\n");

        Network dataCenterNetwork1 = NetworkPrototype.getNetwork("data-center-template");
        dataCenterNetwork1.setBasicInformation("Network ID, client name, deployment type, creation date");
        dataCenterNetwork1.setNetworkTopology("IP address ranges, subnet configurations, VLAN settings");
        dataCenterNetwork1.setSecurityConfiguration("Firewall rules, access controls, encryption settings");
        dataCenterNetwork1.setDeviceSettings("Router configurations, switch settings, wireless access point configs");
        dataCenterNetwork1.setPerformanceSettings("Bandwidth allocations, QoS rules, traffic shaping");
        dataCenterNetwork1.setMonitoringSetup("Alert thresholds, logging configurations, health checks");
        dataCenterNetwork1.render(System.out);
        System.out.println("\n");
    }
}