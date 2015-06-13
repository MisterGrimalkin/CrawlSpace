package net.amarantha.crawlspace;

import java.io.IOException;

import static java.lang.Math.*;

public class Scene {

    private AudioFile audioFile;
    private String sceneName;
    private Integer sceneDuration;

    private boolean loopAudio = true;
    private String audioFilename;

    public Scene(String sceneName, String audioFilename, boolean loopAudio, double sceneDuration) {
        this.sceneName = sceneName;
        this.audioFilename = audioFilename;
        this.loopAudio = loopAudio;
        this.sceneDuration = (int)floor(sceneDuration*1000);
    }

    public Integer getSceneDuration() {
        return sceneDuration;
    }

    public void start() {
        System.out.println(sceneName);
        audioFile = new AudioFile(audioFilename, loopAudio);
        audioFile.play();
    }

    public void stop() {
        audioFile.stop();
    }

}
