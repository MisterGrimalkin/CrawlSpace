package net.amarantha.crawlspace.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SceneManager {

    private Scene currentScene;

    private List<Scene> scenes = new ArrayList<>();
    private int currentSceneNumber = 0;

    private long sceneStarted;

    private boolean running = false;
    private boolean loopAll = false;

    private Timer timer;

    private Scene panicScene;
    private int lastScene;

    public SceneManager() {
        this(false);
    }

    public SceneManager(boolean loopAll) {
        this.loopAll = loopAll;
        panicScene = new Scene("PANIC!", null, 0, null, false);
    }

    private void checkScenes() {
        if ( currentSceneNumber < scenes.size() ) {
            if (currentScene.getSceneDuration() != null
                    && System.currentTimeMillis() - sceneStarted >= currentScene.getSceneDuration()) {
                next(false);
            }
        }
    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public int getCurrentSceneNumber() {
        return currentSceneNumber;
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
            }, 1000, 100);
        }
        currentSceneNumber = 0;
        currentScene = scenes.get(currentSceneNumber);
        sceneStarted = System.currentTimeMillis();
        running = true;
        currentScene.start();
    }

    public void next(boolean forceNext) {
        if ( running ) {
            Scene nowPlaying = scenes.get(currentSceneNumber);
            if ( !forceNext && nowPlaying.isLoopScene() ) {
                nowPlaying.stop();
                nowPlaying.start();
                sceneStarted = System.currentTimeMillis();
            } else {
                Scene nextUp = null;
                if ( ++currentSceneNumber < scenes.size() ) {
                    nextUp = scenes.get(currentSceneNumber);
                } else {
                    currentSceneNumber = 0;
                    if ( loopAll ) {
                        nextUp = scenes.get(currentSceneNumber);
                    }
                }
                nowPlaying.stop();
                if ( nextUp!=null ) {
                    sceneStarted = System.currentTimeMillis();
                    nextUp.start();
                } else {
                    running = false;
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
        currentSceneNumber = -1;
    }

    public void startScene(Scene scene) {
        scene.start();
        sceneStarted = System.currentTimeMillis();
        running = true;
    }

    public void stopScene(Scene scene) {
        scene.stop();
    }

    public boolean loadScene(int sceneNumber) {
        if ( sceneNumber>=0 && sceneNumber<scenes.size() ) {
            stopCurrent();
            currentSceneNumber = sceneNumber;
            startCurrent();
            return true;
        } else {
            return false;
        }
    }

    private void stopLast() {
        if ( lastScene < scenes.size() ) {
            scenes.get(lastScene).stop();
        }
    }

    private void startCurrent() {
        scenes.get(currentSceneNumber).start();
        sceneStarted = System.currentTimeMillis();
        running = true;
    }

    private void stopCurrent() {
        scenes.get(currentSceneNumber).stop();
        running = false;
    }

    public void addScene(Scene scene) {
        scenes.add(scene);
    }

}
