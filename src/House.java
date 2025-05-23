import java.util.ArrayList;

public class House {
    private final String name;
    private final Coordinates coordinates;
    private final Integer areaSize;
    private Integer numberOfRooms;
    private final String address;
    private final ArrayList<Room> rooms;

    public House(String name, Integer areaSize, String address) {
        this.rooms = new ArrayList<>();
        this.name = name;
        this.coordinates = Coordinates.getRandom();
        this.areaSize = areaSize;
        this.numberOfRooms = 0;
        this.address = address;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        numberOfRooms++;
        System.out.println(room.getName() + " has been added to house.");
    }

    public void deleteRoom(Room room) {
        rooms.remove(room);
        numberOfRooms--;
        System.out.println(room + " has been deleted to house.");
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void getNumberOfRooms() {
        System.out.println("There is: " + numberOfRooms + " rooms is this house.");
    }

    public Integer getAreaSize() {
        System.out.println("Area size of this house is: " + areaSize +"m².");
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
