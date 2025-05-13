package app.Interfaces;

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
        list.add(this.generateNewObject(scanner));
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
        String index = ValidatorHelper.checkIndexString("\nChose item from list: " , scanner , list);
        while (true){
            try{
                this.showEditMenu();
                String attribute = PrintHelper.readLine("\nChose attribute to change: " , scanner);
                if ("0".equals(attribute)){
                    break;
                }
                this.setAttributes(attribute , scanner , list.get(Integer.parseInt(index)));
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
                list.remove(Integer.parseInt(index));
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
            printList(list);
            PrintHelper.waitForEnter(scanner);
        }
    }

    protected void printList(List<T> list) {
        System.out.println("\n--- CURRENT LIST ---");
        PrintHelper.showList(list);
    }

    protected abstract void setAttributes(String choice, Scanner scanner, T item);
    protected abstract void showEditMenu();
    protected abstract T generateNewObject(Scanner scanner);
}
