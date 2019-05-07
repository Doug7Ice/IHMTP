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
 *
 * @author anthonyc.alonsolo
 * @version 1.0.0
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
     * This method
     *
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


    /**
     * this method return an arraylist of annotation red from a json file in src\ressources\[VideoName].json
     *
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
