package net.amarantha.crawlspace;

import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import static java.lang.Math.*;

public class Scene {

    private AudioPlayer audioPlayer;
    private String sceneName;
    private Integer sceneDuration;

    private boolean loopAudio = true;
    private String audioFilename;

    public Scene(String sceneName, AudioPlayer audioPlayer, String audioFilename, boolean loopAudio, double sceneDuration) {
        this.sceneName = sceneName;
        this.audioPlayer = audioPlayer;
        this.audioFilename = audioFilename;
        this.loopAudio = loopAudio;
        this.sceneDuration = (int)floor(sceneDuration*1000);
    }

    public Integer getSceneDuration() {
        return sceneDuration;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public void start() {
        audioPlayer.playTrack(audioFilename, loopAudio);
        System.out.println("Started Scene " + sceneName);
        audioPlayer.stop();
    }

    public void stop() {
        audioPlayer.stop();
        System.out.println("Stopped Scene " + sceneName);
    }

}
