package smartDevices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class MotionSensor extends SmartDevice implements SensorDevice, Switchable {
    private boolean motionDetected;
    private Random random = new Random();
    private boolean isOn;
    private final static HashSet<DeviceStatus> ALLOWED_STATUSES = new HashSet<>(Arrays.asList(
            DeviceStatus.ON,
            DeviceStatus.OFF,
            DeviceStatus.SLEEP_MODE
    ));

    public MotionSensor(String name) {
        super(ALLOWED_STATUSES, DeviceType.MOTION_SENSOR);
        this.motionDetected = false;
        this.status = DeviceStatus.OFF;
        this.name = name;
    }

    @Override
    public void simulate() {
        this.motionDetected = random.nextBoolean();
        this.status = DeviceStatus.ON;
    }

    @Override
    public Boolean readValue() {
        return motionDetected;
    }

    @Override
    public String getUnit() {
        motionDetected = true;
        return "detected";
    }

    public void setStatus(DeviceStatus status) {
        if (this.status != status) {
            this.status = status;
        }
        System.out.println("Status was changed to " + status);
    }

    public boolean isMotionDetected() {
        return motionDetected;
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
