package app.Interfaces;

import app.SmartEnums.DeviceEnum;

public interface Switchable {

    void turnOn();
    void turnOff();
    boolean isOn();
}
