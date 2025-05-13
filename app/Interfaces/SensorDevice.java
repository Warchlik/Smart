package app.Interfaces;

public interface SensorDevice<T>{

    String getUnit();
    T readValue();
}
