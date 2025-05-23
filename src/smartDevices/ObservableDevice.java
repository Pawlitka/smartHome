package smartDevices;

public interface ObservableDevice {
    void addObserver(DeviceObserver observer);

    void removeObserver(DeviceObserver observer);

    void notifyObservers();
}
