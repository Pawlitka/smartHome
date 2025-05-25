package smartDevices;
import java.util.*;

public class DevicesManager extends SmartDevice implements ObservableDevice, DeviceObserver{
    private final List<DeviceObserver> observers = new ArrayList<>();
    private final HashMap<String, SmartDevice> devices = new HashMap<>();
    private final static HashSet<DeviceStatus> ALLOWED_STATUSES = new HashSet<>(Arrays.asList(
            DeviceStatus.ON,
            DeviceStatus.OFF
    ));
    private final List<Rule> rules = new ArrayList<>();

    public DevicesManager() {
        super(ALLOWED_STATUSES);
        name = "DevicesManager";
        devices.put(name, this);
        this.status = DeviceStatus.ON;
    }

    @Override
    public void simulate() {
        turnAllOn();
    }

    @Override
    public void update(SmartDevice device) {
        System.out.println("DevicesManager received massage: "
                + device.getName()
                + " changed to: " + getStatus());
    }

    @Override
    public void addObserver(DeviceObserver observer) {
        observers.add(observer);

    }
    public void addRule(Rule rule) {
        rules.add(rule);
        System.out.println("[Rule was added  " + rule);
    }

    public void checkRules() {
        System.out.println("Checking rules");
        for (Rule rule : rules) {
            rule.execute();
        }
    }

    @Override
    public void removeObserver(DeviceObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (DeviceObserver observer : observers) {
            observer.update(this);
        }
    }

    public void setStatus(DeviceStatus newStatus) {
        if (this.status != newStatus) {
            this.status = newStatus;
        }
        System.out.println("Status was changed to " + newStatus);
    }

    public void addDevice(SmartDevice device) {
        devices.put(device.getName(), device);
    }

    public void deleteDevice(String deviceName) {
        devices.remove(deviceName);
    }

    public HashMap<String, SmartDevice> getDevices() {
        return devices;
    }

    public void turnAllOff() {
        for (SmartDevice device : devices.values()) {
            if (device instanceof Switchable switchable) {
                switchable.turnOff();
            }
        }
    }

    public void turnAllOn() {
        for (SmartDevice device : devices.values()) {
            if (device instanceof Switchable switchable) {
                switchable.turnOn();
            }
        }
    }
}
