public class File {
    private String name;
    private String content;
    private SecurityLevel securityLevel;

    public File(String name, String content, SecurityLevel securityLevel) {
        this.name = name;
        this.content = content;
        this.securityLevel = securityLevel;
    }
    // Getters for name, content, and securityLevel
    public String getName() { return name; }
    public String getContent() { return content; }
    public SecurityLevel getSecurityLevel() { return securityLevel; }
}
