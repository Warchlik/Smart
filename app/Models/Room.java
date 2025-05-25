package app.Models;

import app.SmartEnums.RoomEnum;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String name;
    private RoomEnum type;
    private List<SmartDevice<?>> deviceList;

    public Room(String name , RoomEnum type){
        this.name = name;
        this.type = type;
        this.deviceList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("%s [ Type: %s ]",
                name,
                type
        );
    }

    public List<SmartDevice<?>> getDeviceList(){
        return this.deviceList;
    }

    public String getName(){
        return this.name;
    }

    public RoomEnum getType(){
        return this.type;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setType(RoomEnum type){
        this.type = type;
    }
}
