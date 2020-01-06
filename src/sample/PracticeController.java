package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * Klasa zawierajaca metody sterowania czescia cwiczeniowa
 */
public class PracticeController extends GameControls {

    /**
     * Metoda przekazujaca wcisniety klawisz do sprawdzenia. Jezeli poczatek rozgrywki to uruchamia timer.
     * @param e wcisniety klawisz
     */
    @FXML
    public void keyPressed(KeyEvent e){

        if(!isTimeStarted){
            start = System.nanoTime();
            isTimeStarted = true;
        }
        if(!isFinished) {
            typingCheck(e);
        }
    }
    /**
     * Przygotowywanie rozgrywki. Losowanie indeksow dla slow, wczytywanie pliku w zaleznosci od poziomu, ustawienie wyswietlania informacji o rozgrywce.
     * @throws IOException blad pliku
     */
    public void setTeksto() throws IOException {
        testOrPractice = true;
        sekundnik.setVisible(false);
        if(isFinished){
            resultsLabel.setVisible(false);
        }
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
        //////
        if(level <= 20) {
            String nazwapliczku = "src/sample/text_files/"+level+".txt";
            loadTekst(nazwapliczku);
            levelDisplay.setText("Lekcja " +level+ ".");
            textInputField.setVisible(true);
        }else {
            resetAll();
            textToWriteLabel.getChildren().clear();
            textInputField.setVisible(false);
            skipLevelButton.setVisible(false);
            setTekstButton.setVisible(false);
            resultsLabel.setVisible(true);
            resultsLabel.setAlignment(Pos.CENTER);
            switchToTestFromPracticeButton.setVisible(true);
            resultsLabel.setText("Przejdź do testu aby zmierzyć WPM.");
            levelDisplay.setMinSize(511,70);
            levelDisplay.setLayoutX(310);
            levelDisplay.setLayoutY(14);
            levelDisplay.setAlignment(Pos.CENTER);
            levelDisplay.setText("Ukończono ćwiczenia!");
            //System.out.println("Koniec gry!");
        }
        info1.setVisible(false);
        info2.setVisible(false);
        isFinished = false;
    }

    /**
     * Metoda umozliwiajaca pominiecie danego poziomu po niepowodzeniu i przejscie do nastepnego.
     * @throws IOException blad pliku
     */
    public void skipLevel() throws IOException {
        level++;
        setTeksto();
        skipLevelButton.setVisible(false);
    }
}