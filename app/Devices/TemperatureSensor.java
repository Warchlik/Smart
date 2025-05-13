package app.Devices;

import app.SmartEnums.TemperatureSensorEnum;

public class TemperatureSensor extends SmartDevice<TemperatureSensorEnum> {

    public TemperatureSensor(String name){
        super(name , TemperatureSensorEnum.OFF);
    }

    @Override
    public void simulate() {

    }
}
