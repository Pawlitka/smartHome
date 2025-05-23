package smart.devices;

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
    private Double currentTemperature;

    public TemperatureSensor(String name) {
        super(ALLOWED_STATUSES);
        super.name = name;
        this.currentTemperature = (Math.random() * 80) - 40;
    }

    @Override
    public void simulate() {
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
}
