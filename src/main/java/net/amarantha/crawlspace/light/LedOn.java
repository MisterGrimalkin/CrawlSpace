package net.amarantha.crawlspace.light;

import com.pi4j.io.gpio.*;

public class LedOn extends LightingScene {

    private GpioPinDigitalOutput led;

    public LedOn() {
        GpioController gpio = GpioFactory.getInstance();
        led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.HIGH);
    }

    @Override
    public void start() {
        led.high();
    }

    @Override
    public void stop() {
        led.low();
    }
}
