package app.Devices;

import app.Interfaces.Switchable;
import app.SmartEnums.TemperatureSensorEnum;
import java.util.Random;

public class TemperatureSensor extends SmartDevice<TemperatureSensorEnum> implements Switchable {

    private final Random random = new Random();
    private int temperature;

    public TemperatureSensor(String name){
        super(name , TemperatureSensorEnum.OFF);
    }

    @Override
    public void simulate() {
        int prevTemperature = this.temperature;
        Thread thread = new Thread(() -> {
            while (this.getStatus() == TemperatureSensorEnum.OFF) {
                try {
                    if (getStatus() == TemperatureSensorEnum.ACTIVE) {
                        this.temperature = random.nextInt(81) - 40;
                        System.out.println(getName() + " | Temperature: " + temperature + "°C");
                        notifyObservers("Temperature changed" , String.format("Status was changed form %s to %s" , prevTemperature , this.temperature));

                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(getName() + " | Wątek symulacji przerwany.");
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void turnOn() {
        if (!isOn()){
            this.setStatus(TemperatureSensorEnum.ON);
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
        }else {
            System.out.println("\nYou can not turn of TemperatureSensor now.");
        }
    }

    @Override
    public boolean isOn() {
        return this.getStatus() == TemperatureSensorEnum.ON;
    }
}
