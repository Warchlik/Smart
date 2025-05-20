package app.Devices;

import app.Interfaces.Switchable;
import app.SmartEnums.DefaultDeviceEnum;

public class Outlet extends SmartDevice<DefaultDeviceEnum> implements Switchable {
    private boolean inUse;

    public Outlet(String name) {
        super(name , DefaultDeviceEnum.OFF);
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
        if (!isOn()){
            setStatus(DefaultDeviceEnum.ON);
            this.simulate();
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
            if (isOn()){
                System.out.println("Outlet is OFF, you have to ON it before turn OFF.");
            }else {
                this.setStatus(DefaultDeviceEnum.OFF);
                this.setInUse(false);
                synchronized (this) {
                    this.notifyAll();
                }
            }
        }
    }

    @Override
    public boolean isOn() {
        return this.getStatus() != DefaultDeviceEnum.OFF;
    }

    @Override
    public void simulate() {
        Thread thread = new Thread(() -> {
            synchronized (this) {
                try {
                    while (isOn()) {
                        boolean prevUseStatus = this.isInUse();
                        this.setInUse(!this.isInUse());
                        notifyObservers("CHANGED_USE_STATUS", String.format("Temperature changed from %B to %B", prevUseStatus , this.isInUse()));
                        this.wait(5000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }
}
