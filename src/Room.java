import smart.devices.SmartDevice;

import java.util.ArrayList;

public class Room {
    private final String name;
    private final RoomType type;
    private final ArrayList<SmartDevice> devices = new ArrayList<>();

    public Room(String name, RoomType type) {
        this.name = name;
        this.type = type;
    }

    public void addDevice(SmartDevice device) {
        devices.add(device);
    }

    public void removeDevice(SmartDevice device) {
        devices.remove(device);
    }

    public ArrayList<SmartDevice> getDevices() {
        return devices;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.type;
    }
}
