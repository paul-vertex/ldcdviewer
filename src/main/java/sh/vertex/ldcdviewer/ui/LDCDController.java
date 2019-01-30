package sh.vertex.ldcdviewer.ui;

import de.sbe.ldc.persistence.net.Reconnector;
import de.sbe.ldc.persistence.net.TickerConnection;
import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.Request;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sh.vertex.ldcdviewer.LDCDViewer;

import java.io.*;

/**
 * Controller to load in a json file
 * <p>
 * created by paul on 2019-01-18 at 11:51
 */
public class LDCDController {

    public TextField fileLocation;
    public CheckBox remember;
    public ProgressBar loadProgress;

    @FXML
    public void loadData(ActionEvent actionEvent) {
        new Thread(() -> {
            File file = new File(this.fileLocation.getText());
            if (file.exists()) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    JSONParser parser = new JSONParser();

                    double current = 0;
                    int lines = countLines(file);

                    while ((line = reader.readLine()) != null) {
                        double progress = current / (double) lines;
                        JSONObject obj = (JSONObject) parser.parse(line);
                        LDCDViewer.instance.objects.add(obj);
                        if (current % 2 == 0)
                            Platform.runLater(() -> loadProgress.setProgress(progress));
                        current++;
                    }

                    Platform.runLater(() -> {
                        try {
                            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            displayBuildingOverwie(window);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, "Data Load").start();
    }

    @FXML
    public void openFileOpenDialog(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Data");
        File rs = fileChooser.showOpenDialog(LDCDUI.stageInstance);
        if (rs != null)
            this.fileLocation.setText(rs.getAbsolutePath());
    }

    private int countLines(File file) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        try {
            byte[] c = new byte[1024];

            int readChars = is.read(c);
            if (readChars == -1) {
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i = 0; i < 1024; ) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            return count == 0 ? 1 : count;
        } finally {
            is.close();
        }
    }

    public void useLive(ActionEvent actionEvent) {
        LDCDViewer.instance.setLive();
        new Thread(() -> {
            try {
                new Reconnector().reconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        Platform.runLater(() -> {
            try {
                Stage window = (Stage) (LDCDUI.stageInstance.getScene().getWindow());
                displayBuildingOverwie(window);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void displayBuildingOverwie(Stage window) throws IOException {
        Parent buildingScreen = FXMLLoader.load(this.getClass().getResource("/assets/buildingoverview.fxml"));
        Scene buildingScene = new Scene(buildingScreen);
        window.setScene(buildingScene);
        window.show();

        LDCDViewer.instance.mapCurrentFloor();
    }
}
