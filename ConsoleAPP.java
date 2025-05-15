import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.Controllers.DeviceController;
import app.Controllers.HomeController;
import app.Controllers.RoomController;
import app.Devices.SmartDevice;
import app.FileLogger;
import app.Helpers.PrintHelper;
import app.Helpers.ValidatorHelper;
import app.House.Home;
import app.House.Room;

public class ConsoleAPP {

    private boolean running = true;
    private final List<Home> homeList = new ArrayList<>();

    private final HomeController homeController;
    private final RoomController roomController;
    private final DeviceController deviceController;

    public ConsoleAPP(){
        this.homeController = new HomeController();
        this.roomController = new RoomController();
        this.deviceController = new DeviceController(new FileLogger("smart_logs.tsv"));
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
        String homeIndex = ValidatorHelper.checkIndexString("\nChoice Option: ", scanner , this.homeList);
        List<Room> rooms = this.homeList.get(Integer.parseInt(homeIndex)).getRooms();
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
        String homeIndex = ValidatorHelper.checkIndexString("\nChoice Option: " , scanner , this.homeList);
        List<Room> rooms = this.homeList.get(Integer.parseInt(homeIndex)).getRooms();

        if (rooms.isEmpty()){
            System.out.println("\nRoom list is empty, add some home to get access to this options:");
            PrintHelper.waitForEnter(scanner);
            return;
        }

        System.out.println("\n=== Choice room ===");
        PrintHelper.showList(rooms);
        String room = ValidatorHelper.checkIndexString("\nChoice Option: " , scanner , rooms);
        Room selectedRoom = rooms.get(Integer.parseInt(room));
        List<SmartDevice<?>> devices = selectedRoom.getDeviceList();
        this.deviceController.setRoomName(selectedRoom);
        while (true){
            PrintHelper.showDeviceMenu();
            String choice = PrintHelper.readLine("\nChoice Option: " , scanner);
            if ("0".equals(choice)){
                break;
            }
            this.deviceController.handleChoice(devices, choice , scanner);
        }
    }

    public boolean isRunning(){
        return this.running;
    }

    public void setRunning(boolean running){
        this.running = running;
    }
}
