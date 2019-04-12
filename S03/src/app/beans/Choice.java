package app.beans;

/**
 * Cette classe définit les services de la couche "métier" de l'application.
 *
 * @author Anthony Alonso Lopez
 * @version 1.0 / date
 */
enum Choice {
    X("X"), O("O"), N(" ");
    private String value;

    Choice(String value) {
        this.value = value;
    }
}
