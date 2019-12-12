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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.util.Timer;


//import javax.swing.text.Style;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.TimerTask;

import static java.lang.Character.*;
import static javafx.event.ActionEvent.ACTION;

public class Controller {

    @FXML
    private TextArea textInputField;
    @FXML
    private TextFlow textToWriteLabel;
    @FXML
    private Label testLabel;
    @FXML
    public Label sekundnik;

    private String tekst = "siema";
    private int wordCounter = 0;
    private int letterCounter = 0;
    String[] newContent;
    char[] currentLetterWord;
    private int goodWords = 0;
    private int errorWords = 0;
    private int letterGood = 0;
    private int letterError = 0;
    private boolean isBackspaceKey = false;
    private Text[] t;
    private Text[] separator;
    private boolean mistake = false;
    private int mistakeCounter = 0;
    private boolean isStarted = false;
    private int iloscSlowWczytanych = 0;
    public boolean isTimeStarted = false;
    public Main.Czas czas;

    public void refreshTime(){

    }
    public void resetAll(){
        wordCounter = 0;
        letterCounter = 0;
        goodWords = 0;
        errorWords = 0;
        isBackspaceKey = false;
        mistakeCounter = 0;
        iloscSlowWczytanych = 0;
    }
    @FXML
    public void isBackspace(KeyEvent e) {

        if (e.getCode() == KeyCode.BACK_SPACE) {
            //System.out.println("Backspace chuj");
            isBackspaceKey = true;
            if (letterCounter != 0) {
                letterCounter--;
            }
            if (mistake) {
                mistakeCounter--;
            }
            if (mistakeCounter == 0){
                t[wordCounter].setFill(Color.YELLOW);
                mistake = false;
            }

        } else {
            //System.out.println("Nie jest to backsapce");
            isBackspaceKey = false;
        }
    }

