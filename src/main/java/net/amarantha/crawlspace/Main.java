package net.amarantha.crawlspace;

import javafx.application.Application;
import javafx.stage.Stage;
import net.amarantha.crawlspace.controller.ConsoleSceneController;
import net.amarantha.crawlspace.controller.GpioSceneController;
import net.amarantha.crawlspace.light.EStreamer;
import net.amarantha.crawlspace.light.MadrixInterface;
import net.amarantha.crawlspace.light.MadrixEvent;
import net.amarantha.crawlspace.event.*;
import net.amarantha.crawlspace.webservice.WebResource;
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
            protected void onReset() {

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

        AudioEvent doorSlamAndWaves = new AudioEvent("crawlspace-scene-1.mp3");
        AudioEvent atmosphericGurgling = new AudioEvent("crawlspace-scene-2.mp3");
        AudioEvent loudMechanicalNoise = new AudioEvent("crawlspace-scene-3.mp3");
        AudioEvent fadeOut = new AudioEvent("crawlspace-scene-4.mp3");

        MadrixInterface madrix = new MadrixInterface("2.0.0.33");

        EventManager events = new EventManager(madrix);
        WebResource.setEventManager(events);

        madrix.bulkhead().trigger();
//        madrix.fullLineWhiteStrobe().trigger();

        events
                .addEvent   (0.0, doorSlamAndWaves)
                .addEvent   (0.0, madrix.bulkhead())
                .addEvent   (1.1, madrix.darkness())

                .addEvent   (4.5, madrix.wavesLow())
                .addEvent   (6.0, madrix.wavesHigh())
                .addEvent   (7.5, madrix.wavesLow())
                .addEvent   (10.0, madrix.wavesHigh())
                .addEvent   (15.0, madrix.wavesLow())
                .addEvent   (18.0, madrix.wavesHigh())
                .addEvent   (20.0, madrix.wavesLow())
                .addEvent   (25.0, madrix.darkness())

                .loop(25.0, 43.0)

                .addEvent   ( 25.0, atmosphericGurgling)
                .addEvent   ( 26.0, doorSlamAndWaves.stop())

                .addEvent   (25.0, madrix.darkness())
                .addEvent   (28.0, madrix.spot2())
                .addEvent   (31.0, madrix.spot23())
                .addEvent   (34.0, madrix.spot234())
                .addEvent   (37.0, madrix.spot345())
                .addEvent   (40.0, madrix.spot456())
//                .addEvent   (36.0, madrix.spot567())
//                .addEvent   (38.0, madrix.spot678())
//                .addEvent   (40.0, madrix.spot789())
//                .addEvent   (42.0, madrix.spot89())
//                .addEvent   (44.0, madrix.spot9())
//                .addEvent   (40.0, madrix.spot9())

                .loop(44.0, 75.0)

                .addEvent   ( 44.0, loudMechanicalNoise)
                .addEvent   ( 44.0, madrix.fullLineWhiteStrobe())
                .addEvent   ( 45.0, atmosphericGurgling.stop())
                .addEvent   ( 46.0, madrix.fallingRedFast())
                .addEvent   ( 48.5, madrix.fallingWhite())
                .addEvent   ( 52.0, madrix.fallingRedSlow())
                .addEvent   ( 54.0, madrix.fullLineWhiteStrobe())
                .addEvent   ( 56.0, madrix.fallingWhite())
                .addEvent   ( 58.0, madrix.fallingRedFast())
                .addEvent   ( 61.0, madrix.fullLineWhiteStrobe())
                .addEvent   ( 64.0, madrix.fallingRedFast())
                .addEvent   ( 66.0, madrix.fullLineWhiteStrobe())
                .addEvent   ( 67.0, madrix.fallingRedSlow())
                .addEvent   ( 70.0, madrix.fullLineWhiteStrobe())
                .addEvent   ( 72.0, madrix.fallingRedFast())

                .addEvent   ( 76.0, madrix.watery())


                .addEvent   ( 76.0, fadeOut)
                .addEvent   ( 77.0, loudMechanicalNoise.stop())

                .addEvent(133.0, events.stop());

//        new GpioSceneController(events).start();
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

