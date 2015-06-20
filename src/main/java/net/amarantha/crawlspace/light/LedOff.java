package net.amarantha.crawlspace.light;

import com.pi4j.io.gpio.*;

public class LedOff extends LightingScene {

    private GpioPinDigitalOutput led;

    public LedOff() {
        GpioController gpio = GpioFactory.getInstance();
        led = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW);
    }

    @Override
    public void start() {
        led.low();
    }

    @Override
    public void stop() {
        led.low();
    }
}
