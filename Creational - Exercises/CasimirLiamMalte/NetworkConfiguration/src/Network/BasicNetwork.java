package Network;

public abstract class BasicNetwork implements Network {
    private String basicInformation;
    private String networkTopology;
    private String securityConfiguration;
    private String deviceSettings;
    private String performanceSettings;
    private String monitoringSetup;

    @Override
    public void setBasicInformation(String basicInformation) {
        this.basicInformation = basicInformation;
    }

    @Override
    public void setNetworkTopology(String networkTopology) {
        this.networkTopology = networkTopology;
    }

    @Override
    public void setSecurityConfiguration(String securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public void setDeviceSettings(String deviceSettings) {
        this.deviceSettings = deviceSettings;
    }

    @Override
    public void setPerformanceSettings(String performanceSettings) {
        this.performanceSettings = performanceSettings;
    }

    @Override
    public void setMonitoringSetup(String monitoringSetup) {
        this.monitoringSetup = monitoringSetup;
    }

    protected String getBasicInformation() {
        return this.basicInformation;
    }
    protected String getNetworkTopology() {
        return this.networkTopology;
    }
    protected String getSecurityConfiguration() {
        return this.securityConfiguration;
    }
    protected String getDeviceSettings() {
        return this.deviceSettings;
    }
    protected String getPerformanceSettings() {
        return this.performanceSettings;
    }
    protected String getMonitoringSetup() {
        return this.monitoringSetup;
    }

    @Override
    public abstract Network clone();
}
