package app.Controllers;

import app.Devices.*;
import app.Helpers.PrintHelper;
import app.Helpers.ValidatorHelper;
import app.Interfaces.Handler;
import app.Interfaces.Switchable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeviceController extends Handler<SmartDevice<?>> {

    @Override
    protected void setAttributes(String choice, Scanner scanner, SmartDevice<?> item) {
        switch(choice){
            case "1": this.editName(item , scanner); break;
            case "2": this.editStatus(item , scanner); break;
            case "3": this.turnOnDevice((SmartDevice<?> & Switchable) item); break;
            case "4": this.turnOffDevice((SmartDevice<?> & Switchable) item); break;
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

//        List<E> filtered = new ArrayList<>();
//        for (E e : enumList) {
//            if ("ON".equals(e.name()) || "OFF".equals(e.name())) {
//                filtered.add(e);
//            }
//        }
//
//        choices = (E[]) filtered.toArray();

        E newStatus = ValidatorHelper.checkInputValueEnum("\nChoice new Status: " , scanner ,choices);

        device.setStatus(newStatus);
        System.out.printf("\nStatus has been change to: %s", device.getStatus());
    }

    private void turnOnDevice(Switchable device) {
        device.turnOn();
    }

    private void turnOffDevice(Switchable device) {
        device.turnOff();
    }

    @Override
    protected void showEditMenu() {
        PrintHelper.showEditDeviceMenu();
    }

    @Override
    protected SmartDevice<?> generateNewObject(Scanner scanner) {
        PrintHelper.showAddDeviceMenu();
        String type = ValidatorHelper.checkInputValueString("\nType: " , scanner );
        String name = ValidatorHelper.checkInputValueString("\nRoom name: " , scanner );
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
            default:
                System.out.println("\nUnexpected value.");
        }
        return device;
    }
}
