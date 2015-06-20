package net.amarantha.crawlspace.scene;

import net.amarantha.crawlspace.light.LightingScene;
import net.amarantha.crawlspace.sound.AudioFile;

import static java.lang.Math.floor;

public class Scene {

    private String sceneName;
    private Integer sceneDuration;

    private LightingScene lightingScene;
    private AudioFile audioFile;
    private boolean loopAudio = true;
    private String audioFilename;

    public Scene(String sceneName, LightingScene lightingScene, String audioFilename, boolean loopAudio, Double sceneDuration) {
        this.sceneName = sceneName;
        this.lightingScene = lightingScene;
        this.audioFilename = audioFilename;
        this.loopAudio = loopAudio;
        this.sceneDuration = sceneDuration==null ? null : (int)floor(sceneDuration*1000);
    }

    public Integer getSceneDuration() {
        return sceneDuration;
    }

    public void start() {
        System.out.println(sceneName);
        if ( lightingScene!=null ) {
            lightingScene.start();
        }
        if ( audioFilename!=null ) {
            audioFile = new AudioFile(audioFilename, loopAudio);
            System.out.println(audioFilename);
            audioFile.play();
        }
    }

    public void stop() {
        if ( lightingScene!=null ) {
            lightingScene.stop();
        }
        if ( audioFile!=null ) {
            audioFile.stop();
        }
    }

    public String getName() {
        return sceneName;
    }
}