    @FXML
    public void keyPressed(KeyEvent e) {
        if(!isTimeStarted){
            czas.start();
            isTimeStarted = true;
        }

        if (!isBackspaceKey) {
            if (isSpaceChar(e.getCharacter().charAt(0))) {    //jezeli spacja to koniec słowa i sprawdzenie poprawnosci
               textInputField.deletePreviousChar();

                System.out.println("j"+textInputField.getText()+"j");

                if (wordCounter + 1 < newContent.length)
                   t[wordCounter + 1].setFill(Color.YELLOW);

                if (wordCounter != newContent.length) {

                    if (textInputField.getText().equals(newContent[wordCounter])) {
                        System.out.println("zgadza sie");
                        goodWords++;
                        t[wordCounter].setFill(Color.GREEN);
                        wordCounter++;
                        textInputField.deletePreviousChar();
                        textInputField.clear();
                        if(wordCounter % 9 == 0){
                            switchLine();
                            System.out.println("ilosclowwczytanych: "+iloscSlowWczytanych);
                            t[iloscSlowWczytanych].setFill(Color.YELLOW);
                        }
                    } else {
                        System.out.println("nie zgadza sie");
                        errorWords++;
                        t[wordCounter].setFill(Color.RED);
                        wordCounter++;
                        textInputField.deletePreviousChar();
                        textInputField.clear();
                        if(wordCounter % 9 == 0){
                            switchLine();
                            System.out.println("ilosclowwczytanych: "+iloscSlowWczytanych);
                        }
                    }

                    mistakeCounter = 0;
                    mistake = false;
                    letterCounter = 0;
                    if (wordCounter != newContent.length) {
                        currentLetterWord = newContent[wordCounter].toCharArray();
                    }

                }

                if (wordCounter == newContent.length) { // koniec wpisywania
                    czas.stop();

                    textInputField.setEditable(false);
                    testLabel.setText("Koniec wpisywania! Poprawnych słów: " + goodWords + " Błędnych słów: " + errorWords);
                    System.out.println("Koniec dddddddddddddddddddddd");
                    System.out.println("Poprawnych słów: " + goodWords);
                    System.out.println("Błędnych słów: " + errorWords);

                    resetAll();
                }
            }
            if (wordCounter != newContent.length & letterCounter < currentLetterWord.length) {
                System.out.println("litera: " + currentLetterWord[letterCounter]);

                if (e.getCharacter().charAt(0) == currentLetterWord[letterCounter] & !isSpaceChar(e.getCharacter().charAt(0))) {
                    if (!mistake) {
                        System.out.println("ruwna sie ta litera");
                        t[wordCounter].setFill(Color.YELLOW);
                        letterGood++;
                    }else
                        mistakeCounter++;
                } else if (e.getCharacter().charAt(0) != currentLetterWord[letterCounter] & !isSpaceChar(e.getCharacter().charAt(0))) {
                    System.out.println("nieruwna sie ta litera");
                    t[wordCounter].setFill(Color.RED);
                    letterError++;
                    mistake = true;
                    mistakeCounter++;
                }
                if (!isSpaceChar(e.getCharacter().charAt(0)))
                    letterCounter++;
            }
        }
    }
    public void switchLine(){

            iloscSlowWczytanych +=9;
            textToWriteLabel.getChildren().clear();
            if(iloscSlowWczytanych + 17 < newContent.length){
            for (int i = iloscSlowWczytanych; i < iloscSlowWczytanych + 18; i++) {
                t[i] = new Text(newContent[i] + "  ");
                textToWriteLabel.getChildren().add(t[i]);
                t[i].setFill(Color.WHITE);
                if(i==iloscSlowWczytanych+8)
                    textToWriteLabel.getChildren().add(separator[0]);
            }
            t[wordCounter].setFill(Color.YELLOW);
            }else if (iloscSlowWczytanych +18 > newContent.length){
                for (int i = iloscSlowWczytanych; i < newContent.length; i++) {
                    t[i] = new Text(newContent[i] + "  ");
                    textToWriteLabel.getChildren().add(t[i]);
                    t[i].setFill(Color.WHITE);
                    if(i==iloscSlowWczytanych+8)
                        textToWriteLabel.getChildren().add(separator[0]);
                } t[wordCounter].setFill(Color.YELLOW);
            }

    }
    public void loadTekst() throws IOException {
        wordCounter = 0;
        letterCounter = 0;
        textToWriteLabel.getChildren().clear();
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/sample/slowa.txt")));
            newContent = content.split(" ");
            currentLetterWord = newContent[wordCounter].toCharArray();
            t = new Text[newContent.length];
            separator = new Text[1];
            separator[0] = new Text("\n");

            if(newContent.length > 10){
                for (int i = iloscSlowWczytanych; i < 18 +iloscSlowWczytanych; i++) {
                    t[i] = new Text(newContent[i] + " ");
                    textToWriteLabel.getChildren().add(t[i]);
                    t[i].setFill(Color.WHITE);
                    if(i==iloscSlowWczytanych+8)
                        textToWriteLabel.getChildren().add(separator[0]);
                }
            }else{
                for (int i = 0; i < newContent.length; i++) {
                    t[i] = new Text(newContent[i] + " ");
                    textToWriteLabel.getChildren().add(t[i]);
                    t[i].setFill(Color.WHITE);
                }
            }

            t[0].setFill(Color.YELLOW);
            System.out.println("Poprawnie załadowano plik.");
            textInputField.setEditable(true);
            textInputField.clear();
        } catch (IOException e) {
            System.out.println("Błąd ładowania pliku");
        }
    }

    public void setTeksto(ActionEvent event) throws IOException {
        loadTekst();


    }

    @FXML
    private void switchToPractice(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Practice.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
        //loadTekst();
    }

    @FXML
    private void switchToHome(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

        //System.exit(0);
    }

    @FXML
    private void switchToAbout(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/About.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

        System.exit(0);
    }

    @FXML
    private void switchToHelp(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Help.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

        //System.exit(0);
    }

    @FXML
    private void switchToTest(ActionEvent event) throws IOException {

        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Test.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

    }

    public void mobbyn() {
        System.out.println("Siema");
        System.exit(0);
    }

    void initialize() {

    }
}