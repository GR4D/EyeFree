package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.Character.isSpaceChar;

//import javax.swing.text.Style;

public class PracticeController_good {

    @FXML
    private TextArea textInputField;
    @FXML
    private TextFlow textToWriteLabel;
    @FXML
    private Label testLabel;
    @FXML
    public Label sekundnik;
    @FXML
    public Label levelDisplay;
    private String tekst = "siema";
    private int wordCounter = 0;
    private int letterCounter = 0;
    String[] newContent;
    char[] currentLetterWord;
    private int goodWords = 0;
    private int errorWords = 0;
    private boolean isBackspaceKey = false;
    private Text[] t;
    private Text[] separator;
    private boolean isMistake = false;
    private int mistakeCounter = 0;
    private boolean isFinished = false;
    private int iloscSlowWczytanych = 0;
    public boolean isTimeStarted = false;
    public int time = 60;
    public int minutes = (time % 3600) / 60;
    public int seconds= time % 60;
    public String file = "src/sample/1.txt";
    public int level = 1;
    int[] liczby;
    String nazwapliczku;

    TimerTest task1 = new TimerTest();
    Timer timer = new Timer();
    Random random = new Random();

    class TimerTest extends TimerTask{
        @Override
        public void run(){
            displayTimer();
            if(time == 0){
                timer.cancel();
                timer.purge();
                Platform.runLater(() -> typingEnd());
            }
        }
    }
    @FXML
    public void reset(){
        resetAll();
        //switchToPractice(ActionEvent);
    }
    @FXML
    public void displayTimer(){
        time--;
        minutes = (time % 3600) / 60;
        seconds= time % 60;

        Platform.runLater(() -> sekundnik.setText(String.format("%d:%02d", minutes, seconds )));
        System.out.println(String.format("%d:%02d", minutes, seconds ));
        //System.out.println("wordCounter: " + wordCounter);

    }
    public void resetAll(){
        wordCounter = 0;
        letterCounter = 0;
        goodWords = 0;
        errorWords = 0;
        isBackspaceKey = false;
        isMistake = false;
        mistakeCounter = 0;
        iloscSlowWczytanych = 0;
//        isFinished = false;
        isTimeStarted =  false;
        time = 60;
        minutes = (time % 3600) / 60;
        seconds= time % 60;


    }
    @FXML
    public void isBackspace(KeyEvent e) {

        if (e.getCode() == KeyCode.BACK_SPACE) {
            isBackspaceKey = true;

            if (letterCounter != 0 & ( textInputField.getText().length() < newContent[liczby[wordCounter]].length()) ) {
                letterCounter--;
            }
            if (isMistake) {
                mistakeCounter--;
            }
            if (mistakeCounter == 0){
                t[wordCounter].setFill(Color.YELLOW);
                isMistake = false;
            }

        } else {
            isBackspaceKey = false;
        }
    }

