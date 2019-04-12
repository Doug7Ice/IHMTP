package app.helpers;

import javafx.scene.control.Alert;

/**
 * @author P-A Mettraux
 */
public class ViewLib {

    public static void displayPopup(String titre, String entete, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(entete);
        alert.setContentText(message);
        alert.showAndWait();
    }

}