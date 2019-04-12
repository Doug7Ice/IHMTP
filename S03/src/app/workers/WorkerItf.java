package app.workers;

import app.helpers.Choices;

/**
 * Cette interface définit les services "métier" de l'application.
 *
 * @author Anthony Alonso Lopez
 * @version 1.0 / date
 */
public interface WorkerItf {
    Choices[][] fill(Choices[][] game);
}
