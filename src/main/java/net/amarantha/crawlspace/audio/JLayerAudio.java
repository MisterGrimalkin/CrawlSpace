package net.amarantha.crawlspace.audio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.net.URL;

/**
 * Adapted from
 *          https://thiscouldbebetter.wordpress.com/2011/07/04/pausing-an-mp3-file-using-jlayer/
 * Thanks :)
 */
public class JLayerAudio extends PlaybackListener implements Runnable {

    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;

    public JLayerAudio(String filePath)
    {
        this.filePath = filePath;
    }

    public void play()
    {
        try
        {
            String urlAsString =
                    "file:///"
                            + new java.io.File(".").getCanonicalPath()          + "/"
                            + this.filePath;

            this.player = new AdvancedPlayer
                    (
                            new URL(urlAsString).openStream(),
                            FactoryRegistry.systemRegistry().createAudioDevice()
                    );

            player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

//    public void setPlaybackListener(PlaybackListener listener) {
//        player.setPlayBackListener(listener);
//    }

    public String getFilePath() {
        return filePath;
    }

    // PlaybackListener members

    public void playbackStarted(PlaybackEvent playbackEvent)
    {
        System.out.println("playbackStarted");
    }

    public void playbackFinished(PlaybackEvent playbackEvent)
    {
        System.out.println("playbackEnded");
    }

    // Runnable members

    public void run()
    {
        try
        {
            this.player.play();
        }
        catch (JavaLayerException ex)
        {
            ex.printStackTrace();
        }

    }

    public void stop() {
        if ( player!=null && player.getPlayBackListener()!=null ) {
            player.stop();
        } else {
            System.out.println("NO!");
        }
    }

}
