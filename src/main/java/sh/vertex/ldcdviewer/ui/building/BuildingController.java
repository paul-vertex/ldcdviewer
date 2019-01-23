package sh.vertex.ldcdviewer.ui.building;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import sh.vertex.ldcdviewer.LDCDViewer;

/**
 * Controller for Building GUI Screen
 *
 * created by paul on 2019-01-18 at 17:39
 */
public class BuildingController {

    @FXML
    public Canvas buildingScreen;

    /**
     * Called when GUI gets initialized, starts a new animation Thread
     * which renders the building on the screen.
     */
    public void initialize() {
        new AnimationTimer() {

            @Override
            public void handle(long now) {
                LDCDViewer.instance.buildingRenderer.render(buildingScreen.getGraphicsContext2D());
            }
        }.start();
    }
}
