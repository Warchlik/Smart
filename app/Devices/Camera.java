package app.Devices;

import app.Interfaces.Switchable;
import app.SmartEnums.CameraEnum;

public class Camera extends SmartDevice<CameraEnum> implements Switchable {

    public Camera(String name){
        super(name, CameraEnum.OFF);
    }
    @Override
    public void simulate() {

    }

    @Override
    public void turnOn() {
        if (!isOn()){
            this.setStatus(CameraEnum.ON);
            System.out.println("\nYou turn on Camera.");
        }else {
            System.out.println("\nCamera is in use now, you can not ON it second time.");
        }
    }

    @Override
    public void turnOff() {
        if (isOn()){
            this.setStatus(CameraEnum.OFF);
        }else {
            System.out.println("\nYou can not turn of Camera now.");
        }
    }

    @Override
    public boolean isOn() {
        return this.getStatus() != CameraEnum.OFF;
    }
}
