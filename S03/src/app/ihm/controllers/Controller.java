package app.ihm.controllers;

import app.ihm.models.ViewModel;
import app.workers.Worker;
import app.workers.WorkerItf;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Anthony Alonso Lopez
 */
public class Controller implements Initializable {


    private WorkerItf wrk;
    private ViewModel model;
    private MediaPlayer mediaPlayer;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wrk = new Worker();

        intiTestVideo();
    }

    private void intiTestVideo() {
        Media currentMedia = new Media("C:\\Users\\leore\\Nextcloud\\NieR\\Automata\\NieR-Automata Édition Game of the YoRHA _ Bande-annonce de lancement_Full-HD_60fps.mp4");
        mediaPlayer =  new MediaPlayer(currentMedia);
        mediaPlayer.setAutoPlay(true);
        //wrk.loadVideo("");
    }

    public void quitter() {
        System.out.println("app is closing !");
        System.exit(0);
    }
}
