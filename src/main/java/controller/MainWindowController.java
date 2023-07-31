package controller;

import exceptions.NoFlightSelectedException;
import exceptions.NoSuchFXMLFileExistingException;
import exceptions.NoUserLoggedInException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.GUIUtils;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.ButtonActionInitializer.setOnActionMethods;
import static utilities.GUIUtils.showMessageBox;

public class MainWindowController implements Initializable {
    @FXML
    private Button loginSignupBtn;

    @FXML
    private Button editCustomerBtn;

    @FXML
    private Button flightsListBtn;

    @FXML
    private Button orderHistoryBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField phoneNumberField;

    public static Stage loginStage = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOnActionMethods(loginSignupBtn, 15, () -> openPageByButtonPush("../LoginPage.fxml"));
        setOnActionMethods(editCustomerBtn, 15, () -> openPageByButtonPush("../EditCustomerPage.fxml"));
        setOnActionMethods(flightsListBtn, 15, () -> openPageByButtonPush("../FlightsListPage.fxml"));
        setOnActionMethods(orderHistoryBtn, 15, () -> openPageByButtonPush("../OrdersHistoryPage.fxml"));
    }

    private void openPageByButtonPush(String pageName) {
        try {
            GUIUtils.openPage(this, pageName);
        } catch (NoUserLoggedInException ex) {
            showMessageBox("Attention", "User not registered", "Please login or sign up first", Alert.AlertType.ERROR);
        } catch (NoFlightSelectedException ex) {
            showMessageBox("Attention", "No flight is selected", "Please select a flight from the list", Alert.AlertType.ERROR);
        } catch (NoSuchFXMLFileExistingException ex) {
            showMessageBox("Error", "UI load failed!", "Could not load fxml file! \nPlease make sure the file name is correct.", Alert.AlertType.ERROR);
        }
    }

    public void setFieldText(String textFieldName, String newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = getClass().getDeclaredField(textFieldName);
        TextField textField = (TextField) field.get(this);
        field.set(this, textField);
        field.setAccessible(true);
        textField.setText(newValue);
    }
}