    @FXML
    public void spellCheck(KeyEvent e){
        System.out.println("litera: " + currentLetterWord[letterCounter]);

        if (e.getCharacter().charAt(0) == currentLetterWord[letterCounter] & !isSpaceChar(e.getCharacter().charAt(0)) ) {
            if (!isMistake) {
                System.out.println("ruwna sie ta litera");
                t[wordCounter].setFill(Color.YELLOW);
            } else{
                mistakeCounter++;
            }

        } else if (e.getCharacter().charAt(0) != currentLetterWord[letterCounter] & !isSpaceChar(e.getCharacter().charAt(0)) ) {
            System.out.println("nieruwna sie ta litera");
            t[wordCounter].setFill(Color.RED);
            isMistake = true;
            mistakeCounter++;
            System.out.println("iloscBledowjakichstam: "+ mistakeCounter);
        }

        if (!isSpaceChar(e.getCharacter().charAt(0)))
            letterCounter++;
    }
    @FXML
    public void typingEnd(){
        textInputField.clear();
        textInputField.setEditable(false);
        isFinished = true;
        timer.cancel();
        timer.purge();
        time = 60;
        level++;
        testLabel.setText("Koniec wpisywania! Poprawnych słów: " + goodWords + " Błędnych słów: " + errorWords);
        System.out.println("Poprawnych słów: " + goodWords);
        System.out.println("Błędnych słów: " + errorWords);
        //resetAll();
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

                if (wordCounter + 1 < liczby.length)
                    t[wordCounter +1].setFill(Color.YELLOW);

                if (wordCounter != liczby.length) {

                    if (textInputField.getText().equals(newContent[liczby[wordCounter]])) {
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
                    isMistake = false;
                    letterCounter = 0;
                    if (wordCounter != liczby.length) {
                        //currentLetterWord = newContent[wordCounter].toCharArray();
                        currentLetterWord = newContent[liczby[wordCounter]].toCharArray();
                    }
                }
                if (wordCounter == liczby.length) {
                    typingEnd();
                    //wordCounter--;
                    //typingEndBeforeTimer();
                }

            }

            if (wordCounter != liczby.length && letterCounter < currentLetterWord.length) {
                spellCheck(e);
            } else if (wordCounter != liczby.length && textInputField.getText().length() > newContent[liczby[wordCounter]].length()){
                isMistake = true;
                mistakeCounter++;
            }
        }
        if (isMistake){
            t[wordCounter].setFill(Color.RED);
        }
    }
    @FXML
    public void keyPressed(KeyEvent e) throws Exception{//typing

       if(!isFinished & !isTimeStarted){
           timer.schedule(task1, 1000,1000);
           isTimeStarted = true;
           //System.out.println("idzie timer z try");
       }
        if(!isFinished) {
            typingCheck(e);
            //System.out.println("idzie typingcheck");
        }
//        System.out.println("iloscBledow: "+mistakeCounter);
//        System.out.println("czy bledy sa?: " + isMistake);
    }

    public void switchLine() {
        if (!isFinished){
            iloscSlowWczytanych += 9;
            textToWriteLabel.getChildren().clear();
            if (iloscSlowWczytanych + 17 < liczby.length) {
                for (int i = iloscSlowWczytanych; i < iloscSlowWczytanych + 18; i++) {
                    t[i] = new Text(newContent[liczby[i]] + "   ");
                    textToWriteLabel.getChildren().add(t[i]);
                    t[i].setFill(Color.WHITE);
                    if (i == iloscSlowWczytanych + 8)
                        textToWriteLabel.getChildren().add(separator[0]);
                }
                t[wordCounter].setFill(Color.YELLOW);
            } else if (iloscSlowWczytanych + 18 > liczby.length) {
                for (int i = iloscSlowWczytanych; i < liczby.length; i++) {
                    t[i] = new Text(newContent[liczby[i]] + "   ");
                    textToWriteLabel.getChildren().add(t[i]);
                    t[i].setFill(Color.WHITE);
                    if (i == iloscSlowWczytanych + 8)
                        textToWriteLabel.getChildren().add(separator[0]);
                }
                t[wordCounter].setFill(Color.YELLOW);
            }
        }
    }
    public void lineLoader(int i){
        t[i] = new Text(newContent[liczby[i]] + "   ");
        textToWriteLabel.getChildren().add(t[i]);
        t[i].setFill(Color.WHITE);
    }
    public void loadTekst(String path) throws IOException {
        //resetAll();
        textToWriteLabel.getChildren().clear();
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            newContent = content.split(" ");
            for (int i = 0; i < newContent.length ; i++) {
                System.out.println("indeks: "+i+"zawartosc: "+newContent[i]);
            }
            currentLetterWord = newContent[liczby[0]].toCharArray();
            //currentLetterWord = newContent[0].toCharArray();

            t = new Text[liczby.length];

            separator = new Text[1];
            separator[0] = new Text("\n");

            if(liczby.length > 10){
                for (int i = iloscSlowWczytanych; i < 18 +iloscSlowWczytanych; i++) {
                    lineLoader(i);
                    if(i==iloscSlowWczytanych+8)
                        textToWriteLabel.getChildren().add(separator[0]);
                }
            }else{
                for (int i = 0; i < liczby.length; i++) {
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
        resetAll();
        ///losowanie liczb
        Random randNum = new Random();
        Set<Integer> set = new LinkedHashSet<Integer>();

        int index = 0;
        while (set.size() < 50) {
            set.add(randNum.nextInt(50));
        }
        liczby = new int[set.size()];
        for(Integer i : set){
            liczby[index++] = i;
        }
        //set.toArray(liczby);
        //System.out.println("Random numbers with no duplicates = "+set);
        //System.out.println("zerowa: "+liczby[0]);
        //System.out.println("pierwsza:"+liczby[1]);

        //String nazwapliczku = "src/sample/text_files/5.txt";
        if(level <= 20) {
            nazwapliczku = "src/sample/text_files/"+level+".txt";
            loadTekst(nazwapliczku);
            levelDisplay.setText("Lekcja " +level+ ".");
        }else {
            resetAll();
            textToWriteLabel.getChildren().clear();
            System.out.println("Koniec gry!");
            //sekundnik.setText("");
        }

            System.out.println("level: "+level);


//        String nazwapliczku = "src/sample/text_files/"+Integer.toString(level) + ".txt";
        System.out.println(nazwapliczku);

        isFinished = false;
        timer.cancel();
        timer.purge();
        task1 = new TimerTest();
        timer = new Timer();
        testLabel.setText("");
        sekundnik.setText(String.format("%d:%02d", minutes, seconds ));
    }

    @FXML
    private void switchToPractice(ActionEvent event) throws IOException, InterruptedException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Practice.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
        if(isTimeStarted){
            timer.cancel();
            timer.purge();
        }
    }

    @FXML
    private void switchToHome(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
        if(isTimeStarted){
            timer.cancel();
            timer.purge();
        }
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
        if(isTimeStarted){
            timer.cancel();
            timer.purge();
        }
    }

    @FXML
    private void switchToHelp(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Help.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
        if(isTimeStarted){
            timer.cancel();
            timer.purge();
        }
    }

    @FXML
    private void switchToTest(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Test.fxml"));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
        if(isTimeStarted){
            timer.cancel();
            timer.purge();
        }
    }

    void initialize() {

    }
}