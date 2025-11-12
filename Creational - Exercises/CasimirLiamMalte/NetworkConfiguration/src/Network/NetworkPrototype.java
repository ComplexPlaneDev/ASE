package Network;

import java.util.HashMap;
import java.util.Map;

public class NetworkPrototype {
    private static final Map<String, Network> prototypes = new HashMap<>();

    public static void addPrototype(String key, Network prototype) {
        prototypes.put(key, prototype);
    }

    public static Network getNetwork(String key) {
        Network prototype = prototypes.get(key);
        if (prototype == null) {
            throw new IllegalArgumentException(String.format("Network %s does not exist", key));
        }
        return prototype.clone();
    }

    public static void initializePrototypes() {
        addPrototype("branch-office-template", new BranchOfficeNetworks.Network("VPN enabled"));
        addPrototype("corporate-networks-template", new CorporateNetwork.Network("Security Zones enabled"));
        addPrototype("data-center-template", new DataCenterNetworks.Network("Redundancy enabled"));
    }
}
