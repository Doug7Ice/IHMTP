package app.workers;

import app.beans.Annotation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class is the worker.
 * @version 1.0.0
 * @author anthonyc.alonsolo
 */
public class Worker implements WorkerItf {
    private static final String jsonLocation = "src\\ressources\\";
    private final GsonBuilder builder;
    private final Gson gson;
    private IoMaster io;

    /**
     * Build the worker, Init Gson.
     */
    public Worker() {
        this.io = new IoMaster();
        builder = new GsonBuilder();
        gson = builder.create();
    }

    /**
     *  This method
     * @param newAnnotation
     * @return true if write correctly, false if not
     */
    public boolean newAnnotation(Annotation newAnnotation) {
        ArrayList<Annotation> listOfAnnotation = readAnnotation(newAnnotation.getVideoName());
        listOfAnnotation.add(newAnnotation);
        String json = gson.toJson(listOfAnnotation);
        System.out.println(json);
        return io.ecrireFichier(new ArrayList<String>() {{
            add(json);
        }}, jsonLocation, newAnnotation.getVideoName() + ".json");
    }

    public void notification(){
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("src\\ressources\\images\\icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        try {
            tray.add(trayIcon);
        }catch (AWTException e){
            System.out.println(e);
        }


        trayIcon.displayMessage("Groupe 3 Annotation", "notification demo", TrayIcon.MessageType.INFO);
    }
    public void notif(Stage ownerStage,Annotation annotation){
        Stage toastStage=new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(annotation.getText());
        text.setFont(Font.font("Verdana", 40));
        text.setFill(Color.RED);

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-padding: 50px;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(annotation.getDuration()), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey1);
        fadeInTimeline.setOnFinished((ae) ->
        {
            new Thread(() -> {
                try
                {
                    Thread.sleep(annotation.getDuration());
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Timeline fadeOutTimeline = new Timeline();
                KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(annotation.getDuration()), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 0));
                fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
                fadeOutTimeline.play();
            }).start();
        });
        fadeInTimeline.play();
    }

    /**
     * this method return an arraylist of annotation red from a json file in src\ressources\[VideoName].json
     * @param videoName that you can get from Annotation.getVideoName()
     * @return an Arraylist of Annotation (can be empty)
     */
    public ArrayList<Annotation> readAnnotation(String videoName) {
        ArrayList<String> list = io.lireFichier(jsonLocation, videoName + ".json");
        if (list == null) return new ArrayList<>();
        return gson.fromJson(list.get(0), new TypeToken<ArrayList<Annotation>>() {
        }.getType());
    }


}
