package app.Devices;

import app.Interfaces.Switchable;
import app.SmartEnums.TVEnum;

public class TV extends SmartDevice<TVEnum> implements Switchable {

    public TV(String name) {
        super(name , TVEnum.OFF);
    }

    @Override
    public void simulate() {

    }

    @Override
    public void turnOn() {
        if (!isOn()){
            this.setStatus(TVEnum.ON);
            System.out.println("\nYou turn on TV.");
        }else {
            System.out.println("\nTV is in use now, you can not ON it second time.");
        }
    }

    @Override
    public void turnOff() {
        if (isOn()){
            this.setStatus(TVEnum.OFF);
        }else {
            System.out.println("\nYou can not turn of TV now.");
        }
    }

    @Override
    public boolean isOn() {
        return this.getStatus() != TVEnum.OFF;
    }
}
