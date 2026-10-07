package adapter.example1;

/** Demo of the OBJECT ADAPTER (composition) — classic media player scenario. */
public class Main {
    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer(); // client holds only a MediaPlayer view

        player.play("mp3", "high.mp3");   // native capability
        player.play("vlc", "movie.vlc");  // delegated through the adapter
        player.play("mp4", "song.mp4");   // delegated through the adapter
    }
}
