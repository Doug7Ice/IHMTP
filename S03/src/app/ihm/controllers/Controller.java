package app.ihm.controllers;

import app.ihm.models.ViewModel;
import app.workers.Worker;
import app.workers.WorkerItf;
import com.sun.jndi.toolkit.url.Uri;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.xml.bind.annotation.XmlAnyAttribute;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Anthony Alonso Lopez
 */
public class Controller implements Initializable {


    private WorkerItf wrk;
    private ViewModel model;
    private MediaPlayer mediaPlayer;

    @FXML
    public MenuBar menuVLFX;
    @FXML
    public BorderPane borderPane;
    @FXML
    public MediaView mediaView;
    @FXML
    public MenuItem openMenu;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wrk = new Worker();

        //intiTestVideo();

    }

    private void intiTestVideo() {

        try {
            File video = new File("/Users/leore/Nextcloud/NieR/Automata/NieR-Automata Édition Game of the YoRHA _ Bande-annonce de lancement_Full-HD_60fps.mp4/").getCanonicalFile();
            URI a = video.toURI();
            String b = a.toString();
            System.out.println(b);
            Media currentMedia = new Media(video.toURI().toString());
            mediaPlayer =  new MediaPlayer(currentMedia);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }

        //wrk.loadVideo("");
    }

    public void quitter() {
        System.out.println("app is closing !");
        System.exit(0);
    }

    public void openFile(ActionEvent actionEvent) {
        mediaPlayer.pause();

    }
}
