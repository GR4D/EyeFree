package sample;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;

/**
 * Klasa zawierajace metody sterownia rozgrywka w oknie Test
 */
public class TestController extends GameControls{

    /**
     * Metoda przekazujaca wcisniety klawisz do sprawdzenia. Jezeli poczatek rozgrywki do uruchamia timer.
     * @param e wcisniety klawisz
     */
    @FXML
    public void keyPressed(KeyEvent e) {

        if(!isTimeStarted){
            timer.schedule(task1,1000,1000);
            isTimeStarted = true;
        }
        if(!isFinished) {
            typingCheck(e);
        }
    }

    /**
     * Przygotowywanie rozgrywki. Losowanie indeksow dla slow, wczytywanie pliku, ustawienie wyswietlania informacji o rozgrywce. Kasowanie ewentualnego timera i tworzenie nowych obiektow.
     * @throws IOException blad pliku
     */
    public void setTeksto() throws IOException {
        testOrPractice = false;
        if(isFinished){
            resultsLabel.setVisible(false);
        }
        resetAll();
        ///losowanie liczb
        Random randNum = new Random();
        Set<Integer> set = new LinkedHashSet<Integer>();

        int index = 0;
        while (set.size() < 302) {
            set.add(randNum.nextInt(302));
        }
        liczby = new int[set.size()];
        for(Integer i : set){
            liczby[index++] = i;
        }
        /////////
        loadTekst("src/sample/slowaPL.txt");
        isFinished = false;
        sekundnik.setVisible(true);
        info3.setVisible(false);
        timer.cancel();
        timer.purge();
        task1 = new TimerTest();
        timer = new Timer();
    }

}