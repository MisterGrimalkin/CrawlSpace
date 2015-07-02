package net.amarantha.crawlspace.controller;

import net.amarantha.crawlspace.Main;
import net.amarantha.crawlspace.event.EventLoop;
import net.amarantha.crawlspace.event.EventManager;

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
                if ( manager.currentTimeBetween(0.0, Main.scene3Start) ) {
                    manager.jumpTo(Main.scene3Start);
                } else
                if ( manager.getCurrentShowTime() > Main.scene3Start ) {
                    manager.stopShow();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
