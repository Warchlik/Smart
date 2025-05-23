package app.Controllers;

import java.util.Scanner;
import app.Models.Home;
import app.Helpers.*;
import app.Interfaces.Handler;

public class HomeController extends Handler<Home> {

    @Override
    protected void showEditMenu() {
        PrintHelper.showHouseEditMenu();
    }

    @Override
    public Home generateNewObject(Scanner scanner){
        String name = ValidatorHelper.checkInputValueString("Name: " , scanner );
        float size = ValidatorHelper.checkInputValueFloat("Size [type in number]: " , scanner );
        int floors = ValidatorHelper.checkInputValueInt("Floors [type in number]: " , scanner );
        int rooms = ValidatorHelper.checkInputValueInt("Rooms [type in number]: " , scanner );
        return new Home(name, size, floors , rooms);
    }

    @Override
    public void setAttributes(String chose , Scanner scanner , Home home ){
        switch(chose){
            case "1": this.editName(home , scanner); break;
            case "2": this.editSize(home , scanner); break;
            case "3": this.editFloors(home , scanner); break;
            case "4": this.editRoomsValue(home , scanner); break;
            case "0": break;
            default : throw new IllegalStateException("Unexpected value: " + scanner);
        }
    }

    private void editName(Home home , Scanner scanner){
        String newName = ValidatorHelper.checkInputValueString("Type new house Name: " , scanner);
        home.setName(newName);
        System.out.printf("\nNew Floors: %s" , home.getName());
    }

    private void editSize(Home home , Scanner scanner){
        float newSize = ValidatorHelper.checkInputValueFloat("Type new house Name: " , scanner);
        home.setSize(newSize);
        System.out.printf("\nNew Floors: %f" , home.getSize());
    }

    private void editFloors(Home home, Scanner scanner){
        int newFloors = ValidatorHelper.checkInputValueInt("Type new Floors: " , scanner);
        home.setFloors(newFloors);
        System.out.printf("\nNew Floors: %d" , home.getFloors());
    }

    private void editRoomsValue(Home home , Scanner scanner){
        int newRoomsValue = ValidatorHelper.checkInputValueInt("Type new Rooms value: " , scanner);
        home.setRoomCount(newRoomsValue);
        System.out.printf("\nNew Floors: %d" , home.getRoomsValue());
    }
}
