import smartDevices.DevicesManager;
import smartDevices.SmartDevice;
import java.util.HashMap;

public class Room {
    private final String name;
    private final RoomType type;
    private final DevicesManager devicesManager = new DevicesManager();

    public Room(String name, RoomType type) {
        this.name = name;
        this.type = type;
    }

    public void addDevice(SmartDevice device) {
        devicesManager.addDevice(device);
    }

    public void deleteDevice(String deviceName) {
        devicesManager.deleteDevice(deviceName);
    }

    public HashMap<String, SmartDevice> getDevices() {
        return devicesManager.getDevices();
    }

    public String getDevicesAsString() {
        if(devicesManager.getDevices().isEmpty()) {
            return "NONE";
        }

        String devicesAsString = "";
        for(SmartDevice device : devicesManager.getDevices().values()) {
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
        return devicesManager.getDevices().get(nameDevice);
    }
}
