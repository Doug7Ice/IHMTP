package app.helpers;

import javafx.scene.control.Alert;

/**
 * @author Anthony Alonso Lopez
 */
public class ViewLib {
    public static Alert displayPopupConfirm(String titre, String entete, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titre);
        alert.setHeaderText(entete);
        alert.setContentText(message);
        return alert;
    }

    public static void displayPopup(String titre, String entete, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(entete);
        alert.setContentText(message);
        alert.showAndWait();
    }

}