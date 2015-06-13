package net.amarantha.crawlspace.light;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23017Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.i2c.I2CBus;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LedBlink extends LightingScene {

    private GpioPinDigitalOutput led;
    private int period;

    public LedBlink(int period) {
        this.period = period;
        try {
            GpioController gpio = GpioFactory.getInstance();
            MCP23017GpioProvider outputBus = new MCP23017GpioProvider(I2CBus.BUS_1, 0x21);
            led = gpio.provisionDigitalOutputPin(outputBus, MCP23017Pin.GPIO_A0, PinState.LOW);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
