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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;

import static java.lang.Character.*;
import static javafx.event.ActionEvent.ACTION;

public class Controller {

    @FXML
    private TextArea textInputField;
    @FXML
    private Label textToWriteLabel;
    @FXML
    private Label testLabel;

    private String tekst = "siema";
    private int wordCounter = 0;
    private int letterCounter = 0;
    String[] newContent;
    char[] currentLetterWord;
    private int goodWords = 0;
    private int errorWords = 0;
    private int letterGood = 0;
    private int letterError = 0;

    //String[] newContent = "";

//    @FXML
//    public static String readAllBytes(String filePath){
//        String content = new String();
//        try{
//            content = new String (Files.readAllBytes(Paths.get(filePath)));
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//        return content;
//    }

    @FXML
    public void keyPressed(KeyEvent e){

       if( Character.isSpaceChar(e.getCharacter().charAt(0)) ){    //jezeli spacja to koniec słowa i sprawdzenie poprawnosci
//           System.out.println("Spacja");
//           System.out.println("Tekst do wpisania: "+textToWriteLabel.getText());
//           System.out.println("tekst z pola: "+textInputField.getText());
           textInputField.deletePreviousChar();


           if(wordCounter  != newContent.length) {


               if (textInputField.getText().equals(newContent[wordCounter])) {
                   System.out.println("zgadza sie");
                   goodWords++;
                   wordCounter++;
               } else {
                   System.out.println("nie zgadza sie");
                   errorWords++;
                   wordCounter++;
//               currentLetterWord = newContent[wordCounter].toCharArray();
               }

               //wordCounter++;
               textInputField.clear();
               letterCounter = 0;
               if (wordCounter != newContent.length) {
                   currentLetterWord = newContent[wordCounter].toCharArray();
               }

           }
                                   //System.out.println(Character.isSpaceChar(e.getCharacter().charAt(0))); //Wykrywanie spacji

           if(wordCounter  == newContent.length){
               testLabel.setText("Koniec wpisywania! Poprawnych słów: " +goodWords+ " Błędnych słów: "+errorWords+ " Poprawnych liter: " +letterGood+ " Błędnych liter: "+letterError);
               System.out.println("Koniec dddddddddddddddddddddd");
               System.out.println("Poprawnych słów: " +goodWords);
               System.out.println("Błędnych słów: " +errorWords);
               System.out.println("Poprawnych liter: " +letterGood);
               System.out.println("Błędnych liter: " +letterError);
           }
       }/////////////////////////////////////////////////////////////////////////////
        if(wordCounter != newContent.length & letterCounter < currentLetterWord.length){
            System.out.println("litera: " + currentLetterWord[letterCounter]);

            if(e.getCharacter().charAt(0) == currentLetterWord[letterCounter] & !Character.isSpaceChar(e.getCharacter().charAt(0))){
                System.out.println("ruwna sie ta litera");
                letterGood++;
            }else if(e.getCharacter().charAt(0) != currentLetterWord[letterCounter] & !Character.isSpaceChar(e.getCharacter().charAt(0))){
                System.out.println("nieruwna sie ta litera");
                letterError++;
            }

            if( !Character.isSpaceChar(e.getCharacter().charAt(0)) )
                letterCounter++;
        }

    }

    public void loadTekst()throws IOException{
        wordCounter = 0;
        String content = new String();

        try{
            content = new String (Files.readAllBytes(Paths.get("src/sample/slowa.txt")));
        }
        catch(IOException e){
            e.printStackTrace();
        }

        newContent = content.split(" ");
        currentLetterWord = newContent[wordCounter].toCharArray();
        System.out.println("litera: " + currentLetterWord[0]);
        textToWriteLabel.setText(content);
    }

    public void setTeksto(ActionEvent event)throws IOException{
//        wordCounter = 0;
//        String content = new String();
//        try{
//            content = new String (Files.readAllBytes(Paths.get("src/sample/slowa.txt")));
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//
//        newContent = content.split(" ");
//        currentLetterWord = newContent[wordCounter].toCharArray();
//        System.out.println("litera: " + currentLetterWord[0]);
//        textToWriteLabel.setText(content);
        loadTekst();
    }

    @FXML
    private void switchToPractice(ActionEvent event)throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Practice.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
        //loadTekst();


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

