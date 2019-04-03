package sh.vertex.ldcdviewer.ui.building;

import com.sun.javafx.tk.Toolkit;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sh.vertex.ldcdviewer.LDCDViewer;
import sh.vertex.ldcdviewer.util.DisplayUtil;

/**
 * created by paul on 2019-01-20 at 13:41
 */
public class RoomRenderer {

    private final Font menu = Font.font("Arial", FontWeight.NORMAL, 20);
    private final Font source16 = Font.loadFont(RoomRenderer.class.getResource("/assets/SourceSansPro-Regular.ttf").toExternalForm(), 16);
    private final Font source14 = Font.loadFont(RoomRenderer.class.getResource("/assets/SourceSansPro-Regular.ttf").toExternalForm(), 14);
    private final Font source12 = Font.loadFont(RoomRenderer.class.getResource("/assets/SourceSansPro-Regular.ttf").toExternalForm(), 12);

    private BuildingRenderer buildingRenderer;
    private int scrollAmount;
    private int selectedHost = -1;

    public RoomRenderer(BuildingRenderer buildingRenderer) {
        this.buildingRenderer = buildingRenderer;
    }

    /**
     * called by {@link BuildingRenderer}
     *
     * @param gc GraphicsContext passed over by the Canvas
     */
    void render(GraphicsContext gc) {
        LDCDViewer.instance.currentlyRendering = true;
        gc.translate(1020 - this.buildingRenderer.getRoomSlide(), 0);

        /*
            Set Font
         */
        gc.setFont(menu);

        /*
            Back Icon
         */
        gc.setStroke(new Color(0.324, 0.371, 0.534, 1f));
        gc.setFill(new Color(0.324, 0.371, 0.534, 1f));
        double tmpWidth = gc.getLineWidth();
        gc.setLineWidth(4.0d);
        gc.strokeLine(45, 20, 55, 10);
        gc.strokeLine(45, 20, 55, 30);
        gc.setLineWidth(tmpWidth);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("Go back", 60, 20);
        gc.setTextBaseline(VPos.BASELINE);



        /*
            Start Info Rendering
         */
        if (LDCDViewer.instance.currentRoom == null) {
            gc.translate(-(1020 - this.buildingRenderer.getRoomSlide()), 0);
            LDCDViewer.instance.currentlyRendering = false;
            return;
        }
        Room room = LDCDViewer.instance.currentRoom;

        gc.setFill(new Color(0.263, 0.311, 0.455, 1f));
        gc.fillText("Room " + room.getRoomName(), 45, 60);
        gc.fillText("Host Usage", 45, 90);
        gc.setFill(new Color(0.173, 0.212, 0.341, 1f));
        gc.fillRect(45, 102, 200, 18);
        double active = 0;
        for (int i = 0; i < room.getHostList().size(); i++) {
            ComputerHost ch = room.getHostList().get(i);
            if (ch.getUsers().size() > 0) {
                active++;
            }
        }
        double percentage = (active / ((double) room.getHostList().size()));
        double size = 200 * percentage;
        gc.setFill(new Color(0.999, 0.525, 0.497, 1f));
        gc.fillRect(45, 102, size, 18);
        gc.setFill(new Color(1f , 1f, 1f, 1f));
        gc.setFont(source16);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(Math.round(percentage * 10000) / 100 + "%", 45 + 100, 110);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.BASELINE);

