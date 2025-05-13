//package app.Devices;
//
//import app.Interfaces.DeviceObserver;
//import app.Interfaces.ObservableDevice;
//import app.Interfaces.Switchable;
//import app.SmartEnums.DeviceEnum;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Device extends SmartDevice<DeviceEnum> implements Switchable, ObservableDevice {
//
//    private boolean on;
//    private List<DeviceObserver> observers;
//
//    public Device(String name) {
//        super(name, DeviceEnum.OFF);
//        this.on = false;
//        this.observers = new ArrayList<>();
//    }
//
//    @Override
//    public void turnOn() {
//        this.on = true;
////        notifyObservers();
//    }
//
//
//    @Override
//    public void turnOff() {
//        this.on = false;
////        notifyObservers();
//    }
//
//    @Override
//    public boolean isOn() {
//        return this.on;
//    }
//
//    @Override
//    public void addObserver(DeviceObserver observer) {
//        observers.add(observer);
//    }
//
//    @Override
//    public void removeObserver(DeviceObserver observer) {
//        observers.remove(observer);
//    }
//
//
//    @Override
//    public void notifyObservers(String eventType , String description) {
//        for (DeviceObserver observer : observers) {
//            observer.update(this , eventType , description);
//        }
//    }
//
//    @Override
//    public void simulate() {
//        if (isOn()) {
//            System.out.println(getName() + " is operating.");
//        } else {
//            System.out.println(getName() + " is not operating.");
//        }
//    }
//}