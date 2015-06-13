package net.amarantha.crawlspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SceneManager {

    private int currentScene = 0;

    private boolean loop = false;

    public SceneManager() {
        this(false);
    }

    private Timer timer;

    public SceneManager(boolean loop) {
        this.loop = loop;
    }

    private void checkScenes() {
        Scene current = scenes.get(currentScene);
        if ( current!=null && current.getSceneDuration()!=null && System.currentTimeMillis()-sceneStarted >= current.getSceneDuration() ) {
            next();
        }
    }

    private long sceneStarted;

    private boolean running = false;

    private List<Scene> scenes = new ArrayList<>();

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
        System.out.println("Next");
        stopCurrent();
        currentScene++;
        if ( currentScene >= scenes.size() && loop ) {
            currentScene = 0;
        }
        startCurrent();
    }

    public void stop() {
        stopCurrent();
    }

    public void loadScene(int sceneNumber) {
        stopCurrent();
        currentScene = sceneNumber;
        startCurrent();
    }

    private void startCurrent() {
        System.out.println("START");
        scenes.get(currentScene).start();
        sceneStarted = System.currentTimeMillis();
        running = true;
    }

    private void stopCurrent() {
        System.out.println("STOP");
        scenes.get(currentScene).stop();
        running = false;
    }

    public void addScene(Scene scene) {
        scenes.add(scene);
    }
}
