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
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLOutput;

import static java.lang.Character.*;
import static javafx.event.ActionEvent.ACTION;

public class Controller {
    private Button button;


    @FXML
    private TextArea textInputField;
    @FXML
    private Label textToWriteLabel;
    private String tekst = "siema";

    @FXML
    public void keyPressed(KeyEvent e){

       if( Character.isSpaceChar(e.getCharacter().charAt(0)) ){
           System.out.println("Spacja");
           System.out.println("Tekst do wpisania: "+textToWriteLabel.getText());
           System.out.println("tekst z pola: "+textInputField.getText());
           textInputField.deletePreviousChar();
           if(textInputField.getText().equals(textToWriteLabel.getText())){
               System.out.println("zgadza sie");
           }else
               System.out.println("nie zgadza sie");
           textInputField.clear();
       }
       //System.out.println(Character.isSpaceChar(e.getCharacter().charAt(0))); //Wykrywanie spacji
    }





    public void setTeksto(ActionEvent event)throws IOException{

        //textInputField.clear();
        //System.out.println("przycisk dziala");
        //textToWriteLabel.setText(tekst);
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

