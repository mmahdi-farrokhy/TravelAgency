package main;

import controller.MainWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import utilities.GUIUtils;

public class Main extends Application {
    public static MainWindowController mainWindowController;

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../MainWindow.fxml"));
        loader.load();
        mainWindowController = loader.getController();
        primaryStage.setTitle("Travel Agency");
        primaryStage.setScene(new Scene(loader.getRoot()));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> closeApplication());
    }

    private static void fixMainWindowSize(Stage primaryStage) {
        primaryStage.setMinWidth(410);
        primaryStage.setMaxWidth(410);
        primaryStage.setMinHeight(435);
        primaryStage.setMaxHeight(435);
    }

    private void closeApplication() {
        Platform.exit();
        System.exit(0);
    }
}
