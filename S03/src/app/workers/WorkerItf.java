package app.workers;


/**
 * Cette interface définit les services "métier" de l'application.
 *
 * @author Anthony Alonso Lopez
 * @version 1.0 / date
 */
public interface WorkerItf {
    void loadVideo(String fileLocation);
    void writeAnnotation(double timestampMillis, String annotation, String videoName);
}
