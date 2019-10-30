package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {
    String filePath = "slowa.txt";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }
    public static int readAllBytes(){
        String content = new String();
        try{
            content = new String (Files.readAllBytes(Paths.get("src/sample/slowa.txt")));
        }catch(IOException e){
            e.printStackTrace();
        }
        String[] newContent = content.split(" ");
        return newContent.length;
    }

    public static void main(String[] args) {
        System.out.println(readAllBytes());
        //System.out.println(readAllBytes("src/sample/slowa.txt"));
        //System.out.println(newContent[0]);
        launch(args);

    }

}
