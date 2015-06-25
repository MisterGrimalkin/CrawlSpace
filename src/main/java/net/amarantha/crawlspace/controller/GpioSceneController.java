package net.amarantha.crawlspace.controller;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import net.amarantha.crawlspace.Main;
import net.amarantha.crawlspace.event.EventManager;

public class GpioSceneController {

    private EventManager events;

    public GpioSceneController(EventManager events) {
        this.events = events;
    }

    public void start() {
        GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalInput bulkheadTrigger = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput scene3Trigger = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP);
        final GpioPinDigitalInput exitTrigger = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);

        bulkheadTrigger.addListener((GpioPinListenerDigital) gpioEvent -> {
            System.out.println("--> BULKHEAD " + gpioEvent.getState().getName());
            if (gpioEvent.getState()==PinState.HIGH) {
                events.startShow();
            }
        });

        scene3Trigger.addListener((GpioPinListenerDigital) gpioEvent -> {
            System.out.println("--> SCENE 3 " + gpioEvent.getState().getName());
            if (gpioEvent.getState()==PinState.HIGH) {
                events.jumpTo(Main.scene3Start);
            }
        });

        exitTrigger.addListener((GpioPinListenerDigital) gpioEvent -> {
            System.out.println("--> FINISH " + gpioEvent.getState().getName());
            if (gpioEvent.getState() == PinState.HIGH) {
                events.stopShow();
            }
        });

//        myButton1.addListener((GpioPinListenerDigital) event -> {
//            if ( event.getState()==PinState.HIGH ) {
//                if ( !events.isRunning() ) {
//                    events.startShow();
//                } else
//                if ( events.currentTimeBetween(25.0, 44.0) ) {
//                    events.jumpTo(43.5);
//                } else
//                if ( events.currentTimeBetween(45.0, 76.0) ) {
//                    events.jumpTo(75.5);
//                } else
//                if ( events.getCurrentShowTime() > 76.0 ) {
//                    events.stopShow();
//                }
//            }
//        });
        System.out.println("CrawlSpace Running...........");
        while ( true ) {
        }

    }
}
