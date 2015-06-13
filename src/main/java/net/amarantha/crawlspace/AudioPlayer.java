package net.amarantha.crawlspace;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import net.amarantha.crawlspace.audio.JLayerAudio;
import net.amarantha.crawlspace.fromweb.*;

import java.io.IOException;
import java.net.URL;

public class AudioPlayer {

    private MediaPlayer mediaPlayer;
    private SoundJLayer currentTrack;
    private boolean loop = false;

    public void playTrack(String filename, boolean loop) throws IOException {
//        currentTrack = new JLayerAudio(filename);
//        currentTrack.play();
//        String urlAsString = "file:///" + new java.io.File(".").getCanonicalPath() + "/" + filename;
//        this.loop = loop;
//        URL resource = new URL(urlAsString);
//        Media media = new Media(resource.toString());
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.play();

        currentTrack = new SoundJLayer(filename);
        currentTrack.play();

    }

    public void restart() {
//        if ( currentTrack!=null ) {
//            currentTrack.play();
//        }
    }

    public void stop() {
        currentTrack.stop();
//        if ( currentTrack!=null ) {
//            currentTrack.stop();
//        }
    }

}

