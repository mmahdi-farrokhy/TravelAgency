package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.GUIUtils;

import java.lang.reflect.Field;
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
        setButtonAction(loginSignupBtn, "../LoginPage.fxml");
        setButtonAction(editCustomerBtn, "../EditCustomerPage.fxml");
        setButtonAction(flightsListBtn, "../FlightsListPage.fxml");
        setButtonAction(orderHistoryBtn, "../OrderHistoryPage.fxml");
    }

    private void setButtonAction(Button loginSignupBtn, String pageName) {
        loginSignupBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), 15));
        loginSignupBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), 15));
        loginSignupBtn.setOnAction(e -> GUIUtils.openPage(this, pageName));
    }

    public void setFieldText(String fieldName, String newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = getClass().getDeclaredField(fieldName);
        TextField textField = (TextField) field.get(this);
        field.set(this, textField);
        field.setAccessible(true);
        textField.setText(newValue);
    }
}
