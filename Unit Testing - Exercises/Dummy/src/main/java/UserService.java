import java.util.regex.Pattern;

/**
 * UserService handles user registration and management operations.
 */
public class UserService {
    private UserRepository userRepository;
    private EmailService emailService;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    /**
     * Registers a new user with validation and email notification
     *
     * @param user The user to register
     * @return Registration result with success status and message
     * @throws IllegalArgumentException if user data is invalid
     */
    public RegistrationResult registerUser(User user) {
        // Validate user input
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return new RegistrationResult(false, "Email is required");
        }

        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            return new RegistrationResult(false, "Invalid email format");
        }

        if (user.getName() == null || user.getName().trim().length() < 2) {
            return new RegistrationResult(false, "Name must be at least 2 characters long");
        }

        if (user.getAge() < 13) {
            return new RegistrationResult(false, "User must be at least 13 years old");
        }

        // Check if user already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return new RegistrationResult(false, "User with this email already exists");
        }

        // Save user to repository
        User savedUser = userRepository.save(user);

        // Send welcome email
        emailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getName());

        return new RegistrationResult(true, "User registered successfully");
    }

    /**
     * Calculates user account status based on age
     *
     * @param user The user to evaluate
     * @return Account status (MINOR, ADULT, SENIOR)
     */
    public AccountStatus calculateAccountStatus(User user) {
        if (user == null || user.getAge() < 0) {
            throw new IllegalArgumentException("Invalid user or age");
        }

        if (user.getAge() < 18) {
            return AccountStatus.MINOR;
        } else if (user.getAge() < 65) {
            return AccountStatus.ADULT;
        } else {
            return AccountStatus.SENIOR;
        }
    }
}

// Supporting classes and interfaces

interface UserRepository {
    boolean existsByEmail(String email);

    User save(User user);

    User findByEmail(String email);
}

interface EmailService {
    void sendWelcomeEmail(String email, String name);

    void sendPasswordResetEmail(String email);
}

class User {
    private String name;
    private String email;
    private int age;

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class RegistrationResult {
    private boolean success;
    private String message;

    public RegistrationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

enum AccountStatus {
    MINOR, ADULT, SENIOR
}
