package net.amarantha.crawlspace.controller;

import net.amarantha.crawlspace.scene.EventLoop;
import net.amarantha.crawlspace.scene.EventManager;

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
                if ( !manager.isRunning() ) {
                    manager.startShow();
                } else
                if ( manager.currentTimeBetween(25.0, 44.0) ) {
                    manager.jumpTo(43.5);
                } else
                if ( manager.currentTimeBetween(45.0, 76.0) ) {
                    manager.jumpTo(75.5);
                } else
                if ( manager.getCurrentShowTime() > 76.0 ) {
                    manager.stopShow();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
