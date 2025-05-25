import java.util.List;
import java.util.Scanner;

import app.Controllers.*;
import app.Models.SmartDevice;
import app.FileLogger;
import app.Helpers.PrintHelper;
import app.Helpers.ValidatorHelper;
import app.Models.Home;
import app.Models.Room;
import app.Models.Rule;
import app.Service.HomeService;
import app.ReportService;

public class ConsoleAPP {

    private boolean running = true;
    private final List<Home> homeList;

    private final HomeController homeController;
    private final RoomController roomController;
    private final DeviceController deviceController;
    private final RuleController ruleController;


    public ConsoleAPP(){
        this.homeController = new HomeController();
        this.roomController = new RoomController();
        this.deviceController = new DeviceController(new FileLogger("smart_logs.tsv"));
        this.ruleController = new RuleController();
        this.homeList = HomeService.getInstance().getHomelist();
        this.openInterface();
    }

    private void openInterface(){
        Scanner scanner = new Scanner(System.in);
        while (this.isRunning()){
            PrintHelper.showMainMenu();
            String choice = PrintHelper.readLine("\nChoice option: ", scanner);
            this.handleUserChoice(choice , scanner);
        }
        scanner.close();
        System.out.println("\nClosing....");
    }

    private void handleUserChoice(String choice, Scanner scanner) {
        switch (choice) {
            case "1": handleHomeOptions(scanner); break;
            case "2": handleRoomOptions(scanner); break;
            case "3": handleDeviceOptions(scanner); break;
            case "4": handleRuleOptions(scanner); break;
            case "5": handleReportOptions(scanner); break;
            case "0": setRunning(false); break;
            default: System.out.println("\nUnexpected value, try again."); break;
        }
    }

    private void handleHomeOptions(Scanner scanner){
        while(true){
            PrintHelper.showHomeMenu();
            String choice = PrintHelper.readLine("\nChoice Option: " , scanner);
            if ("0".equals(choice)){
                break;
            }
            this.homeController.handleChoice( this.homeList ,choice , scanner);
        }
    }

    private void handleRoomOptions(Scanner scanner) {
        if (homeList.isEmpty()) {
            System.out.println("\nHome list is empty, add some home to get access to this options:");
            PrintHelper.waitForEnter(scanner);
            return;
        }

        System.out.println("\n=== Choice home ===");
        PrintHelper.showList(this.homeList);
        int homeIndex = ValidatorHelper.checkIndexInt("\nChoice Option: ", scanner , this.homeList);
        List<Room> rooms = this.homeList.get(homeIndex).getRooms();
        while (true) {
            PrintHelper.showRoomMenu();
            String choice = PrintHelper.readLine("\nChoice Option: ", scanner);
            if ("0".equals(choice)) {
                break;
            }
            this.roomController.handleChoice(rooms, choice, scanner);
        }
    }

    private void handleDeviceOptions(Scanner scanner){
        if (homeList.isEmpty()){
            System.out.println("\nHome list is empty, add some home to get access to this options:");
            PrintHelper.waitForEnter(scanner);
            return;
        }

        System.out.println("\n=== Choice home ===");
        PrintHelper.showList(this.homeList);
        int homeIndex = ValidatorHelper.checkIndexInt("\nChoice Option: " , scanner , this.homeList);
        List<Room> rooms = this.homeList.get(homeIndex).getRooms();

        if (rooms.isEmpty()){
            System.out.println("\nRoom list is empty, add some home to get access to this options:");
            PrintHelper.waitForEnter(scanner);
            return;
        }

        System.out.println("\n=== Choice room ===");
        PrintHelper.showList(rooms);
        int room = ValidatorHelper.checkIndexInt("\nChoice Option: " , scanner , rooms);
        Room selectedRoom = rooms.get(room);
        List<SmartDevice<?>> devices = selectedRoom.getDeviceList();
        this.deviceController.setRoom(selectedRoom);
        while (true){
            PrintHelper.showDeviceMenu();
            String choice = PrintHelper.readLine("\nChoice Option: " , scanner);
            if ("0".equals(choice)){
                break;
            }
            this.deviceController.handleChoice(devices, choice , scanner);
        }
    }

    private void handleRuleOptions(Scanner scanner){
        if (homeList.isEmpty()){
            System.out.println("\nHome list is empty, add some home to get access to this options:");
            PrintHelper.waitForEnter(scanner);
            return;
        }

        System.out.println("\n=== Choice home ===");
        PrintHelper.showList(this.homeList);
        int homeIndex = ValidatorHelper.checkIndexInt("\nChoice Option: " , scanner , this.homeList);
        List<Room> rooms = this.homeList.get(homeIndex).getRooms();

        if (rooms.isEmpty()){
            System.out.println("\nRoom list is empty, add some home to get access to this options:");
            PrintHelper.waitForEnter(scanner);
            return;
        }

        System.out.println("\n=== Choice room ===");
        PrintHelper.showList(rooms);
        int room = ValidatorHelper.checkIndexInt("\nChoice Option: " , scanner , rooms);
        Room selectedRoom = rooms.get(room);
        List<SmartDevice<?>> devices = selectedRoom.getDeviceList();
        this.deviceController.setRoom(selectedRoom);

        if (devices.isEmpty()){
            System.out.println("\nDevice list is empty, add some home to get access to this options:");
            PrintHelper.waitForEnter(scanner);
            return;
        }

        System.out.println("\n=== Choice device ===");
        PrintHelper.showList(devices);
        int device = ValidatorHelper.checkIndexInt("\nChoice Option: " , scanner , devices);
        SmartDevice<?> selectedDevice = devices.get(device);
        List<Rule> rules = selectedDevice.getRuleList();

        this.ruleController.setCurrentDevice(selectedDevice);

        while (true){
            PrintHelper.showRuleMenu();
            String choice = PrintHelper.readLine("\nChoice Option: " , scanner);
            if ("0".equals(choice)){
                break;
            }
            this.ruleController.handleChoice(rules, choice , scanner);
        }
    }

    private void handleReportOptions(Scanner scanner) {
        while (true) {
            PrintHelper.showReportMenu();
            String choice = PrintHelper.readLine("\nChoice report: ", scanner);
            if ("0".equals(choice)) break;

            switch (choice) {
                case "1": ReportService.printStatusChangeLogs();break;
                case "2": ReportService.printCurrentlyOnDevices();break;
                case "3": ReportService.printCurrentlyOffDevices();break;
                case "4": ReportService.printRuleTriggeredLogs();break;
                default: System.out.println("\nUnexpected value, try again.");
            }
            PrintHelper.waitForEnter(scanner);
        }
    }

    public boolean isRunning(){
        return this.running;
    }

    public void setRunning(boolean running){
        this.running = running;
    }
}
