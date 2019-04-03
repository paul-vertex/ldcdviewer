package sh.vertex.ldcdviewer.ui.building;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Floor Class, handles Rooms
 *
 * created by paul on 2019-01-18 at 23:22
 */
public class Floor {

    private int floorNumber;
    private List<Room> floorRooms;
    private String floorName;

    public Floor() {
        this.floorName = "";
        this.floorRooms = new ArrayList<>();
    }

    public Floor(int floorNumber) {
        this.floorName = "";
        this.floorNumber = floorNumber;
        this.floorRooms = new ArrayList<>();
    }

    protected void registerRoom(String text, int x, int y, int w, int h) {
        this.floorRooms.add(new Room(text, x, y, w, h, RoomState.NORMAL));
    }

    protected void registerDecorative(String description, double x, double y, int w, int h) {
        this.floorRooms.add(new Room(description, x, y, w, h, RoomState.DECO));
    }

    void renderFloor(GraphicsContext gc, double mouseX, double mouseY) {
        this.floorRooms.forEach(room -> room.render(gc, mouseX, mouseY));
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloorName() {
        return floorName;
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
