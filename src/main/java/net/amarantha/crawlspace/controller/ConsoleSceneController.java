package net.amarantha.crawlspace.controller;

import net.amarantha.crawlspace.scene.EventLoop;
import net.amarantha.crawlspace.scene.EventManager;
import net.amarantha.crawlspace.scene.SceneManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleSceneController {

    private EventLoop loop;
    private EventManager manager;

    public ConsoleSceneController(EventManager manager) {
        this.manager = manager;
    }

    public void start() {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                consoleReader.readLine();
                if ( manager.currentTimeBetween(26.0, 45.9) ) {
                    System.out.println("jump-1");
                    manager.jumpTo(46.0);
                } else if ( manager.currentTimeBetween(46.0, 78.9) ) {
                    System.out.println("jump-2");
                    manager.jumpTo(79.0);
                }
//                loop.breakLoop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
