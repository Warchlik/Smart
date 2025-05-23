package app.Models;

import app.Models.Devices.Lightbulb;
import app.Models.Devices.Outlet;
import app.Models.Devices.SmartDevice;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Rule {
    private final SmartDevice<?> device;
    private volatile Predicate<SmartDevice<?>> condition;
    private volatile Consumer<SmartDevice<?>> action;
    private String description;
    private String name;

    public Rule(String name , SmartDevice<?> device, Predicate<SmartDevice<?>> condition, Consumer<SmartDevice<?>> action, String description) {
        this.device = device;
        this.condition = condition;
        this.action = action;
        this.description = description;
        this.name = name;
        this.startChecker();
    }

    @Override
    public String toString() {
        return this.name + " [on " + this.device.getName() + "|" + this.device.getClass().getSimpleName() + "|" + this.description +"]";
    }

    public void checkConditionAndStartAction() {
        if (this.condition.test(this.device)) {
            this.action.accept(this.device);
            this.device.notifyObservers("RULE_TRIGGERED", String.format( "Rule %s triggered on %s", this.description, this.device.getClass().getSimpleName() ));
        }
    }


    private void startChecker() {
        Thread checker = new Thread(() -> {
            while (true) {
                checkConditionAndStartAction();
                synchronized (this) {
                    try {
                        this.wait(6000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }, "Checker - "+ this.name);
        checker.setDaemon(true);
        checker.start();
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }

    public SmartDevice<?> getDevice(){
        return this.device;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCondition(Predicate<SmartDevice<?>> condition) {
        synchronized(this) {
            this.condition = condition;
            this.notifyAll();
        }
    }
    public void setAction(Consumer<SmartDevice<?>> action) {
        synchronized(this) {
            this.action = action;
        }
    }
}

