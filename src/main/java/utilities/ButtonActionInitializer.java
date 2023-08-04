package utilities;

import javafx.scene.control.Button;

import static utilities.GUIUtils.resetButtonStyle;
import static utilities.GUIUtils.setButtonStyle;

public class ButtonActionInitializer {
    @FunctionalInterface
    public interface ActionListener {
        void listener();
    }

    public static void setOnActionMethods(Button button, int buttonRadius, ActionListener actionListener) {
        button.setOnMouseEntered(e -> setButtonStyle((Button) e.getSource(), buttonRadius));
        button.setOnMouseExited(e -> resetButtonStyle((Button) e.getSource(), buttonRadius));
        button.setOnAction(e -> actionListener.listener());
    }
}
