package adapter.example1;

/**
 * TARGET — the interface that the Client expects.
 *
 * The client codes exclusively against this contract. It knows nothing
 * about which concrete implementation (native player or adapter) is used.
 */
public interface MediaPlayer {
    void play(String audioType, String file);
}
