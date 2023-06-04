package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Customer;
import utilities.GUIUtils;

import java.net.URL;
import java.util.ResourceBundle;

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
        Color borderColor = Color.rgb(232, 69, 117);
        Color backgroundColor = Color.rgb(41, 53, 86);

        loginSignupBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), borderColor, backgroundColor, 15));
        loginSignupBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), backgroundColor, 15));
        loginSignupBtn.setOnAction(e -> showPage("../LoginPage.fxml"));

        editCustomerBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), borderColor, backgroundColor, 15));
        editCustomerBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), backgroundColor, 15));
        editCustomerBtn.setOnAction(e -> showPage("../CustomerPage.fxml"));

        flightsListBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), borderColor, backgroundColor, 15));
        flightsListBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), backgroundColor, 15));
        flightsListBtn.setOnAction(e -> showPage("../FlightsListPage.fxml"));

        orderHistoryBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), borderColor, backgroundColor, 15));
        orderHistoryBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), backgroundColor, 15));
        orderHistoryBtn.setOnAction(e -> showPage("../OrderHistoryPage.fxml"));
    }

    private void showPage(String pageName) {
        try {
            if (loginStage == null) {
                VBox root = FXMLLoader.load(this.getClass().getResource(pageName));
                loginStage = new Stage();
                loginStage.setTitle("");
                loginStage.setScene(new Scene(root));
                loginStage.show();
            }
        } catch (Exception e) {
            GUIUtils.showMessageBox("Error", "UI load failed", "Could not load UI from the fxml file");
        }
    }

    public void setUsername(String newValue) {
        usernameField.setEditable(true);
        usernameField.setText(newValue);
        usernameField.setEditable(false);
    }
    public void setFullName(String newValue) {
        fullNameField.setEditable(true);
        fullNameField.setText(newValue);
        fullNameField.setEditable(false);
    }
    public void setPhoneNumber(String newValue) {
        phoneNumberField.setEditable(true);
        phoneNumberField.setText(newValue);
        phoneNumberField.setEditable(false);
    }
}
