package net.amarantha.crawlspace.controller;


import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.util.Timer;
import java.util.TimerTask;

public abstract class ActiveOpenPinListener implements GpioPinListenerDigital {

    private long lastClosedStateSensed;
    private boolean switchOpen = false;

    public ActiveOpenPinListener() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if ( (System.currentTimeMillis()-lastClosedStateSensed >= 500) && !switchOpen ) {
                    switchOpen = true;
                    onOpen();
                }
            }
        }, 0, 100);

    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
        System.out.println(gpioPinDigitalStateChangeEvent.getState().getName());
        lastClosedStateSensed = System.currentTimeMillis();
        if ( switchOpen ) {
            switchOpen = false;
            onClose();
        }
    }

    public abstract void onClose();

    public abstract void onOpen();

}
