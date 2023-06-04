package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class GUIUtils {
    private GUIUtils() {
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

    public static void showMessageBox(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}
