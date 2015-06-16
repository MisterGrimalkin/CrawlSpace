package net.amarantha.crawlspace.scene;

import net.amarantha.crawlspace.light.LedOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SceneManager {

    private List<Scene> scenes = new ArrayList<>();
    private int currentScene = 0;

    private long sceneStarted;

    private boolean running = false;
    private boolean loop = false;

    private Timer timer;

    private Scene panicScene;

    public SceneManager() {
        this(false);
    }

    public SceneManager(boolean loop) {
        this.loop = loop;
        panicScene = new Scene("PANIC!", null,//new LedOn(),
                null, false, null);
    }

    private void checkScenes() {
        Scene current = scenes.get(currentScene);
        if ( current!=null && current.getSceneDuration()!=null && System.currentTimeMillis()-sceneStarted >= current.getSceneDuration() ) {
            next();
        }
    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public int getCurrentScene() {
        return currentScene;
    }

    public void start() {
        if ( timer == null ) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if ( running ) {
                        checkScenes();
                    }
                }
            }, 1000, 500);
        }
        currentScene = 0;
        startCurrent();
    }

    public void next() {
        if ( running ) {
            stopCurrent();
            currentScene++;
            if ( currentScene < scenes.size() ) {
                startCurrent();
            } else {
                if ( loop ) {
                    start();
                }
            }
        } else {
            start();
        }
    }

    public void stop() {
        stopCurrent();
    }

    public void panic() {
        if ( running ) {
            stopCurrent();
        }
        panicScene.start();
        currentScene = -1;
    }

    public boolean loadScene(int sceneNumber) {
        if ( sceneNumber>=0 && sceneNumber<scenes.size() ) {
            stopCurrent();
            currentScene = sceneNumber;
            startCurrent();
            return true;
        } else {
            return false;
        }
    }

    private void startCurrent() {
        scenes.get(currentScene).start();
        sceneStarted = System.currentTimeMillis();
        running = true;
    }

    private void stopCurrent() {
        scenes.get(currentScene).stop();
        running = false;
    }

    public void addScene(Scene scene) {
        scenes.add(scene);
    }

}
