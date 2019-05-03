package app.ihm.controllers;

import app.ihm.models.ViewModel;
import app.workers.Worker;
import app.workers.WorkerItf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Anthony Alonso Lopez
 */
public class Controller implements Initializable {


    public Stage stage;
    @FXML
    public MenuBar menuVLFX;
    @FXML
    public BorderPane borderPane;
    @FXML
    public MediaView mediaView;
    @FXML
    public MenuItem openMenu;
    @FXML
    public MenuItem quitMenu;
    @FXML
    public MenuItem saveMenu;
    private WorkerItf wrk;
    private ViewModel model;
    private MediaPlayer mediaPlayer;
    private FileChooser fileChooser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wrk = new Worker();

        intiTestVideo();

        initFileChooser();

    }

    private void initFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("FileChooserExample");
        File homeDir = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(homeDir);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Video Files", "*.mp4"));
    }

    private void intiTestVideo() {

        try {
            File video = new File("src/ressources/test.mp4").getCanonicalFile();
            URI a = video.toURI();
            String b = a.toString();
            System.out.println(b);
            Media currentMedia = new Media(video.toURI().toString());
            mediaPlayer = new MediaPlayer(currentMedia);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void quitter() {
        System.out.println("app is closing !");
        System.exit(0);
    }

    public void save(){
        System.out.println("Heu... Hehe c'est embarrassant.........");
    }

    public void openFile(ActionEvent actionEvent) {
        mediaPlayer.pause();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                //---Open the file with associated application
                mediaPlayer.stop();
                Media currentMedia = new Media(selectedFile.toURI().toString());
                mediaPlayer = new MediaPlayer(currentMedia);
                mediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("ERROR: Unable to open the file");
            }
        }

    }
}

