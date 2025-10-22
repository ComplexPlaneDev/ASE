public class Employee {
    private String id;
    private SecurityLevel clearanceLevel;

    public Employee(String id, SecurityLevel clearanceLevel) {
        this.id = id;
        this.clearanceLevel = clearanceLevel;
    }
    // Getters for id and clearanceLevel
    public String getId() { return id; }
    public SecurityLevel getClearanceLevel() { return clearanceLevel; }
    
}
