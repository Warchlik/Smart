package app.Interfaces;

import app.Models.SmartDevice;

public interface DeviceObserver {
    void update(SmartDevice<?> device, String eventType, String description);
}
