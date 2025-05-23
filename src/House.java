import java.util.ArrayList;
import java.util.HashMap;

public class House {
    private final String name;
    private final Coordinates coordinates;
    private final Integer areaSize;
    private Integer numberOfRooms;
    private final String address;
    private final HashMap<String, Room> rooms;

    public House(String name, Integer areaSize, String address) {
        this.rooms = new HashMap<>();
        this.name = name;
        this.coordinates = Coordinates.getRandom();
        this.areaSize = areaSize;
        this.numberOfRooms = 0;
        this.address = address;
    }

    public void addRoom(Room room) {
        rooms.put(room.getName(), room);
        numberOfRooms++;
    }

    public void deleteRoom(Room room) {
        rooms.put(room.getName(), room);
        numberOfRooms--;
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public Integer getAreaSize() {
        return areaSize;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
