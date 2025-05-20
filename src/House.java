public class House {
    private String houseName;
    private final Integer[] Coordinates;
    private Integer areaSize;
    private Integer numberOfRooms;
    private Integer numberOfBathrooms;
    private String houseAdres;
    private String name;

    public House(String houseName, Integer[] coordinates, Integer areaSize) {
        this.houseName = houseName;
        this.Coordinates = coordinates;
        this.areaSize = areaSize;
    }
}
