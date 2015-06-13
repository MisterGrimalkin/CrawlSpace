package net.amarantha.crawlspace.fromweb;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class SoundJLayer extends PlaybackListener implements Runnable
{
    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;

    public SoundJLayer(String filePath)
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
                            new java.net.URL(urlAsString).openStream(),
                            javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
                    );

            this.player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void stop() {
        if ( playing ) {
            player.stop();
            playerThread = null;
        }
    }

    // PlaybackListener members

    private boolean playing = false;

    public void playbackStarted(PlaybackEvent playbackEvent)
    {
        playing = true;
    }

    public void playbackFinished(PlaybackEvent playbackEvent)
    {
        playing = false;
    }

    // Runnable members

    public void run()
    {
        try
        {
            this.player.play();
        }
        catch (javazoom.jl.decoder.JavaLayerException ex)
        {
            ex.printStackTrace();
        }

    }
}