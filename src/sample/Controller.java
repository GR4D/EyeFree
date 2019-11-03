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


//import javax.swing.text.Style;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Arrays;

import static java.lang.Character.*;
import static javafx.event.ActionEvent.ACTION;

public class Controller {

    @FXML
    private TextArea textInputField;
    @FXML
    private TextFlow textToWriteLabel;
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
    private boolean isBackspaceKey = false;
    private int currentPositionLeft, currentPositionRight = 0;
    private Text[] t;
    private boolean mistake = false;
    private int mistakeCounter = 0;
    private boolean isStarted = false;

    @FXML
    public void isBackspace(KeyEvent e) {
        if (e.getCode() == KeyCode.BACK_SPACE) {
            //System.out.println("Backspace chuj");
            isBackspaceKey = true;
            if (letterCounter != 0) {
                letterCounter--;
            }
        } else {
            //System.out.println("Nie jest to backsapce");
            isBackspaceKey = false;
        }
    }

    @FXML
    public void keyPressed(KeyEvent e) {
        if (!isBackspaceKey) {
            if (isSpaceChar(e.getCharacter().charAt(0))) {    //jezeli spacja to koniec słowa i sprawdzenie poprawnosci
                //textToWriteLabel.selectRange(currentPositionLeft,currentPositionRight);
                textInputField.deletePreviousChar();


//              t[5].setFill(Color.RED);

                //textToWriteLabel.select
                if (wordCounter + 1 < newContent.length)
                    t[wordCounter + 1].setFill(Color.YELLOW);

                //System.out.println("x: "+currentPositionLeft+ " y: "+currentPositionRight);
                if (wordCounter != newContent.length) {

                    if (textInputField.getText().equals(newContent[wordCounter])) {
                        System.out.println("zgadza sie");
                        goodWords++;
                        t[wordCounter].setFill(Color.GREEN);
                        wordCounter++;
                    } else {
                        System.out.println("nie zgadza sie");
                        errorWords++;
                        t[wordCounter].setFill(Color.RED);
                        wordCounter++;

//               currentLetterWord = newContent[wordCounter].toCharArray();
                    }
                    //wordCounter++;
                    mistakeCounter = 0;
                    mistake = false;
                    textInputField.clear();
                    letterCounter = 0;
                    if (wordCounter != newContent.length) {
                        currentPositionLeft = currentPositionRight + 1;
                        currentLetterWord = newContent[wordCounter].toCharArray();
                        currentPositionRight += currentLetterWord.length + 1;
                    }
                }

                if (wordCounter == newContent.length) { // koniec wpisywania
                    textInputField.setEditable(false);
                    testLabel.setText("Koniec wpisywania! Poprawnych słów: " + goodWords + " Błędnych słów: " + errorWords);
                    System.out.println("Koniec dddddddddddddddddddddd");
                    System.out.println("Poprawnych słów: " + goodWords);
                    System.out.println("Błędnych słów: " + errorWords);
                }
            }
            if (wordCounter != newContent.length & letterCounter < currentLetterWord.length) {
                System.out.println("litera: " + currentLetterWord[letterCounter]);

                if (e.getCharacter().charAt(0) == currentLetterWord[letterCounter] & !isSpaceChar(e.getCharacter().charAt(0))) {
                    if (!mistake) {
                        System.out.println("ruwna sie ta litera");
                        t[wordCounter].setFill(Color.YELLOW);

                        letterGood++;
                    }
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
        } else {
            if (mistake){
                mistakeCounter--;
                if(mistakeCounter == 0)
                    t[wordCounter].setFill(Color.YELLOW);
            }
        }
    }

    public void loadTekst() throws IOException {
        wordCounter = 0;
        letterCounter = 0;
        textToWriteLabel.getChildren().clear();
        //textToWriteLabel.setStyle("-fx-highlight-fill: blue; -fx-highlight-text-fill: firebrick; -fx-font-size: 20px;");
        try {

            String content = new String(Files.readAllBytes(Paths.get("src/sample/slowa.txt")));
            newContent = content.split(" ");
            currentLetterWord = newContent[wordCounter].toCharArray();
            t = new Text[newContent.length];

            for (int i = 0; i < newContent.length; i++) {
                t[i] = new Text(newContent[i] + " ");
                textToWriteLabel.getChildren().add(t[i]);
                t[i].setFill(Color.WHITE);

            }
            //textToWriteLabel.setStyle("-fx-font-size: 25px; ");
            t[0].setFill(Color.YELLOW);

            currentPositionRight = currentLetterWord.length;


            System.out.println("Poprawnie załadowano plik.");
            textInputField.setEditable(true);
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Błąd ładowania pliku");
        }
    }

    public void setTeksto(ActionEvent event) throws IOException {
        loadTekst();
        //textToWriteLabel.selectNextWord();
    }

    @FXML
    private void switchToPractice(ActionEvent event) throws IOException {
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