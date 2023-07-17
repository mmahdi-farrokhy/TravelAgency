package controller;

import datalayer.factories.CustomerDAOFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Main;
import utilities.ButtonActionInitializer;

import java.net.URL;
import java.util.ResourceBundle;

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
        ButtonActionInitializer buttonActionInitializer = new ButtonActionInitializer();
        buttonActionInitializer.setOnActionMethods(loginBtn, 8, this::loginUser);
        accountCheckText.setOnMouseClicked(e -> openPage(this, "..//SignUpPage.fxml"));
    }

    private void loginUser() {
        if (userExisting() && correctPassword()) {
            errorText.setVisible(false);
            closePageAfterOperation(loginBtn);
            getUserInfo();
            showMessageBox("Login", "Done!", "Logged in successfully", Alert.AlertType.INFORMATION);
        } else {
            errorText.setVisible(true);
        }
    }

    private void getUserInfo() {
        Main.loggedInCustomer = CustomerDAOFactory.createCustomerDAO().getRecordById(nationalCodeField.getText());
        fillUserInformation(Main.loggedInCustomer);
    }

    private boolean userExisting() {
        try {
            return CustomerDAOFactory.createCustomerDAO().getRecordById(nationalCodeField.getText()) != null;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    private boolean correctPassword() {
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
