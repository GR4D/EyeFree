package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLOutput;

import static javafx.event.ActionEvent.ACTION;

public class Controller {
    private Button button;
    @FXML
    private Label textToWriteLabel;
    private String tekst = "siema Byku";


    public void setTeksto(ActionEvent event)throws IOException{
        //System.out.println("przycisk dziala");
        textToWriteLabel.setText(tekst);
    }
    @FXML
    private void switchToPractice(ActionEvent event)throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Practice.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

        //System.exit(0);
    }
    @FXML
    private void switchToHome(ActionEvent event)throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

        //System.exit(0);
    }
    @FXML
    private void switchToAbout(ActionEvent event)throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/About.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

        System.exit(0);
    }
    @FXML
    private void switchToHelp(ActionEvent event)throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Help.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

        //System.exit(0);
    }
    @FXML
    private void switchToTest(ActionEvent event)throws IOException {

        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Test.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();


    }

    public void mobbyn(){
        System.out.println("Siema");
        System.exit(0);
    }

    void initialize() {

    }
}

