package app.Devices;

import app.Interfaces.Switchable;
import app.SmartEnums.ConsoleEnum;

public class Console extends SmartDevice<ConsoleEnum> implements Switchable {
    public Console(String name) {
        super(name, ConsoleEnum.OFF);
    }

    @Override
    public void simulate() {

    }

    @Override
    public void turnOn() {
        if (!isOn()){
            this.setStatus(ConsoleEnum.ON);
            System.out.println("\nYou turn on Console.");
        }else {
            System.out.println("\nConsole is in use now, you can not ON it second time.");
        }
    }

    @Override
    public void turnOff() {
        if (isOn()){
            this.setStatus(ConsoleEnum.OFF);
        }else {
            System.out.println("\nYou can not turn of Console now.");
        }
    }

    @Override
    public boolean isOn() {
        return this.getStatus() != ConsoleEnum.OFF;
    }
}
