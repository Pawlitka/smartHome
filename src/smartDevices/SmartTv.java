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
    private List<String> availableInputs = List.of("TV", "HDMI1", "HDMI2", "Netflix", "YouTube");
    private Random random = new Random();

    SmartTv(HashSet<DeviceStatus> allowedStatuses) {
        super(ALLOWED_STATUSES);
        isOn = false;
    }

    @Override
    public void simulate() {
        if (isOn) {
            String randomInput = availableInputs.get(random.nextInt(availableInputs.size()));
            System.out.println("Symulacja: zmieniono źródło na " + randomInput);
        } else {
            System.out.println("Symulacja niemożliwa – TV jest wyłączony.");
        }
    }

    @Override
    public void turnOff() {
        isOn = false;
    }

    @Override
    public void turnOn() {
        isOn = true;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }
}
