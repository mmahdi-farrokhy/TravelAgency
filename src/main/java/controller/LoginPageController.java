package controller;

import dataLayer.CustomerTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Customer;

import java.net.URL;
import java.sql.SQLException;
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
    }

    private void loginUser() {
        if (userExisting())
            errorText.setVisible(false);
        else {
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

    private void showMessageBox(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}
