package app.Models;

import app.Interfaces.DeviceObserver;
import app.Interfaces.ObservableDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class SmartDevice<E extends Enum<E>> implements ObservableDevice {

    private UUID id;
    private String name;
    private E status;
    private final List<DeviceObserver> observableDeviceList = new ArrayList<>();
    private Room room;
    private final List<Rule> ruleList = new ArrayList<>();

    public SmartDevice(String name , E defaultStatus) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.status = defaultStatus;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " | Name: " + this.name + " | ID: " + this.id + " | Status: " + this.status;
    }

    @Override
    public void addObserver(DeviceObserver observer) {
        this.observableDeviceList.add(observer);
    }

    @Override
    public void removeObserver(DeviceObserver observer) {
        this.observableDeviceList.remove(observer);
    }

    @Override
    public void notifyObservers(String eventType, String description) {
        for (DeviceObserver device : observableDeviceList) {
            device.update(this, eventType, description);
        }
    }

    public void setStatus(E status) {
        if (status != null) {
            E prevStatus = this.status;
            this.status = status;
            notifyObservers("CHANGED_STATUS" , String.format("Status was changed form %s to %s" , prevStatus , this.status));
        } else {
            throw new IllegalArgumentException("Incorrect device status.");
        }
    }

    public void setName(String name){
        String prevName = this.name;
        this.name = name;
        notifyObservers("CHANGED_NAME" , String.format("Status was changed form %s to %s" , prevName , this.name));
    }

    public E getStatus() {
        return this.status;
    }

    public String getName() {
        return this.name;
    }

    public UUID getId(){
        return this.id;
    }

    public List<DeviceObserver> getObservableDeviceList() {
        return observableDeviceList;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public Room getRoomName(){
        return this.room;
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public abstract void simulate();
}
