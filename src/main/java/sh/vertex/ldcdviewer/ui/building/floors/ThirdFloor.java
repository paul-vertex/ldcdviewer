package sh.vertex.ldcdviewer.ui.building.floors;

import sh.vertex.ldcdviewer.ui.building.Floor;

/**
 * created by paul on 2019-01-19 at 00:16
 */
public class ThirdFloor extends Floor {

    public ThirdFloor() {
        super(2);

        // 500 Row
        registerRoom("2.501", 275, 0, 83, 34);
        registerRoom("2.506", 167, 15, 83, 55);
        registerRoom("2.507", 82, 15, 83, 55);
        registerRoom("2.508", 25, 15, 55, 55);

        // 400 Row
        registerRoom("2.401", 383, 15, 83, 55);
        registerRoom("2.402", 468, 15, 83, 55);
        registerRoom("2.408", 710, 0, 59, 50);
        registerRoom("2.409", 669, 0, 39, 34);
        registerRoom("2.410", 632, 0, 35, 34);
        registerRoom("2.411", 595, 0, 35, 34);

        // 300 Row
        registerRoom("2.301", 664, 295, 105, 34);
        registerRoom("2.303", 710, 52, 59, 98);
        registerRoom("2.302", 710, 152, 59, 83);

        // 200 Row
        registerRoom("2.201", 383, 257, 83, 55);
        registerRoom("2.202", 468, 257, 83, 55);
        registerRoom("2.209", 576, 295, 45, 34);
        registerRoom("2.208", 623, 295, 39, 34);

        // 100 Row
        registerRoom("2.101", 285, 257, 35, 34);
        registerRoom("2.104", 212, 257, 35, 34);
        registerRoom("2.105", 175, 257, 35, 34);
        registerRoom("2.106", 99, 257, 74, 34);
        registerRoom("2.107", 62, 257, 35, 34);
        registerRoom("2.108", 15, 257, 45, 34);
        registerRoom("2.109", 15, 324, 45, 34);
        registerRoom("2.110", 62, 308, 70, 50);
        registerRoom("2.111", 134, 308, 89, 50);
        registerRoom("2.114", 225, 324, 95, 34);

        // Deco
        registerDecorative("square", 321, 257.5, 62, 70);
        registerDecorative("C1", 368, 70, 16, 187);
        registerDecorative("C2", 694, 70, 16, 187);
        registerDecorative("100", 17, 292, 302, 16);
        registerDecorative("200", 385, 314, 191, 16);
        registerDecorative("201", 553, 282, 24, 32);
        registerDecorative("202", 577.5, 282, 132, 12);
        registerDecorative("300", 710, 238, 59, 56);
        registerDecorative("400", 360, 0, 236, 13);
        registerDecorative("401", 552, 16, 42, 20);
        registerDecorative("402", 552, 36, 160, 13);
        registerDecorative("500", 25, 0, 250, 13);
        registerDecorative("501", 252, 36, 132, 13);
        registerDecorative("502", 252, 16, 23, 17);
        registerDecorative("503", 360, 16, 23, 17);
    }
}
