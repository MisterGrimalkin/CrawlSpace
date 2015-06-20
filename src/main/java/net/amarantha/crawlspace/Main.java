package net.amarantha.crawlspace;

import javafx.application.Application;
import javafx.stage.Stage;
import net.amarantha.crawlspace.controller.GpioSceneController;
import net.amarantha.crawlspace.scene.Scene;
import net.amarantha.crawlspace.scene.SceneManager;
import net.amarantha.crawlspace.webservice.WebResource;
import net.amarantha.crawlspace.webservice.WebService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        System.out.println("Starting Crawl Space....");

        loadConfig();

        WebService.startWebService(ip);

        SceneManager manager = new SceneManager(false);
        WebResource.bindSceneManager(manager);

        manager.addScene(new Scene("ENTRY",      null,//new LedBlink(500),
                "crawlspace-scene-1.mp3",    false,   24.0));
        manager.addScene(new Scene("LOST",      null,//new LedBlink(500),
                "crawlspace-scene-2.mp3",    false,   20.0));
        manager.addScene(new Scene("PROCESSING",       null,//new LedBlink(50),
                "crawlspace-scene-3.mp3",    true,   99.0));
        manager.addScene(new Scene("ESCAPE", null,//new LedBlink(25),
                "crawlspace-scene-4.mp3",    false,  60.0));

        new GpioSceneController(manager).start();
//        new ConsoleSceneController(manager).start();

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
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

