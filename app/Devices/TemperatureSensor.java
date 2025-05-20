package app.Devices;

import app.Interfaces.SensorDevice;
import app.Interfaces.Switchable;
import app.SmartEnums.TemperatureSensorEnum;
import java.util.Random;

public class TemperatureSensor extends SmartDevice<TemperatureSensorEnum> implements Switchable , SensorDevice<Integer> {

    private final Random random = new Random();
    private int temperature;

    public TemperatureSensor(String name){
        super(name , TemperatureSensorEnum.OFF);
    }

    @Override
    public void simulate() {
        Thread thread = new Thread(() -> {
            synchronized (this) {
                try {
                    while (isOn()) {
                        int prevTemperature = this.temperature;
                        this.temperature = random.nextInt(81) - 40;
                        notifyObservers(
                                "CHANGED_TEMPERATURE",
                                String.format("Temperature changed from %d to %d", prevTemperature, this.temperature)
                        );
                        this.wait(5000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(getName() + " | Simulation thread interrupted.");
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    protected boolean checkStatusForSensor(){
        return this.getStatus() == TemperatureSensorEnum.ON || this.getStatus() == TemperatureSensorEnum.ACTIVE;
    }

    @Override
    public void turnOn() {
        if (!isOn()){
            this.setStatus(TemperatureSensorEnum.ON);
            this.simulate();
            System.out.println("\nYou turn ON TemperatureSensor.");
        }else {
            System.out.println("\nTemperatureSensor is in use now, you can not ON it second time.");
        }

    }

    @Override
    public void turnOff() {
        if (isOn()){
            this.setStatus(TemperatureSensorEnum.OFF);
            System.out.println("\nYou turn OFF TemperatureSensor.");
            synchronized (this) {
                this.notifyAll();
            }
        }else {
            System.out.println("\nYou can not turn of TemperatureSensor now.");
        }
    }

    @Override
    public boolean isOn() {
        return this.getStatus() != TemperatureSensorEnum.OFF;
    }

    @Override
    public String getUnit() {
        return this.getClass().getName();
    }

    @Override
    public Integer readValue() {
        return this.temperature;
    }
}
