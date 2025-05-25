package smartDevices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SmartTv extends SmartDevice implements Switchable {
    private Boolean isOn;
    private final static HashSet<DeviceStatus> ALLOWED_STATUSES = new HashSet<>(Arrays.asList(
            DeviceStatus.ON,
            DeviceStatus.OFF
    ));
    private final List<String> availableInputs = List.of("TV", "HDMI1", "HDMI2", "Netflix", "YouTube");
    private final Random random = new Random();

    public SmartTv(String name) {
        super(ALLOWED_STATUSES);
        super.name = name;
        isOn = false;
        status = DeviceStatus.OFF;
    }

    @Override
    public void simulate() {
        if (isOn) {
            String randomInput = availableInputs.get(random.nextInt(availableInputs.size()));
            System.out.println("Changed canal to: " + randomInput);
        } else {
            System.out.println("TV is off.");
        }
    }

    @Override
    public void turnOff() {
        if(isOn) {
            status = DeviceStatus.OFF;
            System.out.println("SmartTv has turned " + getStatus() + ".");
        } else {
            System.out.println("SmartTv is already turned " + getStatus() + ".");
        }
    }

    @Override
    public void turnOn() {
        if(!isOn) {
            isOn = true;
            status = DeviceStatus.ON;
            System.out.println("SmartTv has turned " + getStatus() + ".");
        } else {
            System.out.println("SmartTv is already turned " + getStatus() + ".");
        }
    }

    @Override
    public boolean isOn() {
        return isOn;
    }
}
