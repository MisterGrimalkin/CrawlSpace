package net.amarantha.crawlspace.controller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import net.amarantha.crawlspace.scene.EventManager;

public class GpioSceneController {

    private EventManager events;

    public GpioSceneController(EventManager events) {
        this.events = events;
    }

    public void start() {
        GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalInput myButton1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
        myButton1.addListener((GpioPinListenerDigital) event -> {
            if ( event.getState()==PinState.LOW ) {
                if ( !events.isRunning() ) {
                    events.startShow();
                } else
                if ( events.currentTimeBetween(25.0, 44.0) ) {
                    events.jumpTo(43.5);
                } else
                if ( events.currentTimeBetween(45.0, 76.0) ) {
                    events.jumpTo(75.5);
                } else
                if ( events.getCurrentShowTime() > 76.0 ) {
                    events.stopShow();
                }
            }
            System.out.println(event.getState().getName());
        });
        System.out.println("CrawlSpace Running...........");
        while ( true ) {
        }

    }
}
