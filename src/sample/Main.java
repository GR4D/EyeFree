package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
        primaryStage.setTitle("Eye Free");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.show();

    }

    public static class Czas{
        public Controller controllerr;
        int sekundy = 60;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {


            @Override
            public void run() {
                sekundy--;

               System.out.println("sekundy: "+sekundy);
                controllerr.sekundnik.setText("guw");            }
        };

        public void start(){
            timer.scheduleAtFixedRate(task, 1000, 1000);
        }
        public void stop(){
            timer.cancel();
            System.out.println("sekundy ze stopa: "+sekundy);
        }

    }

    public static void main(String[] args) { launch(args); }
}