package app.workers;

import app.beans.Annotation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;


public class Worker implements WorkerItf {
    private final static String FILEPATH = "C:/Users/anthonyc.alonsolo/Documents/GitHub/IHMTP/src/";
    private final static double duration = 5000;
    private static boolean isFirstAnnotation = false;
    private final GsonBuilder builder;
    private final Gson gson;
    private IoMaster io;

    public Worker() {
        this.io = new IoMaster();
        builder = new GsonBuilder();
        gson = builder.create();
    }

    private static void parseAnnotationObject() {
    }


    public boolean newAnnotation(Annotation newAnnotation) {
        boolean ok = false;
        String json = gson.toJson(newAnnotation);
        System.out.println(json);
        io.ecrireFichier((ArrayList<String>) Arrays.asList(json), "C:\\Users\\anthonyc.alonsolo\\Documents\\GitHub\\IHMTP\\S03\\src\\ressources", newAnnotation.getVideoName());
        return ok;
    }

    public void writeAnnotation(double timestampMillis, String annotation, String videoName) {

    }

    /**
     * @param videoName
     * @return
     */
    private ArrayList<Annotation> readAnnotation(String videoName) {
        return null;
    }

    public void loadVideo(String fileLocation) {

    }
}
