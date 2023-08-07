package controller;

import data.dao.factory.CustomerDAOFactory;
import exceptions.NoFlightSelectedException;
import exceptions.NoSuchFXMLFileExistingException;
import exceptions.NoUserLoggedInException;
import exceptions.UnexpectedException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static main.Main.loggedInCustomer;
import static utilities.ButtonActionInitializer.setOnActionMethods;
import static utilities.GUIUtils.*;

public class LoginPageController implements Initializable {
    @FXML
    private TextField accountCheckText;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField nationalCodeField;

    @FXML
    private Text errorText;

    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorText.setVisible(false);

        setOnActionMethods(loginBtn, 8, this::loginUser);
        accountCheckText.setOnMouseClicked(e -> openSignUpPage());
    }

    private void openSignUpPage() {
        try {
            closeCurrentPage(loginBtn);
            openPage(this, "..//SignUpPage.fxml");
        } catch (NoUserLoggedInException ex) {
            showMessageBox("Attention", ex.getMessage(), "Please login or sign up first", Alert.AlertType.ERROR);
        } catch (NoFlightSelectedException ex) {
            showMessageBox("Attention", ex.getMessage(), "Please select a flight from the list", Alert.AlertType.ERROR);
        } catch (NoSuchFXMLFileExistingException ex) {
            showMessageBox("Error", ex.getMessage(), "Could not load fxml file! \nPlease make sure the file name is correct.", Alert.AlertType.ERROR);
        } catch (UnexpectedException ex) {
            showMessageBox("Error", ex.getMessage(), "Something unexpected happened while trying to open the fxml file.", Alert.AlertType.ERROR);
        }
    }

    private void loginUser() {
        if (isUserRegistered()) {
            if (isPasswordCorrect()) {
                errorText.setVisible(false);
                closeCurrentPage(loginBtn);
                getUserInfo();
                showMessageBox("Login", "Done!", "Logged in successfully", Alert.AlertType.INFORMATION);
            } else {
                showError("Wrong Password!");
            }
        } else {
            showError("User Is Not Registered");
        }
    }

    private void showError(String errorMessage) {
        errorText.setText(errorMessage);
        errorText.setVisible(true);
    }

    private void getUserInfo() {
        loggedInCustomer = CustomerDAOFactory.createCustomerDAO().getRecordById(nationalCodeField.getText());
        fillUserInformation(loggedInCustomer);
    }

    private boolean isUserRegistered() {
        try {
            return CustomerDAOFactory.createCustomerDAO().getRecordById(nationalCodeField.getText()) != null;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    private boolean isPasswordCorrect() {
        try {
            return CustomerDAOFactory.createCustomerDAO()
                    .getRecordById(nationalCodeField.getText())
                    .getPassword()
                    .equals(passwordField.getText());
        } catch (RuntimeException e) {
            return false;
        }
    }
}
