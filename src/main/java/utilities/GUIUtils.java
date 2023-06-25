package utilities;

import controller.MainWindowController;
import exceptions.NoFlightSelectedException;
import exceptions.NoSuchFXMLFileExistingException;
import exceptions.NoUserLoggedInException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.Customer;

import java.io.IOException;
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

    public static void openPage(Object controllerClass, String pageName) throws NoUserLoggedInException, NoFlightSelectedException, NoSuchFXMLFileExistingException {
        try {
            VBox root = FXMLLoader.load(Objects.requireNonNull(controllerClass.getClass().getResource(pageName)));
            MainWindowController.loginStage = new Stage();
            MainWindowController.loginStage.setScene(new Scene(root));
            MainWindowController.loginStage.initModality(Modality.APPLICATION_MODAL);
            MainWindowController.loginStage.setResizable(false);
            MainWindowController.loginStage.show();
            MainWindowController.loginStage.setOnCloseRequest(e -> closePageByMouseClick());
        } catch (IOException e) {
            if (e.getCause().toString().contains("Main.loggedInCustomer"))
                throw new NoUserLoggedInException("No user is logged in");
            else if (e.getCause().toString().contains("Main.selectedFlight"))
                throw new NoFlightSelectedException("No flight is selected from the list");
        } catch (NullPointerException e) {
            if (Main.loggedInCustomer == null)
                throw new NoUserLoggedInException("No user is logged in");
            else
                throw new NoSuchFXMLFileExistingException("This .fxml file does not exist in the path");
        }
    }

    private static void closePageByMouseClick() {
        if (MainWindowController.loginStage != null) {
            MainWindowController.loginStage.close();
            MainWindowController.loginStage = null;
        }
    }

    public static boolean fieldValueNotNullOrEmpty(String fieldValue) {
        return fieldValue != null && fieldValue.trim() != "";
    }

    public static void closePageAfterOperation(Node pageNode) {
        ((Stage) pageNode.getScene().getWindow()).close();
        MainWindowController.loginStage.close();
        MainWindowController.loginStage = null;
    }

    public static boolean passwordConfirmed(TextField password, TextField passwordConfirmation) {
        return fieldValueNotNullOrEmpty(password.getText()) &&
                password.getText().equals(passwordConfirmation.getText());
    }
}
