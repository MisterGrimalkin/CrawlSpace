package net.amarantha.crawlspace.controller;

import com.pi4j.gpio.extension.mcp.MCP23017GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23017Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.i2c.I2CBus;
import net.amarantha.crawlspace.scene.SceneManager;

import java.io.IOException;

public class GpioSceneController {

    private SceneManager sceneManager;

    private long lastOff = 0;
    private boolean buttonDown = true;

    public GpioSceneController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void start() {
        try {
            GpioController gpio = GpioFactory.getInstance();
            MCP23017GpioProvider inputBus = new MCP23017GpioProvider(I2CBus.BUS_1, 0x20);
            final GpioPinDigitalInput myButton1 = gpio.provisionDigitalInputPin(inputBus, MCP23017Pin.GPIO_A0, PinPullResistance.OFF);
            myButton1.addListener((GpioPinListenerDigital) event -> {
                lastOff = System.currentTimeMillis();
                buttonDown = false;
            });
            while ( true ) {
                if ( !buttonDown && System.currentTimeMillis() - lastOff >= 150 ) {
                    buttonDown = true;
                    sceneManager.next();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
