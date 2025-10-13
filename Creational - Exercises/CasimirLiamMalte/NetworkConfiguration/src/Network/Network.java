package Network;

import java.io.PrintStream;

public interface Network extends Cloneable{
    void setBasicInformation(String basicInformation);
    void setNetworkTopology(String networkTopology);
    void setSecurityConfiguration(String securityConfiguration);
    void setDeviceSettings(String deviceSettings);
    void setPerformanceSettings(String performanceSettings);
    void setMonitoringSetup(String monitoringSetup);

    void render(PrintStream overview);

    Network clone();
}
