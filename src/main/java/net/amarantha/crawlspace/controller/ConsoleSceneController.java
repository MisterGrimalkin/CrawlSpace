package net.amarantha.crawlspace.controller;

import net.amarantha.crawlspace.scene.SceneManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleSceneController {

    private SceneManager sceneManager;

    public ConsoleSceneController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void start() {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                consoleReader.readLine();
                sceneManager.next();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
