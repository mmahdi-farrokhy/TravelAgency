package utilities;

import controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class GUIUtils {
    private GUIUtils() {
    }

    public static void setButtonStyle(Button button, int radius) {
        String styleCommand = "-fx-border-color: #e84575; -fx-border-radius: %d; -fx-background-color: #293556; -fx-background-radius: %d; -fx-text-fill : #e84575";
        String format = String.format(styleCommand, radius, radius);
        button.setStyle(format);
    }

    public static void resetButtonStyle(Button button, int radius) {
        String styleCommand = "-fx-background-color: #293556; -fx-background-radius: %d; -fx-text-fill : #e84575";
        String format = String.format(styleCommand, radius, radius);
        button.setStyle(format);
    }

    public static void showMessageBox(String title, String header, String context, Alert.AlertType icon) {
        Alert alert = new Alert(icon);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    public static void fillUserInformation(Customer customer) {
        try {
            Main.mainWindowController.setFieldText("usernameField", "Username: " + customer.getNationalCode());
            Main.mainWindowController.setFieldText("fullNameField", "Full Name: " + customer.getFullName().getRawFullName());
            Main.mainWindowController.setFieldText("phoneNumberField", "Phone Number: " + customer.getPhoneNumber());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openPage(Object controllerClass, String pageName) {
        try {
            if (MainWindowController.loginStage != null)
                closePage();

            Class<?> aClass = controllerClass.getClass();
            URL resource = aClass.getResource(pageName);
            URL url = Objects.requireNonNull(resource);
            VBox root = FXMLLoader.load(url);
            MainWindowController.loginStage = new Stage();
            MainWindowController.loginStage.setScene(new Scene(root));
            MainWindowController.loginStage.initModality(Modality.APPLICATION_MODAL);
            MainWindowController.loginStage.setResizable(false);
            MainWindowController.loginStage.show();
            MainWindowController.loginStage.setOnCloseRequest(e -> closePage());
        } catch (IOException | NullPointerException e) {
            showMessageBox("Error", "UI load failed!", "Could not load fxml file! \n Please make sure the file name is correct.", Alert.AlertType.ERROR);
        }
    }

    private static void closePage() {
        MainWindowController.loginStage.close();
        MainWindowController.loginStage = null;
    }

    public static void UserRegistryPage(Button registryButton) {
        ((Stage) registryButton.getScene().getWindow()).close();
        MainWindowController.loginStage.close();
        MainWindowController.loginStage = null;
    }

    public static boolean fieldValueNotNullOrEmpty(String fieldValue) {
        return fieldValue != null && fieldValue.trim() != "";
    }
}
