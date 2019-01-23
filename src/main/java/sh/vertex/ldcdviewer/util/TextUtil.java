package sh.vertex.ldcdviewer.util;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 * Created by Paul on 25.06.2017.
 */
public class TextUtil {

    /**
     * Array of the special characters that are allowed in any text drawing of Minecraft.
     */
    public static final char[] allowedCharactersArray = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};
    /**
     * Returns a string stored in the system clipboard.
     */
    public static String getClipboardString() {
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object) null);

            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String) transferable.getTransferData(DataFlavor.stringFlavor);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return "";
    }

    public static boolean isAllowedCharacter(char character) {
        return character != 167 && character >= 32 && character != 127;
    }

    /**
     * Filter string by only keeping those characters for which isAllowedCharacter() returns true.
     */
    public static String filterAllowedCharacters(String input) {
        StringBuilder sb = new StringBuilder();
        char[] stringCharArray = input.toCharArray();
        int stringLength = stringCharArray.length;

        for (int i = 0; i < stringLength; ++i) {
            char sChar = stringCharArray[i];

            if (isAllowedCharacter(sChar)) {
                sb.append(sChar);
            }
        }

        return sb.toString();
    }
}
