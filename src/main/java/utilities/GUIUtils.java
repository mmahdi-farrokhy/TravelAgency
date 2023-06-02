package utilities;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class GUIUtils {
    private GUIUtils() {
    }

    private static void mouseEnteredTheButton(Button button) {
        button.setStyle("-fx-border-color: #e84575; -fx-border-radius: 15; -fx-background-color:  #293556; -fx-background-radius:  15;");
    }

    public static void setButtonStyle(Button button, Color borderColor, Color backgroundColor, int radius) {
        String styleCommand = "-fx-border-color: %s; -fx-border-radius: %d; -fx-background-color: %s; -fx-background-radius: %d;";
        String format = String.format(styleCommand, borderColor.toString().replace("0x", "#").substring(0, 7), radius, backgroundColor.toString().replace("0x", "#").substring(0, 7), radius);
        button.setStyle(format);
    }

    public static void resetButtonStyle(Button button, Color backgroundColor, int radius) {
        String styleCommand = "-fx-background-color: %s; -fx-background-radius: %d;";
        String format = String.format(styleCommand, backgroundColor.toString().replace("0x", "#").substring(0, 7), radius);
        button.setStyle(format);
    }
}
