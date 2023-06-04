package controller;

import dataLayer.CustomerTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import model.Customer;
import utilities.GUIUtils;

import java.net.URL;
import java.util.ResourceBundle;

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
        loginBtn.setOnAction(e -> loginUser());
        accountCheckText.setOnMouseClicked(e -> showSignupPage());
    }

    private void showSignupPage() {
        
    }

    private void loginUser() {
        if (userExisting() && correctPassword()) {
            errorText.setVisible(false);
            closeLoginPage();
            getUserInfo();
        } else {
            errorText.setVisible(true);
        }
    }

    private boolean userExisting() {
        try {
            CustomerTable customerTable = new CustomerTable();
            Customer recordById = customerTable.getRecordById(nationalCodeField.getText());
            return recordById != null;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    private boolean correctPassword() {
        try {
            CustomerTable customerTable = new CustomerTable();
            String password = customerTable.getRecordById(nationalCodeField.getText()).getPassword();
            return password.equals(passwordField.getText());
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void getUserInfo() {
        try {
            Customer customer = new CustomerTable().getRecordById(nationalCodeField.getText());
            Main.mainWindowController.setFieldText("usernameField", "Username: " + customer.getNationalCode());
            Main.mainWindowController.setFieldText("fullNameField", "Full Name: " + customer.getFullName().getRawFullName());
            Main.mainWindowController.setFieldText("phoneNumberField", "Phone Number: " + customer.getPhoneNumber());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void closeLoginPage() {
        ((Stage) loginBtn.getScene().getWindow()).close();
        MainWindowController.loginStage.close();
        MainWindowController.loginStage = null;
    }
}
