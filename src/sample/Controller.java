package sample;

import javafx.application.Platform;
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

import java.security.Key;
import java.util.Date;
import java.util.Timer;


//import javax.swing.text.Style;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
    private boolean isFinished = false;
    private int iloscSlowWczytanych = 0;
    public boolean isTimeStarted = false;
    public int time = 12;
    public int minutes = (time % 3600) / 60;
    public int seconds= time % 60;
    public String file = "src/sample/slowa.txt";
    TimerTest task1 = new TimerTest();
    Timer timer = new Timer();



    class TimerTest extends TimerTask{
        @Override
        public void run(){
            displayTimer();
            if(time == 0){
                cancel();
                Platform.runLater(() -> typingEnd());
            }
        }
    }

    @FXML
    public void displayTimer(){
        time--;
        minutes = (time % 3600) / 60;
        seconds= time % 60;

        Platform.runLater(() -> sekundnik.setText(String.format("%d:%02d", minutes, seconds )));
        System.out.println(String.format("%d:%02d", minutes, seconds ));

    }
    public void resetAll(){
        wordCounter = 0;
        letterCounter = 0;
        goodWords = 0;
        errorWords = 0;
        isBackspaceKey = false;
        mistakeCounter = 0;
        iloscSlowWczytanych = 0;
        mistake = false;
    }
    @FXML
    public void isBackspace(KeyEvent e) {

        if (e.getCode() == KeyCode.BACK_SPACE) {
            isBackspaceKey = true;

            if (letterCounter != 0 & ( textInputField.getText().length() < newContent[wordCounter].length()) ) {
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
            isBackspaceKey = false;
        }
    }

    @FXML
    public void spellCheck(KeyEvent e){
        System.out.println("litera: " + currentLetterWord[letterCounter]);

        if (e.getCharacter().charAt(0) == currentLetterWord[letterCounter] & !isSpaceChar(e.getCharacter().charAt(0)) ) {
            if (!mistake) {
                System.out.println("ruwna sie ta litera");
                t[wordCounter].setFill(Color.YELLOW);
                letterGood++;
            } else{
                mistakeCounter++;
            }

        } else if (e.getCharacter().charAt(0) != currentLetterWord[letterCounter] & !isSpaceChar(e.getCharacter().charAt(0)) ) {
            System.out.println("nieruwna sie ta litera");
            t[wordCounter].setFill(Color.RED);
            letterError++;
            mistake = true;
            mistakeCounter++;
            System.out.println("iloscBledowjakichstam: "+ mistakeCounter);
        }

        if (!isSpaceChar(e.getCharacter().charAt(0)))
            letterCounter++;
    }
    @FXML
    public void typingEnd(){
        timer.cancel();
        textInputField.clear();
        textInputField.setEditable(false);
        isFinished = true;
        testLabel.setText("Koniec wpisywania! Poprawnych słów: " + goodWords + " Błędnych słów: " + errorWords);
        System.out.println("Poprawnych słów: " + goodWords);
        System.out.println("Błędnych słów: " + errorWords);
        resetAll();
    }
    public void typingEndBeforeTimer(){
        timer.cancel();
        typingEnd();
    }
    @FXML
    public void typingCheck(KeyEvent e){
        if(!isBackspaceKey) {
            if (isSpaceChar(e.getCharacter().charAt(0))) {    //jezeli spacja to koniec słowa i sprawdzenie poprawnosci
                textInputField.deletePreviousChar();

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
                        if (wordCounter % 9 == 0) {
                            switchLine();
                            System.out.println("ilosclowwczytanych: " + iloscSlowWczytanych);
                            t[iloscSlowWczytanych].setFill(Color.YELLOW);
                        }
                    } else {
                        System.out.println("nie zgadza sie");
                        errorWords++;
                        t[wordCounter].setFill(Color.RED);
                        wordCounter++;
                        textInputField.deletePreviousChar();
                        textInputField.clear();
                        if (wordCounter % 9 == 0) {
                            if(!isFinished) {
                                switchLine();
                            }
                            System.out.println("ilosclowwczytanych: " + iloscSlowWczytanych);
                        }
                    }

                    mistakeCounter = 0;
                    mistake = false;
                    letterCounter = 0;
                    if (wordCounter != newContent.length) {
                        currentLetterWord = newContent[wordCounter].toCharArray();
                    }
                }
                if (wordCounter == newContent.length) {
                    typingEnd();
                    //typingEndBeforeTimer();
                }

            }

            if (wordCounter != newContent.length & letterCounter < currentLetterWord.length) {
                spellCheck(e);
            } else if (textInputField.getText().length() > newContent[wordCounter].length()){
                mistake = true;
                mistakeCounter++;
            }
        }

        if (mistake){
            t[wordCounter].setFill(Color.RED);
        }
    }
    @FXML
    public void keyPressed(KeyEvent e) {//typing

        if (!isTimeStarted){
            timer.schedule(task1, 1000,1000);
            isTimeStarted = true;
        }
        typingCheck(e);
        System.out.println("iloscBledow: "+mistakeCounter);
        System.out.println("czy bledy sa?: " + mistake);
    }

    public void switchLine(){
            iloscSlowWczytanych +=9;
            textToWriteLabel.getChildren().clear();
            if(iloscSlowWczytanych + 17 < newContent.length){
            for (int i = iloscSlowWczytanych; i < iloscSlowWczytanych + 18; i++) {
                t[i] = new Text(newContent[i] + " ");
                textToWriteLabel.getChildren().add(t[i]);
                t[i].setFill(Color.WHITE);
                if(i==iloscSlowWczytanych+8)
                    textToWriteLabel.getChildren().add(separator[0]);
            }
            t[wordCounter].setFill(Color.YELLOW);
            }else if (iloscSlowWczytanych +18 > newContent.length){
                for (int i = iloscSlowWczytanych; i < newContent.length; i++) {
                    t[i] = new Text(newContent[i] + " ");
                    textToWriteLabel.getChildren().add(t[i]);
                    t[i].setFill(Color.WHITE);
                    if(i==iloscSlowWczytanych+8)
                        textToWriteLabel.getChildren().add(separator[0]);
                } t[wordCounter].setFill(Color.YELLOW);
            }
    }
    public void lineLoader(int i){
        t[i] = new Text(newContent[i] + " ");
        textToWriteLabel.getChildren().add(t[i]);
        t[i].setFill(Color.WHITE);
    }
    public void loadTekst(String path) throws IOException {
        resetAll();
        textToWriteLabel.getChildren().clear();
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            newContent = content.split(" ");
            currentLetterWord = newContent[wordCounter].toCharArray();
            t = new Text[newContent.length];
            separator = new Text[1];
            separator[0] = new Text("\n");

            if(newContent.length > 10){
                for (int i = iloscSlowWczytanych; i < 18 +iloscSlowWczytanych; i++) {
                    lineLoader(i);
                    if(i==iloscSlowWczytanych+8)
                        textToWriteLabel.getChildren().add(separator[0]);
                }
            }else{
                for (int i = 0; i < newContent.length; i++) {
                    lineLoader(i);
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
        loadTekst(file);
        sekundnik.setText(String.format("%d:%02d", minutes, seconds ));
    }

    @FXML
    private void switchToPractice(ActionEvent event) throws IOException, InterruptedException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Practice.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    private void switchToHome(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }
    @FXML
    private void quit(ActionEvent event) throws IOException{
        System.exit(0);
    }
    @FXML
    private void switchToAbout(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/About.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    private void switchToHelp(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Help.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    @FXML
    private void switchToTest(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Test.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    void initialize() {

    }
}