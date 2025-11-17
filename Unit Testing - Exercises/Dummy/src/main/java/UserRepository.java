public interface UserRepository {
    boolean existsByEmail(String email);

    User save(User user);

    User findByEmail(String email);
}
