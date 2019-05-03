package app.ihm.controllers;

import app.ihm.models.ViewModel;
import app.workers.Worker;
import app.workers.WorkerItf;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    @FXML
    public Slider sliderTime;
    @FXML
    public Label lblTime;
    @FXML
    public Label lblCurrentTime;
    @FXML
    public Button fullscreenBtn;
    @FXML
    public Slider sliderVolume;
    @FXML
    public Button playBtn;
    private WorkerItf wrk;
    private ViewModel model;
    private MediaPlayer mediaPlayer;
    private FileChooser fileChooser;
    private boolean isBeingPlayed;
    private Duration duration;
    private Image imgPlay;
    private Image imgPause;
    private ImageView imgViewPlay;
    private ImageView imgViewPause;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wrk = new Worker();
        intiTestVideo();
        initFileChooser();
        setListenerMediaPlayer();
        updateLblTotalDuration();
        imgPlay = new Image(getClass().getResourceAsStream("play-solid.png"));
        imgViewPlay = new ImageView(imgPlay);
        imgViewPlay.setFitHeight(25);
        imgViewPlay.setFitWidth(25);
        imgPause = new Image(getClass().getResourceAsStream("pause-solid.png"));
        imgViewPause = new ImageView(imgPause);
        imgViewPause.setFitHeight(25);
        imgViewPause.setFitWidth(25);
        playBtn.setGraphic(imgViewPause);

        //Gestion du slider média
        sliderTime.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (sliderTime.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    if(duration!=null) {
                        mediaPlayer.seek(duration.multiply(sliderTime.getValue() / 100.0));
                    }
                    updateValues();
                }
            }
        });

        //Gestion du slider volume
        sliderVolume.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (sliderVolume.isValueChanging()) {
                    mediaPlayer.setVolume(sliderVolume.getValue() / 100.0);
                }
            }
        });

        //Possibilité de cliquer sur le slider pour avancer/reculer
        sliderTime.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sliderTime.setValueChanging(true);
                double value = (event.getX()/sliderTime.getWidth())*sliderTime.getMax();
                sliderTime.setValue(value);
                sliderTime.setValueChanging(false);
            }
        });
    }

    private void initFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("FileChooserExample");
        File homeDir = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(homeDir);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Video Files", "*.mp4"));
    }

    protected void updateValues(){
        Platform.runLater(new Runnable() {
            public void run() {
                Duration currentTime = mediaPlayer.getCurrentTime();
                String txt = String.format("%.2f",currentTime.toSeconds());
                lblCurrentTime.setText(txt);
                sliderTime.setDisable(currentTime.isUnknown());
                if (!sliderTime.isDisabled() && duration.greaterThan(Duration.ZERO) && !sliderTime.isValueChanging()) {
                    sliderTime.setValue(currentTime.divide(duration.toMillis()).toMillis() * 100.0);
                }
                if (!sliderVolume.isValueChanging()) {
                    sliderVolume.setValue((int) Math.round(mediaPlayer.getVolume() * 100));
                }
            }
        });

    }

    private void updateLblTotalDuration(){
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                duration = mediaPlayer.getTotalDuration();
                Double a = duration.toSeconds();
                String txt = String.format("%.2f",a);
                lblTime.setText("/"+txt);
            }
        });
    }

    private void intiTestVideo() {

        try {
            isBeingPlayed = true;
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

    public void setFullscreen(){
        stage.setFullScreen(true);
    }

    public void resume(){
        Duration tempActuel = mediaPlayer.getCurrentTime();
        Duration tempTotal = mediaPlayer.getTotalDuration();
        System.out.println();
        if (isBeingPlayed && !tempActuel.equals(tempTotal)){
            mediaPlayer.pause();
            isBeingPlayed = false;
        }
        else if (tempActuel.equals(tempTotal)){
            mediaPlayer.seek(new Duration(0));
        }
        else {
            mediaPlayer.play();
            isBeingPlayed = true;
        }
    }

    public void openFile(ActionEvent actionEvent) {
        mediaPlayer.pause();
        isBeingPlayed = false;
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                //---Open the file with associated application
                mediaPlayer.stop();
                Media currentMedia = new Media(selectedFile.toURI().toString());
                mediaPlayer = new MediaPlayer(currentMedia);
                mediaView.setMediaPlayer(mediaPlayer);
                isBeingPlayed = true;
                mediaPlayer.play();
                setListenerMediaPlayer();
                updateLblTotalDuration();
            } catch (Exception e) {
                System.err.println("ERROR: Unable to open the file");
            }
        }

    }

    private void setListenerMediaPlayer() {
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                updateValues();
            }
        });
    }
}

