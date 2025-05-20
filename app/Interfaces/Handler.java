package app.Interfaces;

import app.Devices.TemperatureSensor;
import app.Helpers.PrintHelper;
import app.Helpers.ValidatorHelper;

import java.util.List;
import java.util.Scanner;

public abstract class Handler<T> {

    public void handleChoice(List<T> homeList, String choice, Scanner scanner) {
        switch (choice) {
            case "1": handleAdd(homeList , scanner); break;
            case "2": handleEdit(homeList , scanner); break;
            case "3": handleShow(homeList , scanner); break;
            case "4": handleRemove(homeList , scanner); break;
            case "0": break;
            default: System.out.println("\nUnexpected value, try again."); PrintHelper.waitForEnter(scanner); break;
        }
    }

    protected void handleAdd(List<T> list, Scanner scanner) {
        System.out.println("Add new");
        T newObject = this.generateNewObject(scanner);
        list.add(newObject);
        this.onAdd(newObject);
        System.out.println();
        System.out.println("Added: " + list.getLast());
        PrintHelper.waitForEnter(scanner);
    }

    protected void handleEdit(List<T> list ,Scanner scanner) {
        if (list.isEmpty()){
            System.out.println("\nNo items to edit.");
            PrintHelper.waitForEnter(scanner);
            return;
        }
        printList(list);
        int index = ValidatorHelper.checkIndexInt("\nChose item from list: " , scanner , list);
        while (true){
            try{
                this.showEditMenu();
                String attribute = PrintHelper.readLine("\nChose attribute to change: " , scanner);
                if ("0".equals(attribute)){
                    break;
                }
                T editItem = list.get(index);
                this.setAttributes(attribute , scanner , editItem);
                this.onEdit(editItem);
                PrintHelper.waitForEnter(scanner);
                break;
            }catch (Exception e){
                System.out.println("\nUnexpected value.");
            }
        }
    }

    protected void handleRemove(List<T> list , Scanner scanner) {
        if (list.isEmpty()) {
            System.out.println("\nNo items to remove.");
            PrintHelper.waitForEnter(scanner);
        }
        while (true){
            try {
                printList(list);
                String index = PrintHelper.readLine("\nChose Item from list to remove: ", scanner);
                T removedItem = list.remove(Integer.parseInt(index));
                this.onRemove(removedItem);
                System.out.println("\nItem has been removed.");
                PrintHelper.waitForEnter(scanner);
                break;
            } catch (Exception e) {
                System.out.println("\nInvalid number of item from list.");
                PrintHelper.waitForEnter(scanner);
            }
        }
    }

    protected void handleShow(List<T> list , Scanner scanner) {
        if (list.isEmpty()) {
            System.out.println("\nNo items to show.");
            PrintHelper.waitForEnter(scanner);
        } else {
            printViewList(list);
            PrintHelper.waitForEnter(scanner);
        }
    }

    protected void printList(List<T> list) {
        System.out.println("\n=== Choice Item From List ===");
        PrintHelper.showList(list);
    }

    protected void printViewList(List<T> list){
        System.out.println("\n--- CURRENT LIST ---");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " -> " + list.get(i));
        }
    }

    //hooks
    protected void onAdd(T item){}
    protected void onEdit(T item ){}
    protected void onRemove(T item){}

    protected abstract void setAttributes(String choice, Scanner scanner, T item);
    protected abstract void showEditMenu();
    protected abstract T generateNewObject(Scanner scanner);
}
