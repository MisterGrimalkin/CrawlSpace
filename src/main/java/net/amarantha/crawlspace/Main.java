package net.amarantha.crawlspace;

import javafx.application.Application;
import javafx.stage.Stage;
import net.amarantha.crawlspace.controller.ConsoleSceneController;
import net.amarantha.crawlspace.light.EStreamer;
import net.amarantha.crawlspace.scene.*;
import net.amarantha.crawlspace.webservice.WebService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {

    private Event printStringEvent(String string) {
        return new Event() {
            @Override
            public void onTrigger() {
                System.out.println(string);
            }
            @Override
            protected void onDispose() {

            }
        };
    }

    @Override
    public void start(Stage primaryStage) {

        System.out.println("Starting Crawl Space....");

        loadConfig();

        if ( !getParameters().getUnnamed().contains("noserver") ) {
            WebService.startWebService(ip);
        }

        StartAudioEvent stage1Audio = new StartAudioEvent("crawlspace-scene-1.mp3");
        StartAudioEvent stage2Audio = new StartAudioEvent("crawlspace-scene-2.mp3");
        StartAudioEvent stage3Audio = new StartAudioEvent("crawlspace-scene-3.mp3");
        StartAudioEvent stage4Audio = new StartAudioEvent("crawlspace-scene-4.mp3");


        EventManager events = new EventManager();

        events
            .addEvent   (  0.0, stage1Audio)

            .loop       ( 25.0, 43.0 )

            .addEvent   ( 25.0, stage2Audio)
            .addEvent   ( 26.0, new StopAudioEvent(stage1Audio))

            .loop       ( 44.0, 75.0 )

            .addEvent   ( 44.0, stage3Audio)
            .addEvent   ( 45.0, new StopAudioEvent(stage2Audio))

            .addEvent   ( 76.0, stage4Audio)
            .addEvent   ( 77.0, new StopAudioEvent(stage3Audio))

            .addEvent   (133.0, new ShowStopper(events));

//        new GpioSceneController(manager).start();
        new ConsoleSceneController(events).start();
    }

    public static void main(final String[] args) {
        launch(args);
    }

    private static String ip = null;

    public static void loadConfig() {
        try {
            String message = "CrawlSpace configuration: ";
            Properties prop = new Properties();
            InputStream is = new FileInputStream("crawlspace.properties");
            prop.load(is);
            if ( prop.getProperty("ip")!=null ) {
                ip = prop.getProperty("ip");
                message += " serving on " + ip;
            }
            if ( prop.getProperty("streamerIp")!=null ) {
                EStreamer.setStreamerIp(prop.getProperty("streamerIp"));
            }
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

