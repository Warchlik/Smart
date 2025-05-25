package app.Models.Devices;

import app.Helpers.ValidatorHelper;
import app.Interfaces.Switchable;
import app.Models.SmartDevice;
import app.SmartEnums.ConsoleEnum;
import app.SmartEnums.GameEnum;

public class Console extends SmartDevice<ConsoleEnum> implements Switchable {

    private GameEnum game;
    public Console(String name) {
        super(name, ConsoleEnum.OFF);
    }

    @Override
    public void simulate() {
        Thread thread = new Thread(() -> {
            synchronized (this) {
                try {
                    while (isOn()) {
                        GameEnum prevGame = this.game;
                        this.setGame(ValidatorHelper.getRandomEnumValue(GameEnum.values()) , prevGame);
                        notifyObservers("CHANGED_GAME", String.format("Your console is JailBrake :) and now play: %s" , this.getGame()));
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
            this.setStatus(ConsoleEnum.ON);
            this.simulate();
            System.out.println("\nYou turn on Console.");
        }else {
            System.out.println("\nConsole is in use now, you can not ON it second time.");
        }
    }

    @Override
    public void turnOff() {
        if (isOn()){
            this.setStatus(ConsoleEnum.OFF);
            synchronized (this){
                this.notifyAll();
            }
        }else {
            System.out.println("\nYou can not turn of Console now.");
        }
    }

    @Override
    public boolean isOn() {
        return this.getStatus() != ConsoleEnum.OFF;
    }

    public GameEnum getGame(){
        return this.game;
    }

    public void setGame(GameEnum game , GameEnum prevGame){
        if (game == prevGame){
            this.setGame(ValidatorHelper.getRandomEnumValue(GameEnum.values()) , prevGame);
        }else {
            this.game = game;
        }
    }
}
