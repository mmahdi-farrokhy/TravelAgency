package main;

import controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        primaryStage.show();
    }
}
