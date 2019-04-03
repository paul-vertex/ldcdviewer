package sh.vertex.ldcdviewer.ui.building;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by paul on 2019-03-30 at 19:34
 */
public class FloorParser {
    private File floorsFile;

    public FloorParser() {
        this.floorsFile = new File(".", "floors");
        if (!floorsFile.exists())
            floorsFile.mkdirs();
    }

    public List<Floor> parse() {
        List<Floor> floors = new ArrayList<>();
        JsonParser parser = new JsonParser();
        Arrays.stream(this.floorsFile.listFiles())
                .filter(file -> file.getName().endsWith(".json"))
                .forEach(file -> {
                    Floor f = new Floor();
                    try {
                        JsonArray array = (JsonArray) parser.parse(new FileReader(file));

                        for (JsonElement obj : array) {
                            JsonObject room = (JsonObject) obj;

                            if (room.has("type")) {
                                String type = room.get("type").getAsString();

                                switch (type) {
                                    case "room":
                                        f.registerRoom(room.get("name").getAsString(),
                                                room.get("xPos").getAsInt(),
                                                room.get("yPos").getAsInt(),
                                                room.get("width").getAsInt(),
                                                room.get("height").getAsInt());
                                        break;
                                    case "floor":
                                        f.setFloorNumber(room.get("id").getAsInt());
                                        f.setFloorName(room.get("name").getAsString());
                                        break;
                                    case "decorative":
                                        f.registerDecorative(room.get("name").getAsString(),
                                                room.get("xPos").getAsInt(),
                                                room.get("yPos").getAsInt(),
                                                room.get("width").getAsInt(),
                                                room.get("height").getAsInt());
                                        break;
                                }
                            }
                        }
                    } catch (FileNotFoundException e) {
                    }
                    floors.add(f);
                });
        return floors;
    }
}
