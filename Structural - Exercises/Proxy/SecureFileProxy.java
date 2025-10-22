// SecureFileProxy.java
public class SecureFileProxy implements FileAccessor {

    private RealFileAccessor realFileAccessor;

    public SecureFileProxy() {
        // The proxy creates and manages the object.
        this.realFileAccessor = new RealFileAccessor();
    }

    @Override
    public String readFile(File file, Employee employee) {
       
        boolean hasAccess = employee.getClearanceLevel().getLevel() >= file.getSecurityLevel().getLevel();

        logAccess(employee.getId(), file.getName(), hasAccess);

        if (hasAccess) {
            return realFileAccessor.readFile(file, employee);
        } else {
            return "ACCESS DENIED: Insufficient security clearance for file '" + file.getName() + "'.";
        }
    }


    public void logAccess(String employeeId, String filename, boolean accessGranted) {
        String status = accessGranted ? "SUCCESS" : "FAILED";
        System.out.println("[AUDIT LOG] User '" + employeeId + "' attempted to access '" + filename + "'. Status: " + status);
    }
}
