package app.workers;

import app.beans.Annotation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class Worker implements WorkerItf {
    private static final String jsonLocation = "src\\ressources\\";
    private final GsonBuilder builder;
    private final Gson gson;
    private IoMaster io;

    public Worker() {
        this.io = new IoMaster();
        builder = new GsonBuilder();
        gson = builder.create();
    }

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
     * @param videoName
     * @return an Arraylist of Annotation
     */
    public ArrayList<Annotation> readAnnotation(String videoName) {
        ArrayList<String> list = io.lireFichier(jsonLocation, videoName + ".json");
        if (list == null) return new ArrayList<>();
        return gson.fromJson(list.get(0), new TypeToken<ArrayList<Annotation>>() {
        }.getType());
    }


}
