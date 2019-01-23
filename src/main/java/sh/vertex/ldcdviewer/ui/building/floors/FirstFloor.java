package sh.vertex.ldcdviewer.ui.building.floors;

import sh.vertex.ldcdviewer.ui.building.Floor;

/**
 * created by paul on 2019-01-18 at 23:23
 */
public class FirstFloor extends Floor {

    public FirstFloor() {
        super(0);

        // 800 Row
        registerRoom("0.801", 411, 95, 55, 86);
        registerRoom("0.802", 468, 95, 86, 55);
        registerRoom("0.803", 556, 95, 55, 86);
        registerRoom("0.804", 613, 95, 59, 55);
        registerRoom("0.805", 674, 95, 23, 55);
        registerRoom("0.806", 466, 0, 70, 70);
        registerRoom("0.807", 394, 0, 70, 70);
        registerRoom("0.811", 194, 0, 70, 70);
        registerRoom("0.812", 122, 0, 70, 70);
        registerRoom("0.813", 50, 0, 70, 70);
        registerRoom("0.814", 50, 95, 34, 34);
        registerRoom("0.815", 86, 95, 34, 34);
        registerRoom("0.816", 122, 95, 34, 34);
        registerRoom("0.817", 158, 95, 34, 34);
        registerRoom("0.818", 296, 95, 80, 70);

        registerRoom("0.821", 266, 0, 90, 40);

        // 700 Row
        registerRoom("0.704", 0, 95, 34, 34);
        registerRoom("0.703", 0, 131, 34, 34);
        registerRoom("0.702", 0, 167, 34, 34);
        registerRoom("0.701", 0, 203, 34, 34);

        // 600 Row
        registerRoom("0.601", 229, 167, 65, 70);
        registerRoom("0.602", 229, 95, 65, 70);
        registerRoom("0.603", 141, 131, 51, 51);
        registerRoom("0.604", 141, 184, 51, 51);

        // 300 Row
        registerRoom("0.301", 613, 287, 84, 40);
        registerRoom("0.302", 716, 287, 55, 40);
        registerRoom("0.307", 699, 95, 72, 125);

        // 200 Row
        registerRoom("0.201", 428, 257, 55, 70);
        registerRoom("0.202", 485, 257, 55, 70);
        registerRoom("0.206", 644, 152, 53, 86);
        registerRoom("0.207", 581, 183, 61, 55);
        registerRoom("0.209", 501, 152, 53, 86);
        registerRoom("0.210", 411, 183, 88, 55);
        registerRoom("0.211", 296, 167, 80, 70);
        registerRoom("0.215", 556, 287, 55, 40);

        // 100 Row
        registerRoom("0.101", 285, 257, 35, 34);
        registerRoom("0.104", 211, 257, 35, 34);
        registerRoom("0.105", 141, 257, 51, 34);
        registerRoom("0.106", 50, 257, 89, 34);
        registerRoom("0.108", 15, 324, 45, 34);
        registerRoom("0.109", 62, 308, 70, 50);
        registerRoom("0.110", 134, 308, 89, 50);
        registerRoom("0.113", 225, 324, 95, 34);

        // Deco
        registerDecorative("100", 15, 238, 682,17);
        registerDecorative("101", 15, 292.5, 305,13);
        registerDecorative("200", 376, 97, 34,140);
        registerDecorative("201", 541, 258, 16,80);
        registerDecorative("500", 15, 72, 682,23);
        registerDecorative("600", 193.5, 97, 36,140);
    }
}
