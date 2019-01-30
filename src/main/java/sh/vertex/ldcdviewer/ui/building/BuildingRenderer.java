package sh.vertex.ldcdviewer.ui.building;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.sbe.ldc.persistence.net.DataConnection;
import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.JsonProcessorAdapter;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.protocol.Response;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sh.vertex.ldcdviewer.LDCDViewer;
import sh.vertex.ldcdviewer.util.DisplayUtil;
import sh.vertex.ldcdviewer.util.TextUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles Mouse Events and Rendering
 *
 * created by paul on 2019-01-18 at 18:04
 */
public class BuildingRenderer {

    private final Font copyright = Font.font("Arial", FontWeight.NORMAL, 16);
    private final Font menu = Font.font("Arial", FontWeight.NORMAL, 20);
    public boolean threadStop;
    private double mouseX;
    private double mouseY;

    private boolean menuExpanded;
    private int menuSlide;

    private int roomSlide;
    private RoomRenderer roomRenderer;

    private String text = "";
    private boolean isSearching;

    /**
     * Constructor which starts the AnimationThread for menu expanding
     */
    public BuildingRenderer() {
        this.roomRenderer = new RoomRenderer(this);

        new Thread(() -> {
            while (!threadStop) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                if (menuExpanded && menuSlide < 180) menuSlide += 4;
                if (!menuExpanded && menuSlide > 0) menuSlide -= 4;
                if (LDCDViewer.instance.currentlyRendering)
                    continue;
                if (LDCDViewer.instance.currentRoom != null && roomSlide < 1020) roomSlide += 10;
                if (LDCDViewer.instance.currentRoom == null && roomSlide > 0) roomSlide -= 10;
            }
        }).start();
    }

    /**
     * Main Render Method, called around 60 times a second
     *
     * @param gc GraphicsContext passed over by the Canvas
     */
    void render(GraphicsContext gc) {
        gc.setFill(new Color(0.147, 0.173, 0.272, 1f));
        gc.fillRect(0, 0, 1020, 520);

        int xShift = 130 - roomSlide;
        int yShift = 60;

        /*
            Render Current Floor Number
         */
        gc.setFont(menu);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(new Color(0.263, 0.311, 0.455, 1f));
        gc.fillText("Current Floor: " + LDCDViewer.instance.currentFloor.getFloorNumber(), 510 - roomSlide, 40);
        gc.setTextAlign(TextAlignment.LEFT);

        /*
            Render Room Control
         */
        this.roomRenderer.render(gc);

        /*
            Render Rooms
         */
        gc.translate(xShift, yShift);
        LDCDViewer.instance.currentFloor.renderFloor(gc, mouseX, mouseY);
        gc.translate(-xShift, -yShift);

        /*
            Bottom Bar (Program Name + Options)
         */
        gc.setFill(new Color(0.108, 0.130, 0.212, 1f));
        gc.fillRect(0, 475, 1020, 80);
        gc.setFill(new Color(0.232, 0.275, 0.407, 1f));
        gc.setStroke(new Color(0.232, 0.275, 0.407, 1f));
        gc.setFont(copyright);
        gc.fillText("logoDIDACT export viewer", 20, 512);
        gc.fillText(LDCDViewer.instance.shiftDown ? "show active/room?" : "show hosts/room?", 840, 512);
        if (LDCDViewer.instance.hostsPerRoom)
            gc.fillRect(980, 498, 16, 16);
        else
            gc.strokeRect(980, 498, 16, 16);

        /*
            Search Bar
         */
        gc.setStroke(new Color(0.169, 0.209, 0.320, 1f));
        double tmpWidth = gc.getLineWidth();
        gc.setLineWidth(3.0d);
        gc.strokeRect(1020 / 2 - 200, 493, 400, 30);
        gc.setLineWidth(tmpWidth);
        if (text.isEmpty() && !isSearching) {
            gc.setFill(new Color(0.232, 0.275, 0.407, 1f));
            gc.setFont(copyright);
            gc.fillText("Search...", 1020 / 2 - 190, 514);
        } else {
            gc.setFill(new Color(0.606, 0.654, 0.788, 1f));
            gc.setFont(copyright);
            gc.fillText(text + (isSearching ? "_" : ""), 1020 / 2 - 190, 514);
            gc.setTextAlign(TextAlignment.RIGHT);
            gc.fillText(String.valueOf(this.getSearchResult().size()), 1020 / 2 + 190, 514);
            gc.setTextAlign(TextAlignment.LEFT);
        }

        /*
            Actual Search
         */
        /*List<String> rs = this.getSearchResult();
        int result = rs.size() * 52;

        if (rs.size() < 150) {
            gc.setFill(new Color(0.169, 0.202, 0.318, 1f));
            gc.fillRect(1020 / 2 - 200, 475 - result, 400, result);
            for (int i = 0; i < rs.size(); i++) {
                String name = rs.get(i);
                boolean u0 = this.isUserInFloor(name);
                gc.setFill(new Color(u0 ? 0.187 : 0.176, u0 ? 0.230 : 0.214, u0 ? 0.363 : 0.343, 1f));
                gc.fillRect(1020 / 2 - 200, 475 - result + (i * 52), 400, 50);

                gc.setFill(new Color(0.366, 0.422, 0.598, 1f));
                gc.fillText(name, 1020 / 2 - 190, 505 - result + (i * 52));
            }
        }
        */


        /*
            Hamburger Menu
         */
        if (menuSlide > 0) {
            gc.setFill(new Color(0.169, 0.209, 0.320, 1f));
            gc.fillRect(0, 0, menuSlide, 475);
            gc.setFill(new Color(0.131, 0.158, 0.256, 1f));
            gc.fillRect(menuSlide, 0, 2, 475);


            gc.setFill(new Color(0.108, 0.130, 0.212, 1f));
            gc.fillRect(10, 81, 25, 4);
            gc.fillRect(10, 139, 25, 4);
            gc.fillRect(10, 197, 25, 4);
            gc.setFill(new Color(0.341, 0.397, 0.558, 1f));
            gc.fillRect(10, 89, 25, 4);
            gc.fillRect(10, 97, 25, 4);
            gc.fillRect(10, 147, 25, 4);
            gc.fillRect(10, 131, 25, 4);
            gc.fillRect(10, 181, 25, 4);
            gc.fillRect(10, 189, 25, 4);

            gc.setFont(menu);
            gc.setFill(new Color(0.341, 0.397, 0.558, 1f));
            gc.fillText("Third Floor", 40, 98);
            gc.fillText("Second Floor", 40, 148);
            gc.fillText("First Floor", 40, 198);

            if (LDCDViewer.instance.live) {
                /* Test Function */
                gc.setFill(new Color(0.999, 0.525, 0.497, 1f));
                gc.fillRect(10, 240 , 160, 40);
                gc.setFill(new Color(1f, 1f, 1f, 1f));
                gc.setTextAlign(TextAlignment.CENTER);
                gc.fillText("Test Button", 90, 265);
                gc.setTextAlign(TextAlignment.LEFT);
            }
        }

        /*
            Hamburger Icon
         */
        gc.setFill(new Color(0.108, 0.130, 0.212, 1f));
        gc.fillRect(10, 10, 25, 4);
        gc.fillRect(10, 18, 25, 4);
        gc.fillRect(10, 26, 25, 4);
    }

    /**
     * Check if user is present in current floor
     *
     * @param name user to search
     * @return present or not
     */
    private boolean isUserInFloor(String name) {
        for (Room room : LDCDViewer.instance.currentFloor.getFloorRooms())
            if (room.isNormal())
                for (ComputerHost s : room.getHostList())
                    for (String x : s.getUsers())
                        if (x.equals(name))
                            return true;
        return false;
    }

    /**
     * Handles mouse moves and shifts it onto the building structure
     *
     * @param event mouse event
     */
    public void handleMouseMove(MouseEvent event) {
        mouseX = event.getX() - 130;
        mouseY = event.getY() - 60;
    }

    /**
     * Handles mouse clicks, so all kinds of different buttons etc
     *
     * @param event mouse event
     */
    public void handleMouseClick(MouseEvent event) {
        double x = event.getX() - 130;
        double y = event.getY() - 60;

        if (DisplayUtil.isHovering(10, 10, 35, 35, event.getX(), event.getY())) {
            menuExpanded = !menuExpanded;
        }

        if (menuExpanded) {
            /*
                Floor Buttons
             */
            if (DisplayUtil.isHovering(0, 81, 180, 97, event.getX(), event.getY())) {
                LDCDViewer.instance.SwitchFloor(2);
            }
            if (DisplayUtil.isHovering(0, 131, 180, 147, event.getX(), event.getY())) {
                LDCDViewer.instance.SwitchFloor(1);
            }
            if (DisplayUtil.isHovering(0, 181, 180, 197, event.getX(), event.getY())) {
                LDCDViewer.instance.SwitchFloor(0);
            }
            if (DisplayUtil.isHovering(180, 0, 1020, 475, event.getX(), event.getY()))
                menuExpanded = false;

            /*
                Test Button
             */
            if (DisplayUtil.isHovering(10, 240, 170, 280, event.getX(), event.getY())) {
                if (LDCDViewer.instance.live) {
                    try {
                        Response rsp = DataConnection.instance.send(new Request(Command.OBJECT_DEVICE_GET_INVENTORY_INFO), new JsonProcessorAdapter() {

                            @Override
                            public void processJson(JsonObject _object) {
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (DisplayUtil.isHovering(980, 498, 996, 514, event.getX(), event.getY())) {
                LDCDViewer.instance.hostsPerRoom = !LDCDViewer.instance.hostsPerRoom;
            }

            if (LDCDViewer.instance.currentRoom == null)
                LDCDViewer.instance.currentFloor.getFloorRooms()
                        .stream()
                        .filter(room -> room.isHovering(x, y))
                        .filter(Room::isNormal)
                        .findFirst().ifPresent(room -> LDCDViewer.instance.currentRoom = room);

            if (DisplayUtil.isHovering(1020 / 2 - 200, 493, 1020 / 2 - 200 + 400, 523, event.getX(), event.getY()))
                isSearching = !isSearching;
            else
                isSearching = false;

            if (LDCDViewer.instance.currentRoom != null)
                this.roomRenderer.handleMouseClick(event);
        }
    }

    public void keyTyped(KeyEvent event) {
        if (!isSearching)
            return;

        KeyCode keyCode = event.getCode();
        char typedChar = event.getCharacter().charAt(0);
        if (typedChar == '\uFFFF')
            return;

        if (keyCode == KeyCode.V && event.isControlDown() && !System.getProperty("os.name").toLowerCase().contains("mac"))
            this.text += TextUtil.filterAllowedCharacters(TextUtil.getClipboardString());
        else if (keyCode == KeyCode.V && event.isMetaDown() && System.getProperty("os.name").toLowerCase().contains("mac"))
            this.text += TextUtil.filterAllowedCharacters(TextUtil.getClipboardString());
        else if (keyCode == KeyCode.BACK_SPACE && this.text.length() >= 1)
            this.text = this.text.substring(0, this.text.length() - 1);
        else {
            String typed = TextUtil.filterAllowedCharacters(event.getText());
            this.text += typed;
        }

        long startTime = System.currentTimeMillis();
        Floor cf = LDCDViewer.instance.currentFloor;
        for (Room r : cf.getFloorRooms()) {
            boolean found = false;
            forward:
            for (ComputerHost c : r.getHostList()) {
                for (String un : c.getUsers()) {
                    for (String s : this.getSearchResult()) {
                        if (un.substring(3).equals(s)) {
                            r.saveAndSetSearchRoomState();
                            found = true;
                            System.out.println(s);
                            break forward;
                        }
                    }
                }
            }
            if (!found) {
                r.revertRoomState();
            }
        }
        System.out.println("Took " + (System.currentTimeMillis() - startTime) + "ms to calculate");
    }

    /**
     * Get current slide offset
     *
     * @return thread offset
     */
    public int getRoomSlide() {
        return roomSlide;
    }

    public double getMouseX() {
        return mouseX + 130;
    }

    public double getMouseY() {
        return mouseY + 60;
    }

    public void handleScroll(ScrollEvent event) {
        if (LDCDViewer.instance.currentRoom != null)
            this.roomRenderer.handleScroll(event);
    }

    public List<String> getSearchResult() {
        List<String> result = new ArrayList<>();

        if (text.isEmpty())
            return result;

        for (String x : LDCDViewer.instance.users) {
            if (x.toLowerCase().contains(text.toLowerCase())) {
                result.add(x);
            }
        }

        return result;
    }
}
