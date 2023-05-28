package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = FXMLLoader.load(this.getClass().getResource("../MainWindow.fxml"));
        primaryStage.setTitle("Travel Agency");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
