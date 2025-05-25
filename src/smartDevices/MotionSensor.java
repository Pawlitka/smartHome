package smartDevices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class MotionSensor extends SmartDevice implements SensorDevice{
    private boolean motionDetected;
    private Random random = new Random();
    private final static HashSet<DeviceStatus> ALLOWED_STATUSES = new HashSet<>(Arrays.asList(
            DeviceStatus.ON,
            DeviceStatus.OFF,
            DeviceStatus.SLEEP_MODE
    ));

    public MotionSensor(String name) {
        super(ALLOWED_STATUSES);
        this.motionDetected = false;
        this.status = DeviceStatus.OFF;
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

}
