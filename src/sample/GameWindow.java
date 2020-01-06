package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Klasa zawierajaca wszystkie elementy okna gry oraz pola niezbedne do rozgrywki.
 */
public class GameWindow extends Main {
    /** Pole do wpisywania tekstu  */
    @FXML
    protected TextArea textInputField;
    /** Wczytany tekst ktory uzytkownik ma wpisac  */
    @FXML
    protected TextFlow textToWriteLabel;
    /** Wyswietla rezultat */
    @FXML
    protected Label resultsLabel;
    /**  Wyswietla timer*/
    @FXML
    protected Label sekundnik;
    /**  Wyswietla aktualny poziom  */
    @FXML
    protected Label levelDisplay;
    /**  Przycisk do pominiecia poziomu i przejscia do nastepnego */
    @FXML
    protected Button skipLevelButton;
    /**  Przycisk do wczytania tekstu */
    @FXML
    protected Button setTekstButton;
    /** Przejscie do czesci testowej po ukonczeniu cwiczen */
    @FXML
    protected Button switchToTestFromPracticeButton;
    /** Informacje  */
    @FXML
    protected Label info1;
    /** Informacje  */
    @FXML
    protected Label info2;
    /** Informacje  */
    @FXML
    protected Label info3;
    /** Wyswietla wyniki */
    @FXML
    protected Label scoresLabel;

    /** Licznik slow */
    protected int wordCounter = 0;
    /** Licznik liter */
    protected int letterCounter = 0;
    /** Slowa po korekcji - wstawieniu separatora */
    String[] newContent;
    /** Aktualna litera do wpisania z danego slowa */
    char[] currentLetterWord;
    /** Licznik poprawnie wpisanych slow */
    protected int goodWords = 0;
    /** Licznik blednie wpisanych slow */
    protected int errorWords = 0;
    /** Czy wcisnieto backspace */
    protected boolean isBackspaceKey = false;
    /** Przeksztalcenie zawartosci newContent na Text aby zmieniac kolor slow */
    protected Text[] t;
    /** dodaje znak nowej linii */
    protected Text[] separator;
    /** Czy popelniono blad? */
    protected boolean isMistake = false;
    /** Licznik bledow  */
    protected int mistakeCounter = 0;
    /** Czy skonczono wprowadzac jedna linie? */
    protected boolean isFinished = false;
    /** Ilosc slow wczytanych z pliku */
    protected int iloscSlowWczytanych = 0;
    /** Czy rozpoczeto pisanie? */
    protected boolean isTimeStarted = false;
    /** Czas do timera */
    protected int time = 60;
    protected int minutes = (time % 3600) / 60;
    protected int seconds= time % 60;
    /** Poziom */
    protected int level = 1;
    /** Tablica losowych liczb dzieki ktorej wczytywane sa losowe slowa */
    public int[] liczby;
    /** Pola przechowujace czas ropoczecia i koniec na potrzeby cwiczen */
    protected long start,end = 0;
    /** Czas poswiecony na jeden poziom */
    protected long timePassed = 0;
    /** Pole przechowujace obliczone WPM */
    protected int wpm;
    /** Pole przechowujace liczbe prawidlowo wpisanych liter - potrzebne do obliczenia WPM */
    protected int goodLettersWpm;
    /** Czy uzytkownik znajduje sie w oknie test czy practice? */
    public boolean testOrPractice;

    /**
     * Metoda umozliwiajaca poruszanie oknem
     * @param view2 wczytywana scena
     * @param event ruch myszka
     */
    public void movableScene(Parent view2, ActionEvent event){
        //poruszanie oknem
        view2.setOnMousePressed(event2 -> {
            xOffset = event2.getSceneX();
            yOffset = event2.getSceneY();
        });
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        view2.setOnMouseDragged(event2 -> {
            window.setX(event2.getScreenX() - xOffset);
            window.setY(event2.getScreenY() - yOffset);
        });
        ///

        Scene scene2 = new Scene(view2);
        window.setScene(scene2);
        window.show();
    }

}
