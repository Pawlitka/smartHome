package smartDevices;

import java.util.Arrays;
import java.util.HashSet;

public class Outlet extends SmartDevice implements Switchable {
    private boolean isOn;
    boolean isUse;
    private final static HashSet<DeviceStatus> ALLOWED_STATUSES = new HashSet<>(Arrays.asList(
            DeviceStatus.ON,
            DeviceStatus.OFF
    ));

    public Outlet(String name) {
        super(ALLOWED_STATUSES, DeviceType.OUTLET);
        super.name = name;
        this.isOn = false;
        this.isUse = false;
        this.status = DeviceStatus.OFF;
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
    public void turnOn() {
        if(!isOn) {
            isOn = true;
            isUse = true;
            status = DeviceStatus.ON;
            System.out.println("Outlet has turned " + getStatus() + "." + " Device is receiving power.");
        } else {
            System.out.println("Outlet is already turned " + getStatus() + "." + " Device stopped receiving power.");
        }
    }

    @Override
    public void turnOff() {
        if(isOn || !isUse) {
            status = DeviceStatus.OFF;
            System.out.println("Outlet has turned " + getStatus() + ".");
        } else {
            System.out.println("Outlet is already turned " + getStatus() + ".");
        }
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    public void isInUse() {
        if(isUse) {
            System.out.println("Outlet is used");
        } else {
            System.out.println("Outlet is not used");
        }
    }

    public void useOutlet() {
        isUse = true;
    }
}
