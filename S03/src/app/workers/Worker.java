package app.workers;

import app.beans.Annotation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
     * This method write a new annotation to json's video
     *
     * @param newAnnotation
     * @return true if write correctly, false if not
     */
    public boolean newAnnotation(Annotation newAnnotation) {
        ArrayList<Annotation> listOfAnnotation = readAnnotation(newAnnotation.getVideoName());
        if (listOfAnnotation == null) {
            listOfAnnotation = new ArrayList<>();
        }
        listOfAnnotation.add(newAnnotation);
        return writeAnnotations(listOfAnnotation);
    }


    /**
     * this method return an arraylist of annotation red from a json file in src\ressources\[VideoName].json
     *
     * @param videoName that you can get from Annotation.getVideoName()
     * @return an Arraylist of Annotation (can be empty)
     */
    public ArrayList<Annotation> readAnnotation(String videoName) {
        ArrayList<String> list = io.lireFichier(jsonLocation, videoName + ".json");
        if (list == null || list.isEmpty() ) return new ArrayList<>();
        return gson.fromJson(list.get(0), new TypeToken<ArrayList<Annotation>>() {
        }.getType());
    }

    /**
     * This method erase Annotations from the json video
     *
     * @param videoName the video name
     */
    public void eraseAnnotation(String videoName) {
        System.out.println(videoName);
        io.ecrireFichier(new ArrayList<String>() {{
            add("");
        }}, jsonLocation, videoName + ".json");
    }

    @Override
    public boolean checkLogin(String email) {
        return email.equals("leodoug.rey@edu.hefr.ch");
    }

    /**
     * This method write an ArrayList of Annotation to json (videoname.json)
     *
     * @param list your arrayList of annotation
     * @return true if write correctly
     */
    public boolean writeAnnotations(ArrayList<Annotation> list) {
        if (list != null && !list.isEmpty() || list.get(0).getText() != null) {
            String json = gson.toJson(list);
            System.out.println(json);
            return io.ecrireFichier(new ArrayList<String>() {{
                add(json);
            }}, jsonLocation, list.get(0).getVideoName() + ".json");
        }else{

            return io.ecrireFichier(new ArrayList<String>() {{
                add("");
            }}, jsonLocation, list.get(0).getVideoName() + ".json");
        }
    }

}
