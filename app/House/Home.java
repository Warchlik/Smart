package app.House;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Home {

    private String name;
    private final double  latitude = ThreadLocalRandom.current().nextDouble(-90.00, 90.00);;
    private final double longitude = ThreadLocalRandom.current().nextDouble(-180.00, 180.00);;
    private float size;
    private int floors;
    private int rooms;
    private List<Room> roomsList;

    public Home(String name , float size , int floors, int rooms) {
        this.name = name;
        this.size = size;
        this.floors = floors;
        this.rooms = rooms;
        this.roomsList = new ArrayList<>();
    }

    public String toString(){
        return String.format("%s [ Size: %.2f m² | Floors: %d | Rooms: %d | RoomList: %d | Latitude: %.5f | Longitude: %.5f ]",
                name,
                size,
                floors,
                rooms,
                roomsList.size(),
                latitude,
                longitude
        );
    }

    public List<Room> getRooms() {
        return this.roomsList;
    }

    public int getFloors(){
        return this.floors;
    }

    public String getName(){
        return this.name;
    }

    public float getSize(){
        return this.size;
    }

    public int getRoomsValue(){
        return this.rooms;
    }

    public void addRoom(Room room){
        this.roomsList.add(room);
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSize(float size){
        this.size = size;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public void setRoomCount(int rooms) {
        if (rooms < this.roomsList.size()) {
            throw new IllegalArgumentException("Cannot set rooms to " + rooms + ", already have " + roomsList.size());
        }
        this.rooms = rooms;
    }
}
