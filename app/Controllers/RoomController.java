package app.Controllers;

import app.Helpers.PrintHelper;
import app.Helpers.ValidatorHelper;
import app.Models.Room;
import app.SmartEnums.RoomEnum;

import java.util.Scanner;

public class RoomController extends Handler<Room> {

    @Override
    protected Room generateNewObject(Scanner scanner) {
        RoomEnum type = ValidatorHelper.checkInputValueEnum("\nChoice new Type: " , scanner , RoomEnum.values());
        String name = ValidatorHelper.checkInputValueString("\nRoom name: " , scanner );
        return new Room(name , type);
    }

    @Override
    protected void showEditMenu() {
        PrintHelper.showRoomEditMenu();
    }

    @Override
    protected void setAttributes(String chose, Scanner scanner, Room room){
        switch(chose){
            case "1": this.editName(room , scanner); break;
            case "2": this.editType(room , scanner); break;
            case "0": break;
            default : throw new IllegalStateException("\nUnexpected value: " + scanner);
        }
    }

    private void editName(Room room , Scanner scanner){
        String newName = ValidatorHelper.checkInputValueString("\nType new house Name: " , scanner);
        room.setName(newName);
    }

    private void editType(Room room, Scanner scanner){
        RoomEnum newType = ValidatorHelper.checkInputValueEnum("\nChoice new Type: ", scanner ,RoomEnum.values() );
        room.setType(newType);
        System.out.printf("\nStatus has been change to: %s", room.getType());
    }
}
