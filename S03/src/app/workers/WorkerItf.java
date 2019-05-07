package app.workers;


import app.beans.Annotation;

import java.util.ArrayList;

/**
 * Cette interface définit les services "métier" de l'application.
 *
 * @author Anthony Alonso Lopez
 * @version 1.0 / date
 */
public interface WorkerItf {
    boolean newAnnotation(Annotation newAnnotation);
    ArrayList<Annotation> readAnnotation(String videoName);
}
