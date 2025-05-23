package app.Controllers;

import app.Helpers.PrintHelper;
import app.Helpers.ValidatorHelper;
import app.Interfaces.Handler;
import app.Interfaces.Switchable;
import app.Models.Devices.*;
import app.Models.Rule;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class RuleController extends Handler<Rule> {

    private SmartDevice<?> currentDevice;

    @Override
    protected void showEditMenu() {
        PrintHelper.showEditRuleMenu();
    }

    protected void handleAdd(List<Rule> list, Scanner scanner) {
        if (!list.isEmpty()){
            System.out.println("\nYou can't add a rule because this device already has one, you can only edit it.");
            PrintHelper.waitForEnter(scanner);
            return;
        }

        System.out.println("\nAdd new:");
        Rule newObject = this.generateNewObject(scanner);
        list.add(newObject);
        this.onAdd(newObject);
        System.out.println();
        System.out.println("\nAdded: " + list.getLast());
        PrintHelper.waitForEnter(scanner);
    }

    protected Rule generateNewObject(Scanner scanner) {
        String name = ValidatorHelper.checkInputValueString("\nRule name: ", scanner);
        String description = ValidatorHelper.checkInputValueString("\nDescription: ", scanner);
        Rule rule = new Rule(name, currentDevice, d->false, d->{}, description);
        configureLogic(rule, scanner);
        return rule;
    }

    private void configureLogic(Rule rule, Scanner scanner) {
        Predicate<SmartDevice<?>> condition;
        Consumer<SmartDevice<?>> action;

        String type = currentDevice.getClass().getSimpleName();
        switch (type) {
            case "TemperatureSensor" -> {
                int staticTemperature = ValidatorHelper.checkInputValueInt("\nTrigger when temp < (°C): ", scanner);
                condition = device -> ((TemperatureSensor)device).readValue() < staticTemperature;
                action = device -> ((TemperatureSensor)device).turnOn();
            }
            case "Lightbulb" -> {
                double h = ValidatorHelper.checkIsHueRadius("\nHue [0–360]: ", scanner);
                double s = ValidatorHelper.checkIsSaturationAndValueRadius("\nSaturation [0–1]: ", scanner);
                double v = ValidatorHelper.checkIsSaturationAndValueRadius("\nValue [0–1]: ", scanner);
                condition = device -> true;
                action = device -> {
                    Lightbulb lightbulb = (Lightbulb)device;
                    if (!lightbulb.isOn()) {
                        lightbulb.turnOn();
                    }
                    lightbulb.setHue(h); lightbulb.setSaturation(s); lightbulb.setValue(v);
                };
            }
            case "TV", "Console" -> {
                int start = ValidatorHelper.checkInputValueInt("\nAllowed ON hour start (0–23): ", scanner);
                int end = ValidatorHelper.checkInputValueInt("\nAllowed ON hour end (0–23): ", scanner);
                condition = device -> {
                    int currentTime = LocalTime.now().getHour();
                    boolean isIn = (start <= end) ? (currentTime >= start && currentTime <= end) : (currentTime >= start || currentTime <= end);
                    boolean isOff = !(device instanceof Switchable switchable && switchable.isOn());
                    return isIn && isOff;
                };
                action = device -> ((Switchable)device).turnOn();

            }
            default -> {
                condition = device -> false;
                action = device -> {};
            }
        }

        rule.setCondition(condition);
        rule.setAction(action);
        System.out.println("\nLogic reconfigured.");
    }

    @Override
    protected void setAttributes(String choice, Scanner scanner, Rule item) {
        switch (choice) {
            case "1": this.editName(item, scanner);break;
            case "2": this.editDesc(item, scanner);break;
            case "3": this.configureLogic(item, scanner);break;
            case "0": break;
            default : throw new IllegalStateException("\nUnexpected value: " + scanner);
        }
    }

    private void editName(Rule rule, Scanner scanner) {
        String newName = ValidatorHelper.checkInputValueString("\nType new Rule Name: ", scanner);
        rule.setName(newName);
        System.out.printf("\nName has been changed to: %s", rule.getName());
    }

    private void editDesc(Rule rule, Scanner scanner){
        String newDesc = ValidatorHelper.checkInputValueString("\nType new Rule Description: " , scanner);
        rule.setDescription(newDesc);
        System.out.printf("\nNew Description: %s" , rule.getDescription());
    }

    protected void onAdd(Rule rule) {
        rule.getDevice().notifyObservers("RULE_ADDED", String.format("Added rule %s with condition %s", rule.getName(), rule.getDescription()));
    }

    public void setCurrentDevice(SmartDevice<?> currentDevice) {
        this.currentDevice = currentDevice;
    }
}
