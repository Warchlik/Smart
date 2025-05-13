package app.Interfaces;

public interface ObservableDevice {

    void addObserver(DeviceObserver observer);
    void removeObserver(DeviceObserver observer);
    void notifyObservers(String eventType, String description);
}
