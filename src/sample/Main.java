package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Klasa Main z ktorej uruchamiana jest gra.
 */
public class Main extends Application {

    /**
     * Pole do zmiany pozycji okna - X
     */
    public double xOffset = 0;
    /**
     * Pole do zmiany pozycji okna - Y
     */
    public double yOffset = 0;

    /**
     * Metoda uruchamiajaca gre
     * @param primaryStage Glowna scena
     * @throws Exception blad ladowania pliku .fxml
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        //grab your root here
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        //move around here
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        //set transparent
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        //primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) { launch(args); }
}