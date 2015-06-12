package net.amarantha.crawlspace;

import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import net.amarantha.crawlspace.audio.JLayerAudio;

public class AudioPlayer {

    private JLayerAudio currentTrack;
    private boolean loop = false;

    public void playTrack(String filename, boolean loop) {
        currentTrack = new JLayerAudio(filename);
        currentTrack.play();
//        currentTrack.setPlaybackListener(new PlaybackListener() {
//            @Override
//            public void playbackFinished(PlaybackEvent evt) {
//                if ( loop ) {
//                    restart();
//                }
//            }
//        });
        this.loop = loop;
    }

    public void restart() {
        if ( currentTrack!=null ) {
            currentTrack.play();
        }
    }

    public void stop() {
        if ( currentTrack!=null ) {
            currentTrack.stop();
        }
    }

}

