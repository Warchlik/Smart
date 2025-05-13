package app.Devices;

import app.Interfaces.Switchable;
import app.SmartEnums.DeviceEnum;

public class Outlet extends SmartDevice<DeviceEnum> implements Switchable {
    private boolean inUse;

    public Outlet(String name) {
        super(name , DeviceEnum.OFF);
        this.inUse = false;
    }

    public void setInUse(boolean inUse) {
        if (inUse && !isOn()) {
            throw new IllegalStateException("Cannot set in use when the outlet is off");
        }
        this.inUse = inUse;
    }

    public boolean isInUse() {
        return inUse;
    }

    @Override
    public void turnOn() {
        if (getStatus() == DeviceEnum.OFF){
            setStatus(DeviceEnum.ON);
            System.out.println("You turn on Outlet.");
        }else {
            System.out.println("Your device is already ON.");
        }
    }

    @Override
    public void turnOff() {
        if (isInUse()){
            System.out.println("Outlet in use, you can not turnOff outlet.");
        }else {
            if (getStatus() == DeviceEnum.OFF){
                System.out.println("Outlet is OFF, you have to ON it before turn OFF.");
            }else {
                this.setStatus(DeviceEnum.OFF);
                this.setInUse(false);
            }
        }
    }

    @Override
    public boolean isOn() {
        return this.getStatus() == DeviceEnum.ON;
    }

    @Override
    public void simulate() {

    }
}