        /*
            Active Clients
         */
        gc.setFill(new Color(0.169, 0.202, 0.318, 1f));
        gc.fillRect(770, 0, 250, 475);
        gc.setFill(new Color(0.131, 0.158, 0.256, 1f));
        gc.fillRect(768, 0, 2, 475);
        gc.setFont(source16);
        gc.setFill(new Color(0.606, 0.654, 0.788, 1f));
        gc.fillText("Hosts (" + room.getHostList().size() + ")", 790, 35 + scrollAmount);
        gc.setFont(source14);
        for (int i = 0; i < room.getHostList().size(); i++) {
            ComputerHost ch = room.getHostList().get(i);

            boolean u0 = ch.getUsers().size() > 0;

            gc.setFill(new Color(u0 ? 0.187 : 0.176, u0 ? 0.230 : 0.214, u0 ? 0.363 : 0.343, 1f));
            gc.fillRect(770, 56 + (i * 52) + scrollAmount, 250, 50);

            if (ch.getUsers().size() > 0) {
                gc.setFill(new Color(0.999, 0.525, 0.497, 1f));
                gc.fillRect(770, 56 + (i * 52) + scrollAmount, 2, 50);

                if (selectedHost == i) {
                    gc.fillRect(550, 56 + (i * 52) + scrollAmount, 220, 50);
                    gc.setFill(new Color(1f, 1f, 1f, 1f));
                    gc.fillText("Idle Time: " + ch.getIdleTime(), 555, 72 + (i * 52) + scrollAmount);
                    gc.fillText("Uptime: " + ch.getUptime() + " [" + ch.getIp() + "]", 555, 85 + (i * 52) + scrollAmount);
                    gc.fillText("OS: " + ch.getOsName(), 555, 98 + (i * 52) + scrollAmount);
                }
            }

            gc.setFill(new Color(u0 ? 0.166 : 0.165, u0 ? 0.194 : 0.199, u0 ? 0.311 : 0.315, 1f));
            gc.fillRoundRect(795, 62 + (i * 52) + scrollAmount, 17, 17, 9, 9);
            gc.fillRoundRect(795, 82 + (i * 52) + scrollAmount, 17, 17, 9, 9);

            if (u0) {
                gc.setFill(new Color(0.617, 0.666, 0.800, 1f));
                gc.fillText(ch.getUsers().get(0), 820, 77 + (i * 52) + scrollAmount);
                gc.setFill(new Color(0.366, 0.422, 0.598, 1f));
                gc.fillText("-" + ch.getHostEnding(), 820, 94 + (i * 52) + scrollAmount);

                if (ch.isInExam()) {
                    gc.setFill(new Color(109.0d / 255.0f, 76.0d / 255.0f, 112.0d / 255.0f, 1f));
                    gc.fillRoundRect(795, 62 + (i * 52) + scrollAmount, 17, 17, 9, 9);
                    gc.setFont(source12);
                    gc.setFill(new Color(0.965, 0.435, 0.593, 1f));
                    gc.fillText("E", 800, 74 + (i * 52) + scrollAmount);
                    gc.setFont(source14);
                }
            } else {
                gc.setFill(new Color(0.366, 0.422, 0.598, 1f));
                gc.fillText("-" + ch.getHostEnding(), 820, 85 + (i * 52) + scrollAmount);
            }
        }

        gc.translate(-(1020 - this.buildingRenderer.getRoomSlide()), 0);
        LDCDViewer.instance.currentlyRendering = false;
    }

    public void handleMouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        if (DisplayUtil.isHovering(45, 10, 60 + Toolkit.getToolkit().getFontLoader().computeStringWidth("Go Back", menu), 30, x, y)) {
            LDCDViewer.instance.currentRoom = null;
            scrollAmount = 0;
            selectedHost = 0;
        }

        if (LDCDViewer.instance.currentRoom == null)
            return;
        Room room = LDCDViewer.instance.currentRoom;

        boolean set = false;
        for (int i = 0; i < room.getHostList().size(); i++) {
            ComputerHost ch = room.getHostList().get(i);
            if (DisplayUtil.isHovering(770, 56 + (i * 52) + scrollAmount, 1020, 106 + (i * 52) + scrollAmount, x, y) && ch.getUsers().size() > 0) {
                if (selectedHost == i)
                    selectedHost = -1;
                else
                    selectedHost = i;
                set = true;
            }
        }

        if (!set) {
            selectedHost = -1;
        }
    }

    public void handleScroll(ScrollEvent event) {
        if (LDCDViewer.instance.currentRoom == null) {
            return;
        }
        Room room = LDCDViewer.instance.currentRoom;

        if (DisplayUtil.isHovering(770, 0, 1020, 475, this.buildingRenderer.getMouseX(), this.buildingRenderer.getMouseY())) {
            if (room.getHostList().size() * 52 - 475 > 0) {
                scrollAmount += event.getDeltaY();
                if (scrollAmount < -(56 + room.getHostList().size() * 52 - 475))
                    scrollAmount = -(56 + room.getHostList().size() * 52 - 475);
                if (scrollAmount > 0) scrollAmount = 0;
            }
        }
    }
}
