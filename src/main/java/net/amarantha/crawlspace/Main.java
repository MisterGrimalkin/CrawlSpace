package net.amarantha.crawlspace;

import javafx.application.Application;
import javafx.stage.Stage;
import net.amarantha.crawlspace.controller.GpioSceneController;
import net.amarantha.crawlspace.event.EventManager;
import net.amarantha.crawlspace.light.EStreamerInterface;
import net.amarantha.crawlspace.light.MadrixInterface;
import net.amarantha.crawlspace.sound.AudioEvent;
import net.amarantha.crawlspace.webservice.WebResource;
import net.amarantha.crawlspace.webservice.WebService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {

    public static double scene2Start;
    public static double scene3Start;
    public static double scene4Start;

    @Override
    public void start(Stage primaryStage) {

        System.out.println("Starting Crawl Space....");

        loadConfig();

        if ( !getParameters().getUnnamed().contains("noserver") ) {
            WebService.startWebService(ip);
        }

        AudioEvent doorSlamAndWaves =       new AudioEvent("crawlspace-scene-1.mp3");
        AudioEvent atmosphericGurgling =    new AudioEvent("crawlspace-scene-2.mp3");
        AudioEvent loudMechanicalNoise =    new AudioEvent("crawlspace-scene-3.mp3");
        AudioEvent fadeOut =                new AudioEvent("crawlspace-scene-4.mp3");

        MadrixInterface madrix = new MadrixInterface("2.0.0.33");

        EventManager events = new EventManager(madrix);
        WebResource.setEventManager(events);

        madrix.fade(127).trigger();

        madrix.bulkhead(1).trigger();
        madrix.bulkhead(2).trigger();


        events
             .addEvent(0.0,     doorSlamAndWaves)
                .addEvent(0.0,  madrix.bulkhead(1))
                .addEvent(1.1,  madrix.darkness(1))
                .addEvent(4.5, madrix.wavesLow(1))
                .addEvent(5.0,  madrix.wavesHigh(2))
                .addEvent(7.5,  madrix.wavesLow(1))
                .addEvent(10.0, madrix.wavesHigh(2))
                .addEvent(15.0, madrix.wavesLow(1))
                .addEvent(18.0, madrix.wavesHigh(2))
                .addEvent(20.0, madrix.wavesLow(1));

        scene2Start = 21.0;
        events
                .addEvent(scene2Start,          atmosphericGurgling)
                .addEvent(scene2Start + 1.0,    doorSlamAndWaves.stop())
                .addEvent(scene2Start + 0.5,    madrix.sparkly(1))
                .addEvent(scene2Start + 2.0,    madrix.spot2(2))
                .addEvent(scene2Start + 2.5,    madrix.fade(200))
                .addEvent(scene2Start + 6,      madrix.spot23(2))
                .addEvent(scene2Start + 8,      madrix.fade(200))
                .addEvent(scene2Start + 9,      madrix.spot234(2))
                .addEvent(scene2Start + 10,     madrix.fade(200))
                .addEvent(scene2Start + 12,     madrix.spot345(2))
                .addEvent(scene2Start+13,       madrix.midspot(2));

        scene3Start = 35.0;
        events
                .addEvent(scene3Start,      madrix.darkness(1))
                .addEvent(scene3Start+1,    loudMechanicalNoise)
                .addEvent(scene3Start+1,    madrix.fullLineWhiteStrobe(1))
                .addEvent(scene3Start+2,    doorSlamAndWaves.stop())
                .addEvent(scene3Start+2,    atmosphericGurgling.stop())
                .addEvent(scene3Start+3,    madrix.fallingRedFast(2))
                .addEvent(scene3Start+5.5,  madrix.fallingWhite(1))
                .addEvent(scene3Start+9,    madrix.fallingRedSlow(2))
                .addEvent(scene3Start+11,   madrix.fullLineWhiteStrobe(1))
                .addEvent(scene3Start+13,   madrix.fallingWhite(2))
                .addEvent(scene3Start+15,   madrix.fallingRedFast(1))
                .addEvent(scene3Start+18,   madrix.fullLineWhiteStrobe(2))
                .addEvent(scene3Start+21,   madrix.fallingRedFast(1))
                .addEvent(scene3Start + 23, madrix.fullLineWhiteStrobe(2))
                .addEvent(scene3Start + 24, madrix.fallingRedSlow(1))
                .addEvent(scene3Start + 27, madrix.fullLineWhiteStrobe(2));

        scene4Start = 62.0;
        events
                .addEvent(scene4Start, madrix.spot789(1))
                .addEvent(scene4Start, madrix.spot789(2))
                .addEvent(scene4Start, fadeOut)
                .addEvent(scene4Start+0.5, loudMechanicalNoise.stop())

                .addEvent(100.0, events.stop());


        new GpioSceneController(events).start();
//        new ConsoleSceneController(events).start();
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
                EStreamerInterface.setStreamerIp(prop.getProperty("streamerIp"));
            }
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

