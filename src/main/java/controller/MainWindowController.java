package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
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
                loginStage.setScene(new Scene(root));
                loginStage.initModality(Modality.APPLICATION_MODAL);
                loginStage.show();
                loginStage.setOnCloseRequest(e -> closePage());
            }
        } catch (Exception e) {
            GUIUtils.showMessageBox("Error", "UI load failed", "Could not load UI from the fxml file");
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
