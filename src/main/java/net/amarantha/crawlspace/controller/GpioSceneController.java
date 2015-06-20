package net.amarantha.crawlspace.controller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import net.amarantha.crawlspace.scene.SceneManager;

public class GpioSceneController {

    private SceneManager sceneManager;

    private Long lastTrigger;

    public GpioSceneController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void start() {
        GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalInput myButton1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
        myButton1.addListener((GpioPinListenerDigital) event -> {
            if ( lastTrigger==null || System.currentTimeMillis() - lastTrigger > 750 ) {
                lastTrigger = System.currentTimeMillis();
                sceneManager.next();
            }
        });
        while ( true ) {
        }
    }

}
