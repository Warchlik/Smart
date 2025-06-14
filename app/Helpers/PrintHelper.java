package app.Helpers;

import java.util.List;
import java.util.Scanner;

public class PrintHelper {

    private PrintHelper(){}

    public static String readLine(String string, Scanner scanner) {
        System.out.print(string);
        return scanner.nextLine().trim();
    }

    public static void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1) Home Options");
        System.out.println("2) Room Options");
        System.out.println("3) Device Options");
        System.out.println("4) Rule Options");
        System.out.println("5) Generate report");
        System.out.println("0) Exit");
    }

    public static void showHomeMenu() {
        System.out.println("\n=== Home Menu ===");
        System.out.println("1) Add home");
        System.out.println("2) Edit home");
        System.out.println("3) Show home list");
        System.out.println("4) Delete home");
        System.out.println("0) Back");
    }

    public static void showHouseEditMenu() {
        System.out.println("\n=== Edit Home Menu ===");
        System.out.println("1) Edit Name");
        System.out.println("2) Edit Size");
        System.out.println("3) Edit Floors");
        System.out.println("4) Edit Rooms");
        System.out.println("0) Back");
    }

    public static void showRoomEditMenu() {
        System.out.println("\n=== Edit Room Menu ===");
        System.out.println("1) Edit Name");
        System.out.println("2) Edit Type");
        System.out.println("0) Back");
    }

    public static void showRoomMenu(){
        System.out.println("\n=== Room Menu ===");
        System.out.println("1) Add room");
        System.out.println("2) Edit room");
        System.out.println("3) Show room list");
        System.out.println("4) Delete room");
        System.out.println("0) Back");
    }

    public static void showAddRoomMenu(){
        System.out.println("\n--- Choice Room Type ---");
        System.out.println("1) Kitchen");
        System.out.println("2) Showroom");
        System.out.println("3) Bedroom");
        System.out.println("4) Toilet");
        System.out.println("5) Bathroom");
        System.out.println("6) Garage");
        System.out.println("7) Attic");
        System.out.println("8) Hall");
    }

    public static void showDeviceMenu(){
        System.out.println("\n=== Device Menu ===");
        System.out.println("1) Add device");
        System.out.println("2) Edit device");
        System.out.println("3) Show device list");
        System.out.println("4) Delete device");
        System.out.println("5) Turn ON all device in this room");
        System.out.println("6) Turn OFF all device in this room");
        System.out.println("0) Back");
    }

    public static void showRuleMenu(){
        System.out.println("\n=== Rule Menu ===");
        System.out.println("1) Add rule");
        System.out.println("2) Edit rule");
        System.out.println("3) Show rule list");
        System.out.println("4) Delete rule");
        System.out.println("0) Back");
    }

    public static void showGlobalRuleMenu(){
        System.out.println("\n=== Global Rule Menu ===");
        System.out.println("1) Add global rule");
        System.out.println("2) Edit global rule");
        System.out.println("3) Show global rule list");
        System.out.println("4) Delete global rule");
        System.out.println("0) Back");
    }

    public static void showRuleEditMenu(){
        System.out.println("\n=== Select Attribute For Rule ===");
        System.out.println("1) Name");
        System.out.println("0) Back");
    }

    public static void showRulesVariant(){
        System.out.println("\n=== Rule Scope ===");
        System.out.println("1) Home Rules (all devices in home)");
        System.out.println("2) Show Rules List");
        System.out.println("0) Back");
    }

    public static void showAddDeviceMenu(){
        System.out.println("\n=== ADD Device Menu ===");
        System.out.println("1) Lightbulb");
        System.out.println("2) Outlet");
        System.out.println("3) Temperature Sensor");
        System.out.println("4) TV");
        System.out.println("5) PlayStation");
        System.out.println("6) Camera");
    }

    public static void showEditDeviceMenu(){
        System.out.println("\n=== Select Attribute For Device ===");
        System.out.println("1) Name");
        System.out.println("2) Status");
        System.out.println("3) Turn ON");
        System.out.println("4) Turn OFF");
        System.out.println("5) Change Color [ONLY FOR LIGHTBULB]");
        System.out.println("0) Back");
    }

    public static void showDeviceTypeMenu(){
        System.out.println("\n=== Device Type For Choice ===");
        System.out.println("1) Lightbulb");
        System.out.println("2) Outlet");
        System.out.println("3) Temperature Sensor");
        System.out.println("4) TV");
        System.out.println("5) PlayStation");
        System.out.println("6) Camera");
    }

    public static void showEditRuleMenu(){
        System.out.println("\n=== Edit Rule ===");
        System.out.println("1) Change name");
        System.out.println("2) Change description");
        System.out.println("3) Reconfigure logic");
        System.out.println("0) Back");
    }

    public static void showReportMenu(){
        System.out.println("\n=== Report Menu ===");
        System.out.println("1) All status‐change log entries");
        System.out.println("2) Devices currently ON");
        System.out.println("3) Devices currently OFF");
        System.out.println("4) All rule‐triggered entries");
        System.out.println("0) Back");
    }

    public static <T> void showList(List<T> list){
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ") " + list.get(i));
        }
    }

    public static <T> void printViewList(List<T> list){
        System.out.println("\n--- CURRENT LIST ---");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " -> " + list.get(i));
        }
    }

    public static void waitForEnter(Scanner scanner) {
        System.out.print("\nPress [Enter] to return to main menu...");
        scanner.nextLine();
    }
}
