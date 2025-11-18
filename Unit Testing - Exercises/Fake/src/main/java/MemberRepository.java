public interface MemberRepository {
    Member findById(String memberId) throws DatabaseException;
}
