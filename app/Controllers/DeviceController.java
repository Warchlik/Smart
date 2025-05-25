package app.Controllers;

import app.Models.Devices.*;
import app.FileLogger;
import app.Helpers.PrintHelper;
import app.Helpers.ValidatorHelper;
import app.Models.Room;
import app.Interfaces.Switchable;
import app.Models.SmartDevice;

import java.util.List;
import java.util.Scanner;

public class DeviceController extends Handler<SmartDevice<?>> {

    private final FileLogger fileLogger;
    private Room room;

    public DeviceController(FileLogger fileLogger) {
        this.fileLogger = fileLogger;
    }

    @Override
    public void handleChoice(List<SmartDevice<?>> list, String choice, Scanner scanner) {
        switch (choice) {
            case "1": handleAdd(list , scanner); break;
            case "2": handleEdit(list , scanner); break;
            case "3": handleShow(list , scanner); break;
            case "4": handleRemove(list , scanner); break;
            case "5": handleTurnOnInRoom(list , scanner); break;
            case "6": handleTurnOffInRoom(list , scanner); break;
            case "0": break;
            default: System.out.println("\nUnexpected value, try again."); PrintHelper.waitForEnter(scanner); break;
        }
    }

    private void handleTurnOnInRoom(List<SmartDevice<?>> devices, Scanner scanner) {
        if (devices.isEmpty()){
            System.out.println("\nDevice list is empty, add some devices to get access to this options:");
            PrintHelper.waitForEnter(scanner);
            return;
        }

        devices.stream()
                .filter(device -> device instanceof Switchable)
                .map(device -> (Switchable)device)
                .filter(device -> !device.isOn())
                .forEach(Switchable::turnOn);
        System.out.println("\nAll previously OFF devices have been turned ON.");
        PrintHelper.waitForEnter(scanner);
    }

    private void handleTurnOffInRoom(List<SmartDevice<?>> devices, Scanner scanner) {
        if (devices.isEmpty()){
            System.out.println("\nDevice list is empty, add some devices to get access to this options:");
            PrintHelper.waitForEnter(scanner);
            return;
        }

        devices.stream()
                .filter(device -> device instanceof Switchable)
                .map(device -> (Switchable)device)
                .filter(Switchable::isOn)
                .forEach(Switchable::turnOff);
        System.out.println("\nAll previously ON devices have been turned OFF.");
        PrintHelper.waitForEnter(scanner);
    }

    @Override
    protected void setAttributes(String choice, Scanner scanner, SmartDevice<?> item) {
        switch(choice){
            case "1": this.editName(item , scanner); break;
            case "2": this.editStatus(item , scanner); break;
            case "3": this.turnOnDevice((SmartDevice<?> & Switchable) item); break;
            case "4": this.turnOffDevice((SmartDevice<?> & Switchable) item); break;
            case "5": this.changeColorIfLightbulb(item , scanner); break;
            case "0": break;
            default : throw new IllegalStateException("\nUnexpected value: " + scanner);
        }
    }

    private void editName(SmartDevice<?> device , Scanner scanner){
        String newName = PrintHelper.readLine("\nType new Device Name: " , scanner);
        device.setName(newName);
        System.out.printf("\nName has been changed to: %s", device.getName());
    }

    private <E extends Enum<E>> void editStatus(SmartDevice<E> device , Scanner scanner){

        E[] choices = device.getStatus().getDeclaringClass().getEnumConstants();

        E newStatus = ValidatorHelper.checkInputValueEnum("\nChoice new Status: " , scanner , choices);

        device.setStatus(newStatus);
        System.out.printf("\nStatus has been change to: %s", device.getStatus());
    }

    private void turnOnDevice(Switchable device) {
        device.turnOn();
    }

    private void turnOffDevice(Switchable device) {
        device.turnOff();
    }

    private void changeColorIfLightbulb(SmartDevice<?> device, Scanner scanner) {
        if (!(device instanceof Lightbulb lightbulb)) {
            System.out.println("\nThis option is available only for Lightbulb devices.");
            return;
        }

        double newHue = ValidatorHelper.checkInputValueFloat("\nNew Hue [0–360]: ", scanner);
        double newSat = ValidatorHelper.checkInputValueFloat("\nNew Saturation [0–1]: ", scanner);
        double newVal = ValidatorHelper.checkInputValueFloat("\nNew Value [0–1]: ", scanner);

        try {
            lightbulb.setHue(newHue);
            lightbulb.setSaturation(newSat);
            lightbulb.setValue(newVal);
            System.out.printf("\nColor changed to: | H=%.1f | S=%.2f | V=%.2f%n", lightbulb.getHue(), lightbulb.getSaturation(), lightbulb.getValue());
            lightbulb.notifyObservers("COLOR_CHANGED", String.format("Changed HSV to [%.1f,%.2f,%.2f]", newHue, newSat, newVal));
        } catch (IllegalArgumentException e) {
            System.out.println("\nInvalid color parameter.");
        }
    }

    @Override
    protected void showEditMenu() {
        PrintHelper.showEditDeviceMenu();
    }

    @Override
    protected SmartDevice<?> generateNewObject(Scanner scanner) {
        PrintHelper.showAddDeviceMenu();
        String type = ValidatorHelper.checkInputValueString("\nType: " , scanner );
        String name = ValidatorHelper.checkInputValueString("\nDevice name: " , scanner );
        return this.choseType(type , name);
    }

    private SmartDevice<?> choseType(String type, String name) {
        SmartDevice<?> device = null;
        switch (type) {
            case "1": device = new Lightbulb(name);break;
            case "2": device = new Outlet(name);break;
            case "3": device = new TemperatureSensor(name);break;
            case "4": device = new TV(name);break;
            case "5": device = new Console(name);break;
            case "6": device = new Camera(name);break;
            default: System.out.println("\nUnexpected value.");
        }
        return device;
    }

    @Override
    protected void onAdd(SmartDevice<?> device) {
        device.addObserver(fileLogger);
        device.setRoom(this.room);
        device.notifyObservers("DEVICE_ADDED" , String.format("Added device %s.", device.getName()));
    }


    @Override
    protected void onRemove(SmartDevice<?> device) {
        device.notifyObservers("DEVICE_REMOVED", String.format("Removed device %s from room.", device.getName()));
    }

    public void setRoom(Room room){
        this.room = room;
    }
}
