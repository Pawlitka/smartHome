import smartDevices.SmartDevice;
import java.util.HashMap;

public class Room {
    private final String name;
    private final RoomType type;
    private final HashMap<String, SmartDevice>  devices = new HashMap<>();

    public Room(String name, RoomType type) {
        this.name = name;
        this.type = type;
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

    public String getDevicesAsString() {
        if(devices.isEmpty()) {
            return "NONE";
        }

        String devicesAsString = "";
        for(SmartDevice device : devices.values()) {
            devicesAsString += device + ", ";
        }
        return devicesAsString;
    }

    public String getName() {
        return name;
    }

    public String getRoomType() {
        return type.name();
    }

    @Override
    public String toString() {
        return this.name + ":" + this.type;
    }

    public SmartDevice getDevice(String nameDevice) {
        return devices.get(nameDevice);
    }
}
