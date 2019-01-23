package sh.vertex.ldcdviewer.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main GUI Stage, called on start, jumps into load json menu.
 *
 * created by paul on 2019-01-18 at 11:48
 */
public class LDCDUI extends Stage {

    public static LDCDUI stageInstance;

    public LDCDUI() throws IOException {
        /* Set instance */
        stageInstance = this;

        /* Dimensions */
        this.setWidth(1020.0d);
        this.setHeight(560.0d);

        /* FXML */
        Parent root = FXMLLoader.load(this.getClass().getResource("/assets/loadjson.fxml"));

        /* Window Options */
        this.setTitle("LDCD User/Room Exports");
        this.setResizable(false);
        this.setScene(new Scene(root));
    }
}
