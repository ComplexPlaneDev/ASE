package adapter.example1;

/**
 * ADAPTER (object adapter — composition).
 *
 * Implements the Target interface and HOLDS a reference to the Adaptee.
 * It translates every call expressed in the Target vocabulary into calls
 * of the Adaptee's own vocabulary. The client never sees the Adaptee.
 */
public class MediaAdapter implements MediaPlayer {

    private final AdvancedAudioPlayer advancedPlayer; // composition: has-a Adaptee
    private final String audioType;

    public MediaAdapter(String audioType) {
        this.audioType = audioType;
        this.advancedPlayer = new AdvancedAudioPlayer();
    }

    @Override
    public void play(String audioType, String file) {
        switch (audioType) {
            case "vlc" -> advancedPlayer.playVlc(file);
            case "mp4" -> advancedPlayer.playMp4(file);
            default    -> throw new UnsupportedOperationException(
                              "Unsupported type in adapter: " + audioType);
        }
    }
}
