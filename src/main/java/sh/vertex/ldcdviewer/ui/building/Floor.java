package sh.vertex.ldcdviewer.ui.building;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Floor Class, handles Rooms
 * <p>
 * created by paul on 2019-01-18 at 23:22
 */
public class Floor {

    private int floorNumber;
    private List<Room> floorRooms;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.floorRooms = new ArrayList<>();
        LoadFloorObjects();
    }

    private void LoadFloorObjects() {
        Type type = new TypeToken<List<FloorJsonObject>>() {
        }.getType();

        //Builtin Resource
        InputStream inputStream = getClass().getResourceAsStream(String.format("/assets/floors/floor%d.json", floorNumber));
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        List<FloorJsonObject> floorJsonObjects = (new Gson()).fromJson(inputReader, type);


        //Local
        //List<FloorJsonObject> floorJsonObjects = (new Gson()).fromJson(new InputStreamReader(new FileInputStream(String.format("assets\\floor%d.json", floorNumber))), type);

        for (FloorJsonObject floorObject : floorJsonObjects) {
            if (floorObject.getType() == 0)
                registerRoom(floorObject.getName(), floorObject.getX(), floorObject.getY(), floorObject.getW(), floorObject.getH());
            else if (floorObject.getType() == 1)
                registerDecorative(floorObject.getName(), floorObject.getX(), floorObject.getY(), floorObject.getW(), floorObject.getH());
            else System.out.printf("Undefined FloorObjectType: %d%n", floorObject.getType());
        }
    }

    private void registerRoom(String text, int x, int y, int w, int h) {
        this.floorRooms.add(new Room(text, x, y, w, h, RoomState.NORMAL));
    }

    private void registerDecorative(String description, double x, double y, int w, int h) {
        this.floorRooms.add(new Room(description, x, y, w, h, RoomState.DECO));
    }

    void renderFloor(GraphicsContext gc, double mouseX, double mouseY) {
        this.floorRooms.forEach(room -> room.render(gc, mouseX, mouseY));
    }

    public List<Room> getFloorRooms() {
        return floorRooms;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public Room findRoomByName(String name) {
        for (Room room : this.getFloorRooms())
            if (room.getRoomName().replaceAll("[.]", "").equals(name))
                return room;

        return null;
    }
}
