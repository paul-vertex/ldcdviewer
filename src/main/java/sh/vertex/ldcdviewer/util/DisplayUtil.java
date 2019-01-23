package sh.vertex.ldcdviewer.util;

/**
 * created by paul on 2019-01-18 at 21:24
 */
public class DisplayUtil {

    /**
     * Checks if the current mouse cursor is in range
     */
    public static boolean isHovering(double x, double y, double x2, double y2, double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
