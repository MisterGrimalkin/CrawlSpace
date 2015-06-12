package net.amarantha.crawlspace;

import java.io.IOException;
import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23017Pin;
import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CBus;

public class Main {

    public static void main(String[] args) {

        System.out.println("Starting Crawl Space....");

        try {
            GpioController gpio = GpioFactory.getInstance();
            MCP23017GpioProvider gpioProvider = new MCP23017GpioProvider(I2CBus.BUS_1, 0x21);
            GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(gpioProvider, MCP23017Pin.GPIO_A0, PinState.LOW);
            while ( true ) {
                System.out.println("HIGH");
                led.high();
                Thread.sleep(1000);
                System.out.println("LOW");
                led.low();
                Thread.sleep(1000);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }



    }

}
