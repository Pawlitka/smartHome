package smart.devices;

public interface SensorDevice<T> {
    T readValue();
    String getUnit();
}
