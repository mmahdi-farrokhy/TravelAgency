package controller;

import dataLayer.CustomerTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
        loginBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), 15));
        loginBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), 15));
        loginBtn.setOnAction(e -> loginUser());
        accountCheckText.setOnMouseClicked(e -> GUIUtils.openPage(this, "..//SignUpPage.fxml"));
    }

    private void loginUser() {
        if (userExisting() && correctPassword()) {
            errorText.setVisible(false);
            GUIUtils.UserRegistryPage(loginBtn);
            getUserInfo();
            GUIUtils.showMessageBox("Login","Done!","Logged in successfully", Alert.AlertType.INFORMATION);
        } else {
            errorText.setVisible(true);
        }
    }

    private void getUserInfo() {
        Customer customer = new CustomerTable().getRecordById(nationalCodeField.getText());
        Main.loggedInCustomer = customer;
        GUIUtils.fillUserInformation(customer);
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
}
