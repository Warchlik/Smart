package app.Devices;

import java.util.UUID;

public abstract class SmartDevice<E extends Enum<E>> {

    private UUID id;
    private String name;
    private E status;

    public SmartDevice(String name , E defaultStatus) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.status = defaultStatus;
    }

    @Override
    public String toString() {
        return this.name + " | ID: " + this.id + " | Status: " + this.status;
    }

    public abstract void simulate();

    public void setStatus(E status) {
        if (status != null) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Incorrect device status.");
        }
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

    public void setName(String name){
        this.name = name;
    }
}
