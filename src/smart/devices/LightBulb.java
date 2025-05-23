package smart.devices;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;

public class LightBulb extends SmartDevice implements Switchable {
    private final static HashSet<DeviceStatus> ALLOWED_STATUSES = new HashSet<>(Arrays.asList(
            DeviceStatus.ON,
            DeviceStatus.OFF
    ));
    private boolean isOn;
    private Float hue = 30F;
    private Float saturation = 0.01F;
    private Float value = 0.98F;

    public LightBulb(String name) {
        super(ALLOWED_STATUSES);
        super.name = name;
        isOn = false;
    }

    public Color getRGBColor() {
        return Color.getHSBColor(hue,saturation,value);
    }

    @Override
    public void simulate() {
        if(!isOn) {
            turnOn();
        } else {
            turnOff();
        }
    }

    @Override
    public void turnOff() {
        if(isOn) {
            isOn = false;
            status = DeviceStatus.OFF;
            System.out.println("Light bulb has turned off.");
        } else {
            System.out.println("Light bulb is already turned off.");
        }
    }

    @Override
    public void turnOn() {
        if(!isOn) {
            isOn = true;
            status = DeviceStatus.ON;
            System.out.println("Light bulb has turned on.");
        } else {
            System.out.println("Light bulb is already turned on.");
        }
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    public Float setHue(Float value) {
        try {
            if (0 <= value && value < 360) {
                this.hue = value;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Hue has to be greater or equal to 0 and smaller than 360");
        }
        return hue;
    }

    public Float setSaturation(Float value) {
        try {
            if(0 <= value && value <= 1) {
                this.saturation = value;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Saturation has to be greater or equal to 0 and smaller or equal to 1");
        }
        return saturation;
    }

    public Float setValue(Float value) {
        try {
            if(0 <= value && value <= 1) {
                this.value = value;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Value has to be greater or equal to 0 and smaller or equal to 1");
        }
        return value;
    }
}
