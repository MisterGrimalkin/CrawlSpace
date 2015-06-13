package net.amarantha.crawlspace;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.net.URL;

public class AudioFile extends PlaybackListener implements Runnable {

    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;

    private boolean playing = false;
    private boolean loop = false;

    public AudioFile(String filePath) {
        this(filePath, false);
    }
    public AudioFile(String filePath, boolean loop) {
        this.filePath = filePath;
        this.loop = loop;
    }

    public void play() {
        try {
            String urlAsString = "file:///" + new java.io.File(".").getCanonicalPath() + "/" + this.filePath;

            player = new AdvancedPlayer(new URL(urlAsString).openStream(), FactoryRegistry.systemRegistry().createAudioDevice());
            player.setPlayBackListener(this);

            playerThread = new Thread(this, "AudioPlayerThread");
            playerThread.start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        if ( playing ) {
            loop = false;
            player.stop();
            playerThread = null;
        }
    }

    public void playbackStarted(PlaybackEvent playbackEvent) {
        playing = true;
    }

    public void playbackFinished(PlaybackEvent playbackEvent) {
        playing = false;
        if ( loop ) {
            play();
        }
    }

    public void run() {
        try {
            this.player.play();
        } catch (JavaLayerException ex) {
            ex.printStackTrace();
        }

    }

}

