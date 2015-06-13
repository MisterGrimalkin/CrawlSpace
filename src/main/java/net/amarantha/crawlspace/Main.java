package net.amarantha.crawlspace;

import javafx.application.Application;
import javafx.stage.Stage;
import net.amarantha.crawlspace.controller.GpioSceneController;
import net.amarantha.crawlspace.light.LedBlink;
import net.amarantha.crawlspace.light.LedOff;
import net.amarantha.crawlspace.light.LedOn;
import net.amarantha.crawlspace.scene.Scene;
import net.amarantha.crawlspace.scene.SceneManager;
import net.amarantha.crawlspace.webservice.WebResource;
import net.amarantha.crawlspace.webservice.WebService;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        System.out.println("Starting Crawl Space....");

        WebService.startWebService(getParameters().getNamed().get("ip"));

        SceneManager manager = new SceneManager(false);
        WebResource.bindSceneManager(manager);

        manager.addScene(new Scene("1. Entry",      new LedOff(),       "test3.mp3",    true,   5.0));
        manager.addScene(new Scene("2. Lost",       new LedOn(),        "test2.mp3",    true,   null));
        manager.addScene(new Scene("3. Processing", new LedBlink(50),   "test1.mp3",    false,  20.0));
        manager.addScene(new Scene("4. Escape",     new LedBlink(500),  "test4.mp3",    false,  20.0));

        new GpioSceneController(manager).start();

    }

    public static void main(final String[] args) {
        launch(args);
    }

}

