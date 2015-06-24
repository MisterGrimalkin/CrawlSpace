package net.amarantha.crawlspace.event;

import net.amarantha.crawlspace.light.EStreamer;
import net.amarantha.crawlspace.sound.AudioFile;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.floor;
import static net.amarantha.crawlspace.light.EStreamer.Action.PLAY;
import static net.amarantha.crawlspace.light.EStreamer.Action.STOP;

public class Scene {

    private AudioFile currentAudio;

    private String sceneName;
    private String audioFilename;
    private int lightingSceneNumber;
    private Integer sceneDuration;
    private boolean loopScene;

    public Scene(String sceneName, String audioFilename, int lightSceneNumber, Double sceneDuration, boolean loopScene) {
        this.sceneName = sceneName;
        this.audioFilename = audioFilename;
        this.lightingSceneNumber = lightSceneNumber;
        this.sceneDuration = sceneDuration==null ? null : (int)floor(sceneDuration*1000);
        this.loopScene = loopScene;
    }

    public void start() {
        System.out.println("START: " + sceneName);
        if ( audioFilename!=null ) {
            currentAudio = new AudioFile(audioFilename);
            currentAudio.play();
        }
        EStreamer.getInstance().sendCommand(PLAY, lightingSceneNumber);
    }

    public void stop() {
        System.out.println("STOP: " + sceneName);
        if ( currentAudio!=null ) {
            final AudioFile audio = currentAudio;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    audio.stop();
                }
            }, 1000);
        }
        EStreamer.getInstance().sendCommand(STOP, lightingSceneNumber);
    }

    public String getName() {
        return sceneName;
    }

    public Integer getSceneDuration() {
        return sceneDuration;
    }

    public boolean isLoopScene() {
        return loopScene;
    }
}

