package net.amarantha.crawlspace;

import javafx.application.Application;
import javafx.stage.Stage;
import net.amarantha.crawlspace.controller.ConsoleSceneController;
import net.amarantha.crawlspace.controller.GpioSceneController;
import net.amarantha.crawlspace.light.LedBlink;
import net.amarantha.crawlspace.light.LedOff;
import net.amarantha.crawlspace.light.LedOn;
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

        manager.addScene(new Scene("1. Entry",      new LedOff(),
                "Crawlspace001.mp3",    false,   18.0));
        manager.addScene(new Scene("2. Lost",       new LedOn(),
                "Crawlspace002.mp3",    false,   23.0));
        manager.addScene(new Scene("3. Processing", new LedBlink(50),
                "Crawlspace003.mp3",    true,  null));
        manager.addScene(new Scene("4. Escape",     new LedBlink(500),
                "Crawlspace004.mp3",    false,  88.0));

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

