public class Coordinates {
    private final Double x;
    private final Double y;

    public Coordinates(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public static Coordinates getRandom() {
        return new Coordinates(Math.random() + 1,Math.random() + 1);
    }
}
