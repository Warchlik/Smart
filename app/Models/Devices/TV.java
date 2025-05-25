package app.Models.Devices;

import app.Helpers.ValidatorHelper;
import app.Interfaces.Switchable;
import app.Models.SmartDevice;
import app.SmartEnums.FilmEnum;
import app.SmartEnums.TVEnum;

public class TV extends SmartDevice<TVEnum> implements Switchable {

    private FilmEnum film;
    public TV(String name) {
        super(name , TVEnum.OFF);
    }

    @Override
    public void simulate() {
        Thread thread = new Thread(() -> {
            synchronized (this) {
                try {
                    while (isOn()) {
                        FilmEnum prevFilm = this.film;
                        this.setFilm(ValidatorHelper.getRandomEnumValue(FilmEnum.values()) , prevFilm);
                        notifyObservers("CHANGED_GAME", String.format("Your console is JailBrake :) and now play: %s" , this.getFilm()));
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
            this.setStatus(TVEnum.ON);
            this.simulate();
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

    public FilmEnum getFilm(){
        return this.film;
    }

    public void setFilm(FilmEnum film , FilmEnum prevFilm){
        if (film == prevFilm){
            this.setFilm(ValidatorHelper.getRandomEnumValue(FilmEnum.values()) , prevFilm);
        }else {
            this.film = film;
        }
    }
}
