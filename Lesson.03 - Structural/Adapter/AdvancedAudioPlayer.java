package adapter.example1;

/**
 * ADAPTEE — an existing class with an INCOMPATIBLE interface.
 *
 * It already plays .vlc and .mp4 files, but its methods are named
 * playVlc()/playMp4() instead of play(type, file). We cannot (or do not
 * want to) modify it: it may be legacy code or a third-party library.
 */
public class AdvancedAudioPlayer {

    public void playVlc(String file) {
        System.out.println("Playing VLC file: " + file);
    }

    public void playMp4(String file) {
        System.out.println("Playing MP4 file: " + file);
    }
}
