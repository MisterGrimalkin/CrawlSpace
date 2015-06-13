package net.amarantha.crawlspace;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class Main extends Application {

    private static long lastOff = 0;
    private static boolean buttonDown = true;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        System.out.println("Starting Crawl Space....");

        AudioPlayer audioPlayer = new AudioPlayer();

        SceneManager manager = new SceneManager(true);
        manager.addScene(new Scene("1. Entry", audioPlayer, "test1.mp3", true, 5));
        manager.addScene(new Scene("2. Lost", audioPlayer, "test2.mp3", false, 5));
        manager.addScene(new Scene("3. Processing", audioPlayer, "test3.mp3", false, 5));
        manager.addScene(new Scene("4. Escape", audioPlayer, "test4.mp3", false, 5));
        manager.start();

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                consoleReader.readLine();
//                audioPlayer.stop();
                manager.next();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }




//        try {
//            GpioController gpio = GpioFactory.getInstance();
//            MCP23017GpioProvider inputBus = new MCP23017GpioProvider(I2CBus.BUS_1, 0x20);
//            MCP23017GpioProvider outputBus = new MCP23017GpioProvider(I2CBus.BUS_1, 0x21);
//            final GpioPinDigitalInput myButton1 = gpio.provisionDigitalInputPin(inputBus, MCP23017Pin.GPIO_A0, PinPullResistance.OFF);
//            myButton1.addListener((GpioPinListenerDigital) event -> {
//                lastOff = System.currentTimeMillis();
//                buttonDown = false;
//            });
//            GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(outputBus, MCP23017Pin.GPIO_A0, PinState.LOW);
//            while ( true ) {
//                if ( !buttonDown && System.currentTimeMillis() - lastOff >= 150 ) {
//                    buttonDown = true;
//                    led.toggle();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

}

