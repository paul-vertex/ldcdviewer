package sh.vertex.ldcdviewer;

import de.sbe.ldc.persistence.net.Reconnector;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sh.vertex.ldcdviewer.ui.LDCDUI;
import sh.vertex.ldcdviewer.ui.building.*;
import sh.vertex.ldcdviewer.ui.building.floors.FirstFloor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * logoDIDACT User Export JSON Viewer
 *
 * created by paul on 2019-01-18 at 11:41
 */
public class LDCDViewer extends Application {

    public static LDCDViewer instance;

    /**
     * Java Main Method (basically just runs the start(Stage) method)
     *
     * @param args Console specified arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public List<JSONObject> objects = new ArrayList<>();
    public List<String> users = new ArrayList<>();
    public BuildingRenderer buildingRenderer;
    public Floor currentFloor;
    public Room currentRoom;
    public boolean hostsPerRoom;
    public boolean shiftDown;
    public boolean currentlyRendering;
    public boolean live;

    /**
     * Launch Method / Display the Main Frame
     *
     * @param primaryStage JavaFX Window
     */
    public void start(Stage primaryStage) throws Exception {
        /* Set Instance */
        instance = this;

        this.buildingRenderer = new BuildingRenderer();
        this.currentFloor = new FirstFloor();

        LDCDUI userInterface = new LDCDUI();
        userInterface.show();
        userInterface.addEventFilter(MouseEvent.MOUSE_MOVED, (event) -> buildingRenderer.handleMouseMove(event));
        userInterface.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> buildingRenderer.handleMouseClick(event));
        userInterface.addEventFilter(ScrollEvent.SCROLL, (event) -> buildingRenderer.handleScroll(event));
        userInterface.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {if (event.getCode() == KeyCode.SHIFT) shiftDown = true;});
        userInterface.addEventFilter(KeyEvent.KEY_RELEASED, (event) -> {if (event.getCode() == KeyCode.SHIFT) shiftDown = false;});
        userInterface.setOnCloseRequest((event) -> buildingRenderer.threadStop = true);
        userInterface.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> buildingRenderer.keyTyped(event));

    }

    /**
     * Reads out the JSON Array and maps it onto the current floor.
     */
    public void mapCurrentFloor() {
        if (currentFloor == null) return;
        users.clear();
        for (JSONObject obj : this.objects) {
            if (obj.get("type") != null && obj.get("type").equals("host")) {
                String hostCode = (String) obj.get("id");
                if (hostCode != null && hostCode.startsWith("r") && hostCode.contains("-") && hostCode.split("-").length == 2) {
                    String room = hostCode.substring(1).split("-")[0];
                    String ending = hostCode.substring(1).split("-")[1];
                    int floor = Integer.parseInt(room.substring(0, 1));

                    if (currentFloor.getFloorNumber() == floor) {
                        Room r = currentFloor.findRoomByName(room);

                        if (r != null && obj.get("stateid") != null && obj.get("state") != null) {
                            ComputerHost host;
                            if (r.findHostByEnding(ending) != null) {
                                host = r.findHostByEnding(ending);
                            } else {
                                host = new ComputerHost();
                                host.setHostEnding(ending);
                                r.getHostList().add(host);
                            }

                            Object state = obj.get("state");

                            switch ((String) obj.get("stateid")) {
                                case "osName":
                                    host.setOsName((String) state);
                                    break;
                                case "osVersion":
                                    host.setOsVersion((String) state);
                                    break;
                                case "osArch":
                                    host.setOsArch((String) state);
                                    break;
                                case "ip":
                                    host.setIp((String) state);
                                    break;
                                case "power":
                                    host.setPowerState(state.equals("on"));
                                    break;
                                case "uptime":
                                    host.setUptime((Long) state);
                                    break;
                                case "users":
                                    if (state instanceof JSONArray) {
                                        JSONArray array = (JSONArray) state;
                                        List<String> rs = new ArrayList<>();
                                        for (Object object : array) {
                                            rs.add(removeEscapeCodes((String) object));
                                        }
                                        host.setUsers(rs);
                                    } else if (state instanceof String) {
                                        String s = (String) state;
                                        System.out.println(s);
                                    }
                                    break;
                                case "exam":
                                    r.setState(RoomState.EXAM);
                                    host.setInExam(true);
                                    break;
                                case "idleTime":
                                    try {
                                        JSONObject idleObj = (JSONObject) state;
                                        if (idleObj.keySet().size() == 1) {
                                            host.setIdleTime((Long) idleObj.get(idleObj.keySet().toArray()[0]));
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                        }
                    }
                }
            } else if (obj.get("type") != null && obj.get("type").equals("user")) {
                if (obj.get("id") != null) {
                    String id = (String) obj.get("id");
                    users.add(id);
                }
            }
        }
    }

    /**
     * Gets rid of all escape codes in a string and replaces them with
     * literal escape codes
     *
     * @param unformatted string with escape codes
     * @return string without escape codes
     */
    private String removeEscapeCodes(String unformatted) {
        return unformatted
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\'", "\\'")
                .replace("\"", "\\\"");
    }

    public void setLive() {
        for (int i = 0; i < 5; i++)
            System.out.println("Live!");

        live = true;
    }
}
