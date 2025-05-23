package app.Models.Devices;

import app.Interfaces.SensorDevice;
import app.Interfaces.Switchable;
import app.SmartEnums.CameraEnum;

public class Camera extends SmartDevice<CameraEnum> implements Switchable , SensorDevice<Boolean> {

    private boolean detected;
    public Camera(String name){
        super(name, CameraEnum.OFF);
        this.detected = false;
    }

    @Override
    public void simulate() {
        Thread thread = new Thread(() -> {
            synchronized (this) {
                try {
                    while (isOn()) {
                        this.setDetected(!this.isDetected());
                        notifyObservers("DETECTED_STATUS", String.format("Someone has been detected | Value: %B" , this.readValue()));
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

    @Override
    public void turnOn() {
        if (!isOn()){
            this.setStatus(CameraEnum.ON);
            this.simulate();
            System.out.println("\nYou turn on Camera.");
        }else {
            System.out.println("\nCamera is in use now, you can not ON it second time.");
        }
    }

    @Override
    public void turnOff() {
        if (isOn()){
            this.setStatus(CameraEnum.OFF);
            synchronized (this) {
                this.notifyAll();
            }
            this.notifyObservers("CHANGED_STATUS", String.format("Device was turn %s",  this.getStatus()));
        }else {
            System.out.println("\nYou can not turn of Camera now.");
        }
    }

    @Override
    public boolean isOn() {
        return this.getStatus() != CameraEnum.OFF;
    }

    public boolean isDetected() {
        return detected;
    }

    public void setDetected(boolean detected){
        this.detected = detected;
    }

    @Override
    public String getUnit() {
        return this.getClass().getName();
    }

    @Override
    public Boolean readValue() {
        return this.detected;
    }
}
