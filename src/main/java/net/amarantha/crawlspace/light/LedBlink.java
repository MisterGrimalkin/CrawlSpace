package net.amarantha.crawlspace.light;

import com.pi4j.io.gpio.*;

import java.util.Timer;
import java.util.TimerTask;

public class LedBlink extends LightingScene {

    private GpioPinDigitalOutput led;
    private int period;

    public LedBlink(int period) {
        this.period = period;
        GpioController gpio = GpioFactory.getInstance();
        led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.HIGH);
    }

    private Timer timer;

    @Override
    public void start() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                led.toggle();
            }
        }, 0, period);
        led.high();
    }

    @Override
    public void stop() {
        led.low();
        timer.cancel();
    }
}
