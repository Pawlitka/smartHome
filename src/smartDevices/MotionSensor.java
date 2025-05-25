package smartDevices;

import java.util.HashSet;
import java.util.Random;

public class MotionSensor extends SmartDevice implements SensorDevice{
    private boolean motionDetected;
    private Random random = new Random();

    public MotionSensor(HashSet<DeviceStatus> allowedStatuses) {
        super(allowedStatuses);
        this.motionDetected = false;
    }

    @Override
    public void simulate() {
        this.motionDetected = random.nextBoolean();
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
}
