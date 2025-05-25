package smartDevices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class TemperatureSensor extends SmartDevice implements SensorDevice {

    private final static HashSet<DeviceStatus> ALLOWED_STATUSES = new HashSet<>(Arrays.asList(
            DeviceStatus.ON,
            DeviceStatus.OFF,
            DeviceStatus.ACTIVE,
            DeviceStatus.FAULT,
            DeviceStatus.LOW_BATTERY,
            DeviceStatus.TAMPERED
    ));

    private  Double currentTemperature;

    public TemperatureSensor(String name) {
        super(ALLOWED_STATUSES);
        super.name = name;
        this.currentTemperature = (Math.random() * 80) - 40;
        this.status = DeviceStatus.OFF;
    }

    @Override
    public void simulate() {
        isTemperatureLow.execute();
        this.status = DeviceStatus.ON;
        double temperature = readValue();
        String unit = getUnit();
        System.out.println("Temperature sensor is reading: " +  String.format("%.2f",temperature) + unit);
    }

    public Double readValue() {
        double temperatureChange = -0.5 + Math.random();
        return currentTemperature += temperatureChange;
    }

    public String getUnit() {
        return "°C";
    }


    public void setTemperature(Double temperature) {
            if (temperature < -40.0 || temperature > 40.0) {
                throw new IllegalArgumentException("Temperature cannot be lower than -40" + getUnit() + " and higher than 40" + getUnit());
            }
        currentTemperature = temperature;
        System.out.println("You have changed temperature to " + String.format("%.2f",temperature) + getUnit());
    }

    public void setStatus(DeviceStatus newStatus) {
        if (this.status != newStatus) {
            this.status = newStatus;
        }
        System.out.println("Status was changed to " + newStatus);
    }

    public Double getTemperature() {
        return currentTemperature;
    }

    private final Rule isTemperatureLow = new Rule(this,
            _ -> getTemperature() < 16,
            _ -> setTemperature(currentTemperature + (Math.random() + 1) * 3));

}
