package sh.vertex.ldcdviewer.ui.building;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sh.vertex.ldcdviewer.LDCDViewer;
import sh.vertex.ldcdviewer.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles stuff like rendering a room, holding hosts
 * and different room states
 */
public class Room {

    private static final Font arial = Font.font("Arial", FontWeight.NORMAL, 16);

    private final String roomName;
    private double x, y, w, h;
    private RoomState state;
    private List<ComputerHost> hostList;
    private boolean isSearchState;
    private RoomState prevState;

    public Room(String roomName, double x, double y, double w, double h, RoomState state) {
        this.hostList = new ArrayList<>();
        this.roomName = roomName;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.state = state;
    }

    public String getRoomName() {
        return roomName;
    }

    public RoomState getState() {
        return state;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public void setState(RoomState state) {
        this.state = state;
    }

    public void render(GraphicsContext gc, double mouseX, double mouseY) {
        switch (getState()) {
            case NORMAL:
                gc.setFill(new Color(0.200, 0.244, 0.380, 1f));
                gc.fillRect(x, y, w, h);
                renderRoomName(gc, mouseX, mouseY);
                break;
            case EXAM:
                gc.setStroke(new Color(0.965, 0.435, 0.593, 0.5f));
                gc.strokeRect(x + 1, y + 1, w - 2, h - 2);

                gc.setFill(new Color(109.0d / 255.0f, 76.0d / 255.0f, 112.0d / 255.0f, 255.0d / 255.0f));
                gc.fillRect(x, y, w, h);

                gc.setFill(new Color(0.965, 0.435, 0.593, 1f));
                gc.setFont(arial);
                gc.fillText("E", x + 5, y + h - 7);
                renderRoomName(gc, mouseX, mouseY);
                break;
            case SEACH_RESULT:
                gc.setStroke(new Color(0.360, 0.733, 1.000, 0.5f));
                gc.strokeRect(x + 1, y + 1, w - 2, h - 2);

                gc.setFill(new Color(0.338, 0.534, 0.996, 1f));
                gc.fillRect(x, y, w, h);

                gc.setFill(new Color(0.360, 0.733, 1.000, 1f));
                gc.setFont(arial);
                gc.fillText("S", x + 5, y + h - 7);
                renderRoomName(gc, mouseX, mouseY);
                break;
            case DECO:
                gc.setFill(new Color(0.215, 0.253, 0.373, 1f));
                for (int i = 0; i < this.h; i += 4) {
                    for (int j = 0; j < this.w; j += 4) {
                        gc.fillRect(x + j, y + i, 2, 2);
                    }
                }
                break;
        }
    }

    private void renderRoomName(GraphicsContext gc, double mouseX, double mouseY) {
        gc.setFill(new Color(0.108, 0.130, 0.212, 1f));
        gc.setFont(arial);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        if (isHovering(mouseX, mouseY)) {
            gc.fillText(roomName, x + w / 2, y + h / 2 + (LDCDViewer.instance.hostsPerRoom ? -8 : 0));
        }
        if (LDCDViewer.instance.hostsPerRoom) {
            if (LDCDViewer.instance.shiftDown) {
                int totalUsers = 0;
                for (ComputerHost ch : this.getHostList())
                    totalUsers += ch.getUsers().size();

                if (totalUsers > 0) {
                    if (this.getState() == RoomState.EXAM) {
                        gc.setFill(new Color(0.965, 0.435, 0.593, 1f));
                    } else {
                        gc.setFill(new Color(0.999, 0.525, 0.497, 1f));
                    }
                }

                gc.fillText(String.valueOf(totalUsers), x + w / 2, y + h / 2 + (isHovering(mouseX, mouseY) ? 8 : 0));
            } else {
                gc.fillText(String.valueOf(this.getHostList().size()), x + w / 2, y + h / 2 + (isHovering(mouseX, mouseY) ? 8 : 0));
            }
        }
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.BASELINE);
    }

    public boolean isHovering(double mouseX, double mouseY) {
        return DisplayUtil.isHovering(x, y, x + w, y + h, mouseX, mouseY);
    }

    public List<ComputerHost> getHostList() {
        return hostList;
    }

    public ComputerHost findHostByEnding(String hostEnding) {
        for (ComputerHost host : this.getHostList())
            if (host.getHostEnding() != null && host.getHostEnding().equals(hostEnding))
                return host;

        return null;
    }

    public boolean isNormal() {
        return this.getState() == RoomState.NORMAL || this.getState() == RoomState.EXAM || this.getState() == RoomState.SEACH_RESULT;
    }

    public void saveAndSetSearchRoomState() {
        if (!this.isSearchState) {
            this.isSearchState = true;
            this.prevState = this.state;
            this.state = RoomState.SEACH_RESULT;
        }
    }

    public void revertRoomState() {
        if (this.isSearchState) {
            this.state = this.prevState;
            this.isSearchState = false;
        }
    }
}