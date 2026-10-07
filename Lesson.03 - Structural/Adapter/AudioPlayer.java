package adapter.example1;

/**
 * CLIENT — uses only the Target interface.
 *
 * Plays .mp3 natively; for .vlc/.mp4 it delegates to a MediaAdapter,
 * which is invisible to the caller: from outside, everything is just
 * a MediaPlayer.
 */
public class AudioPlayer implements MediaPlayer {

    @Override
    public void play(String audioType, String file) {
        switch (audioType) {
            case "mp3" -> System.out.println("Playing MP3 file: " + file);
            case "vlc", "mp4" -> {
                MediaAdapter adapter = new MediaAdapter(audioType); // create the adapter
                adapter.play(audioType, file);                      // call through Target
            }
            default -> System.out.println("Invalid media type: " + audioType);
        }
    }
}
