public class Member {
    private final String id;
    private final String name;
    private final String email;
    private final boolean active;
    private final int borrowLimit;

    public Member(String id, String name, String email, boolean active, int borrowLimit) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.active = active;
        this.borrowLimit = borrowLimit;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isActive() { return active; }
    public int getBorrowLimit() { return borrowLimit; }
}
