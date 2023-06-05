package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilities.GUIUtils;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Objects;
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
        setButtonAction(loginSignupBtn, borderColor, backgroundColor, "../LoginPage.fxml");
        setButtonAction(editCustomerBtn, borderColor, backgroundColor, "../CustomerPage.fxml");
        setButtonAction(flightsListBtn, borderColor, backgroundColor, "../FlightsListPage.fxml");
        setButtonAction(orderHistoryBtn, borderColor, backgroundColor, "../OrderHistoryPage.fxml");
    }

    private void setButtonAction(Button loginSignupBtn, Color borderColor, Color backgroundColor, String pageName) {
        loginSignupBtn.setOnMouseEntered(e -> GUIUtils.setButtonStyle((Button) e.getSource(), borderColor, backgroundColor, 15));
        loginSignupBtn.setOnMouseExited(e -> GUIUtils.resetButtonStyle((Button) e.getSource(), backgroundColor, 15));
//        loginSignupBtn.setOnAction(e -> showPage(pageName));
        loginSignupBtn.setOnAction(e -> GUIUtils.switchUserRegistryPage(this,pageName));
    }

    private void showPage(String pageName) {
        try {
            VBox root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource(pageName)));
            loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.setResizable(false);
            loginStage.show();
            loginStage.setOnCloseRequest(e -> closePage());
        } catch (Exception e) {
            GUIUtils.showMessageBox("Error", "UI load failed", "Could not load UI from the fxml file", Alert.AlertType.ERROR);
        }
    }

    private void closePage() {
        loginStage.close();
        loginStage = null;
    }

    public void setFieldText(String fieldName, String newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = getClass().getDeclaredField(fieldName);
        TextField textField = (TextField) field.get(this);
        field.set(this, textField);
        field.setAccessible(true);
        textField.setText(newValue);
    }
}
