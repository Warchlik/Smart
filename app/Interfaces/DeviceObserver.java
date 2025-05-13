package app.Interfaces;

import app.Devices.SmartDevice;

public interface DeviceObserver {
    void update(SmartDevice<?> device, String eventType, String description);
}
