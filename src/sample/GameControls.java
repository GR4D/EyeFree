package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Character.isSpaceChar;

/**
 * Klasa zawierajaca wszystkie metody sterujace rozgrywka
 */
public class GameControls extends GameWindow{
    /**
     * tworzy nowe zadanie dla timera
     */
    TimerTest task1 = new TimerTest();
    /**
     * nowy timer
     */
    Timer timer = new Timer();

    /**
     * Klasa definiujaca timer - zadanie ktore co sekunde odejmuje czas
     */
    class TimerTest extends TimerTask {
        @Override
        public void run(){
            displayTimer();
            if(time == 0){
                timer.cancel();
                timer.purge();
                Platform.runLater(() -> {
                    try {
                        typingEndTest();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    /**
     * Wyswietlanie stanu timera
     */
    @FXML
    public void displayTimer(){
        time--;
        minutes = (time % 3600) / 60;
        seconds= time % 60;

        Platform.runLater(() -> sekundnik.setText(String.format("%d:%02d", minutes, seconds )));
    }

    /**
     * Resetowanie wszystkich pol do stanu sprzed rozgrywki aby rozpoczac ponowne wprowadzanie danych
     */
    public void resetAll(){
        wordCounter = 0;
        letterCounter = 0;
        goodWords = 0;
        errorWords = 0;
        isBackspaceKey = false;
        isMistake = false;
        mistakeCounter = 0;
        iloscSlowWczytanych = 0;;
        isTimeStarted =  false;
        time = 60;
        minutes = (time % 3600) / 60;
        seconds= time % 60;
    }

    /**
     * Metoda sprawdzajaca czy wcisnieto backspace - sluzy do odpowiedniej orientacji aktualnego znaku w przypadku kasowania bledow
     * @param e wcisniety klawisz
     */
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

    /**
     * Sprawdzanie poprawnosci wprowadzenia pojedzynczego znaku - glownie do zmieniania kolorow tekstu i obliczania WPM
     * @param e wcisniety klawisz
     */
    @FXML
    public void spellCheck(KeyEvent e){

        if (e.getCharacter().charAt(0) == currentLetterWord[letterCounter] & !isSpaceChar(e.getCharacter().charAt(0)) ) {
            if (!isMistake) {
               //prawidlowa litera
                t[wordCounter].setFill(Color.YELLOW);
                goodLettersWpm++;
            } else{
                mistakeCounter++;
            }

        } else if (e.getCharacter().charAt(0) != currentLetterWord[letterCounter] & !isSpaceChar(e.getCharacter().charAt(0)) ) {
            //nieprawidlowa litera
            t[wordCounter].setFill(Color.RED);
            isMistake = true;
            mistakeCounter++;
        }

        if (!isSpaceChar(e.getCharacter().charAt(0)))
            letterCounter++;
    }

    /**
     * Glowna metoda sprawdzajaca poprawnosc wpisanego slowa. Kasuje wprowadzona spacje i sprawdza slowo. Koloruje tekst. Przechodzi do nastepnego slowa.
     * @param e wcisniety klawisz - do sprawdzania czy spacja
     */
    @FXML
    public void typingCheck(KeyEvent e){
        if(!isBackspaceKey) {
            if (isSpaceChar(e.getCharacter().charAt(0))) {    //jezeli spacja to koniec słowa i sprawdzenie poprawnosci
                textInputField.deletePreviousChar();

                if (wordCounter + 1 < liczby.length)
                    t[wordCounter +1].setFill(Color.YELLOW);

                if (wordCounter != liczby.length) {

                    if (textInputField.getText().equals(newContent[liczby[wordCounter]])) {
                       //poprawne slowo
                        goodWords++;
                        t[wordCounter].setFill(Color.GREEN);
                        wordCounter++;
                        textInputField.deletePreviousChar();
                        textInputField.clear();
                        if (wordCounter % 9 == 0) {
                            switchLine();
                            t[iloscSlowWczytanych].setFill(Color.YELLOW);
                        }
                    } else {
                        //niepoprawne slowo
                        errorWords++;
                        t[wordCounter].setFill(Color.RED);
                        wordCounter++;
                        textInputField.deletePreviousChar();
                        textInputField.clear();
                        if (wordCounter % 9 == 0) {
                            if(!isFinished) {
                                switchLine();
                            }
                        }
                    }
                    mistakeCounter = 0;
                    isMistake = false;
                    letterCounter = 0;
                    if (wordCounter != liczby.length) {
                        currentLetterWord = newContent[liczby[wordCounter]].toCharArray();
                    }
                }
                if (wordCounter == liczby.length) {//koniec wpisywania
                    if(!testOrPractice) {
                        try {
                            typingEndTest();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        typingEndPractice();
                    }
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

    /**
     * Metoda umozliwiajaca wczytywanie tekstu do textToWriteLabel partiami. Wczytuje dwie linie a po wprowadzeniu pierwszej druga zajmuje jej miejsce a wczytywana jest kolejna.
     * Umozliwia to plynna rozgrywke i nie przytlacza iloscia tekstu do wprowadzenia oraz pozwala na lepsze skupienie.
     */
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

    /**
     * Koniec Testu. Kasowanie timera, obliczanie wpm (wpm = poprawnie wprowadzone litery / 5 ,poniewaz uznaje sie 5 za srednia dlugosc slowa), wyswietlanie wynikow oraz zapisanie ich do pliku.
     * @throws IOException blad pliku
     */
    @FXML
    public void typingEndTest() throws IOException{//zostawic
        resultsLabel.setVisible(true);
        textInputField.clear();
        textInputField.setVisible(false);
        isFinished = true;
        timer.cancel();
        timer.purge();
        time = 60;
        wpm = goodLettersWpm / 5;

        resultsLabel.setText("Koniec wpisywania! Poprawnych słów: " + goodWords + " Błędnych słów: " + errorWords + "\nWPM: " + wpm);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        BufferedWriter writer = new BufferedWriter(
                new FileWriter("wyniki.txt", true)  //Set true for append mode
        );
        writer.newLine();   //Add new line
        writer.write(dtf.format(now) + " WPM: " + wpm);
        writer.close();

    }

    /**
     * Koniec jednego poziomu cwiczen. Sprawdzanie czy nie popelniono wiecej niz 8 bledow i czy czas pisania byl krotszy niz 2 minuty. Wyswietlanie wynikow.
     */
    @FXML
    public void typingEndPractice(){//zostawic
        resultsLabel.setVisible(true);
        textInputField.clear();
        textInputField.setEditable(false);
        isFinished = true;
        end = System.nanoTime();
        timePassed = (end-start)/1000000000;

        resultsLabel.setText("Koniec wpisywania! Poprawnych słów: " + goodWords + " Błędnych słów: " + errorWords);

        if(timePassed < 120  && errorWords < 8) {
            level++;
        }else{
            skipLevelButton.setVisible(true);
            resultsLabel.setText("Zrobiłeś za dużo błędów lub pisanie zajęło ci zbyt dużo czasu.\nSpróbuj ponownie aby przejść do kolejnego poziomu.\nTwoim celem jest mniej niż 8 błędów i czas maksymalnie 120 sekund.");
        }
    }

    /**
     * Wczytywanie pojedynczego losowego slowa z newContent do Text[].
     * @param i indeks danego slowa
     */
    public void wordLoader(int i){
        t[i] = new Text(newContent[liczby[i]] + "   ");
        textToWriteLabel.getChildren().add(t[i]);
        t[i].setFill(Color.WHITE);
    }

    /**
     * Poczatkowy odczyt slow z pliku. Tworzenie Text[], utawianie czasu i widoku okna. Gotowosc do wpisywania.
     * @param path sciezka do pliku
     */
    public void loadTekst(String path) {
        textToWriteLabel.getChildren().clear();
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            newContent = content.split(" ");
            for (int i = 0; i < newContent.length ; i++) {
            }
            currentLetterWord = newContent[liczby[0]].toCharArray();
            t = new Text[liczby.length];
            separator = new Text[1];
            separator[0] = new Text("\n");

            if(liczby.length > 10){
                for (int i = iloscSlowWczytanych; i < 18 +iloscSlowWczytanych; i++) {
                    wordLoader(i);
                    if(i==iloscSlowWczytanych+8)
                        textToWriteLabel.getChildren().add(separator[0]);
                }
            }else{
                for (int i = 0; i < liczby.length; i++) {
                    wordLoader(i);
                }
            }
            t[0].setFill(Color.YELLOW);
            textInputField.setEditable(true);
            textInputField.setVisible(true);
            textInputField.clear();
            if(!testOrPractice){
                sekundnik.setVisible(true);
                sekundnik.setText("1:00");
            }

        } catch (IOException e) {
            //System.out.println("Błąd ładowania pliku");
        }
    }

    /**
     * Odczyt zapisanych wynikow
     */
    public void readScores() {
        scoresLabel.setText("");
        try {
            String wyniki = new String (Files.readAllBytes(Paths.get("wyniki.txt")));
            scoresLabel.setText("Wyniki: \n" + wyniki);
            System.out.println("zap[isano wyniki ");
        }catch (IOException e){
            System.out.println("blad: "+e);
        }
    }

    /**
     * Metoda umozliwiajaca wyczyszczenie zapisanych wynikow.
     */
    public void deleteScores(){
        try
        {
            File f= new File("wyniki.txt");           //file to be delete
            if(f.delete())
                System.out.println(f.getName() + " deleted");   //getting and printing the file name
            else
                System.out.println("failed");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Wyjscie z gry
     */
    @FXML
    private void quit(){
        System.exit(0);
    }

    /**
     * Przejscie do okna HOME
     * @param event ruch myszka
     * @throws IOException blad ladowania pliku .fxml
     */
    @FXML
    private void switchToHome(ActionEvent event) throws IOException {

        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));

        movableScene(view2,event);
        if(isTimeStarted){
            timer.cancel();
            timer.purge();
        }
    }
    @FXML
    private void switchToAbout(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/About.fxml"));

        movableScene(view2,event);
        if(isTimeStarted){
            timer.cancel();
            timer.purge();
        }
    }

    @FXML
    private void switchToHelp(ActionEvent event) throws IOException {
        try{
            Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Help.fxml"));

            movableScene(view2,event);

            if(isTimeStarted){
                timer.cancel();
                timer.purge();
            }
        }catch (IOException e){
        }
    }

    @FXML
    private void switchToPractice(ActionEvent event) throws IOException, InterruptedException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Practice.fxml"));
        movableScene(view2,event);
    }

    @FXML
    private void switchToTest(ActionEvent event) throws IOException {
        Parent view2 = FXMLLoader.load(getClass().getResource("fxml/Test.fxml"));

        movableScene(view2,event);
    }
}
