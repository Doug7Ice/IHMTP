package app.ihm.controllers;

import app.beans.Annotation;
import app.beans.VideoBean;
import app.helpers.ActiveDirectory;
import app.helpers.ViewLib;
import app.ihm.models.ViewModel;
import app.workers.Worker;
import app.workers.WorkerItf;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Anthony Alonso Lopez & Léo Doug Rey
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
    public MenuItem menuAnnotation;
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
    @FXML
    public ListView<Annotation> listViewAnnotations;
    private WorkerItf wrk;
    private ViewModel model;
    private MediaPlayer mediaPlayer;
    private FileChooser fileChooser;
    private boolean isBeingPlayed;
    private Duration duration;
    private ControllerAnnotations controllerAnnotations;
    private Stage stagePopup;
    private VideoBean videoBean;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wrk = new Worker();
        intiTestVideo();
        initFileChooser();
        setListenerMediaPlayer();
        updateLblTotalDuration();

        playBtn.setStyle("-fx-background-image: url(ressources/images/pause-solid.png);-fx-background-repeat: no-repeat;-fx-background-size: contain; -fx-background-position : center center;");

        //Gestion du slider média
        sliderTime.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (sliderTime.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    if (duration != null) {
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
        sliderTime.setOnMouseClicked(event -> {
                    sliderTime.setValueChanging(true);
                    double value = (event.getX() / sliderTime.getWidth()) * sliderTime.getMax();
                    sliderTime.setValue(value);
                    sliderTime.setValueChanging(false);
                }
        );


        //Popup pour l'ajout d'annotations
        menuAnnotation.setOnAction(event -> {
            try {
                if (controllerAnnotations == null) {
                    controllerAnnotations = new ControllerAnnotations();
                    controllerAnnotations.controller = this;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/popupAnnotations.fxml"));
                    loader.setController(controllerAnnotations);
                    Scene scene = new Scene(loader.load(), 600, 400);
                    stagePopup = new Stage();
                    stagePopup.setTitle("Add your Annotations !");
                    stagePopup.setScene(scene);
                    controllerAnnotations.stage = stagePopup;
                }
                controllerAnnotations.txtTimeCode.setText(lblCurrentTime.getText());
                stagePopup.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void initFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("FileChooserExample");
        File homeDir = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(homeDir);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("VideoBean Files", "*.mp4"));
    }

    protected void updateValues() {

        Platform.runLater(() -> {


            Duration currentTime = mediaPlayer.getCurrentTime();
            String txt = String.format("%.2f", currentTime.toSeconds());
            lblCurrentTime.setText(txt);
            sliderTime.setDisable(currentTime.isUnknown());
            if (!sliderTime.isDisabled() && duration.greaterThan(Duration.ZERO) && !sliderTime.isValueChanging()) {
                sliderTime.setValue(currentTime.divide(duration.toMillis()).toMillis() * 100.0);
                int indexAnnotationRunning = 0;
                for (Annotation annotation : videoBean.getListAnnotations()) {
                    indexAnnotationRunning++;
                    double delta = currentTime.toSeconds() - annotation.getTimestampMillis();
                    if (delta < 0.5 && delta > -0.5) {
                        ViewLib.toast(stage, annotation.getText(), annotation.getDuration());
                    }
                }
            }
            if (!sliderVolume.isValueChanging()) {
                sliderVolume.setValue((int) Math.round(mediaPlayer.getVolume() * 100));
            }
        });
    }

    private void changeStyleButtonPlayPause() {
        if (isBeingPlayed) {
            playBtn.setStyle("-fx-background-image: url(ressources/images/pause-solid.png);-fx-background-repeat: no-repeat;-fx-background-size: contain; -fx-background-position : center center;");
        } else {
            playBtn.setStyle("-fx-background-image: url(ressources/images/play-solid.png);-fx-background-repeat: no-repeat;-fx-background-size: contain; -fx-background-position : center center;");
        }

    }

    private void updateLblTotalDuration() {
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                duration = mediaPlayer.getTotalDuration();
                Double a = duration.toSeconds();
                String txt = String.format("%.2f", a);
                lblTime.setText("/" + txt);
                videoBean.setLenght(a);
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
            videoBean = new VideoBean("test.mp4", wrk.readAnnotation("test.mp4"));
            updateListView();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private void updateListView() {
        listViewAnnotations.getItems().clear();
        if(videoBean!= null && videoBean.getListAnnotations()!= null){
            listViewAnnotations.getItems().addAll(videoBean.getListAnnotations());
        }

    }

    public void removeAnnotation(){
        wrk.eraseAnnotation(listViewAnnotations.getSelectionModel().getSelectedItem().getVideoName());
        ObservableList<Annotation> arrayAnnotationTemp = listViewAnnotations.getItems();
        ArrayList<Annotation> arrayListAnnotation = new ArrayList<>();
        arrayListAnnotation.addAll(arrayAnnotationTemp);
        arrayListAnnotation.remove(listViewAnnotations.getSelectionModel().getSelectedItem());
        wrk.writeAnnotations((arrayListAnnotation));
        updateListView();
    }

    public void quitter() {
        System.out.println("app is closing !");
        if (controllerAnnotations != null) {
            controllerAnnotations.stage.close();
        }
        stage.close();
    }

    public void save() {
        System.out.println("Heu... Hehe c'est embarrassant.........");
    }

    public void setFullscreen() {
        stage.setFullScreen(true);
    }

    public void resume() {
        Duration tempActuel = mediaPlayer.getCurrentTime();
        Duration tempTotal = mediaPlayer.getTotalDuration();
        System.out.println();
        if (isBeingPlayed && !tempActuel.equals(tempTotal)) {
            mediaPlayer.pause();
            isBeingPlayed = false;
            changeStyleButtonPlayPause();
        } else if (tempActuel.equals(tempTotal)) {
            mediaPlayer.seek(new Duration(0));
        } else {
            mediaPlayer.play();
            isBeingPlayed = true;
            changeStyleButtonPlayPause();
        }
    }

    public void openFile(ActionEvent actionEvent) {
        mediaPlayer.pause();
        isBeingPlayed = false;
        openFile(fileChooser.showOpenDialog(stage));
    }

    public void openFile(File selectedFile) {
        if (selectedFile != null) {
            try {
                mediaPlayer.stop();
                Media currentMedia = new Media(selectedFile.toURI().toString());
                mediaPlayer = new MediaPlayer(currentMedia);
                mediaView.setMediaPlayer(mediaPlayer);
                isBeingPlayed = true;
                mediaPlayer.play();
                videoBean = new VideoBean(selectedFile.getName(), wrk.readAnnotation(selectedFile.getName()));
                updateListView();
                setListenerMediaPlayer();
                updateLblTotalDuration();
            } catch (Exception e) {
                System.err.println("ERROR: Unable to open the file");
            }
        }
    }
    public void openURL(String selectedFile) {
        if (selectedFile != null) {
            try {
                mediaPlayer.stop();
                System.out.println(selectedFile);
                Media currentMedia = new Media(selectedFile);
                mediaPlayer = new MediaPlayer(currentMedia);
                mediaView.setMediaPlayer(mediaPlayer);
                isBeingPlayed = true;
                mediaPlayer.play();
                videoBean = new VideoBean(selectedFile, wrk.readAnnotation(selectedFile));
                updateListView();
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

    public boolean newAnnotation(Annotation a) {
        boolean ok = wrk.newAnnotation(a);
        if (ok) {
            ViewLib.WindowsNotification("Annotation ajoutée !", "Vidéo : " + a.getVideoName() + " texte : " + a.getText(), TrayIcon.MessageType.INFO, null);
            listViewAnnotations.getItems().clear();
            listViewAnnotations.getItems().addAll(wrk.readAnnotation(videoBean.getTitle()));
        }
        return ok;
    }

    @FXML
    private void dragAndDrop(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            openFile(db.getFiles().get(0));
            success = true;
        }else if (db.hasString()){
            openURL(db.getString());
            success = true;
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    @FXML
    private void PrepareDragAndDrop(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }else if (db.hasString()){
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
        dragEvent.consume();
    }

    public VideoBean getVideoBean() {
        return videoBean;
    }
}

