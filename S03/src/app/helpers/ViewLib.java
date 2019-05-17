package app.helpers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;


/**
 * @author Anthony Alonso Lopez
 */
public class ViewLib {

    /**
     *  Display a popup with confirmation
     * @param titre your title
     * @param entete your header
     * @param message your message
     * @return Alert for caption action
     */
    public static Alert displayPopupConfirm(String titre, String entete, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titre);
        alert.setHeaderText(entete);
        alert.setContentText(message);
        return alert;
    }

    /**
     * This methods display a popup
     * @param titre your title
     * @param entete your header
     * @param message your message
     */
    public static void displayPopup(String titre, String entete, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(entete);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This methods create a windows notification
     *
     * @param title    the title of your notification
     * @param message  Your message
     * @param iconType TrayIcon.MessageType
     * @param menu     you can add a menu (PopupMenu) wich will be displayed by windows. You can link action.
     */
    public static void WindowsNotification(String title, String message, TrayIcon.MessageType iconType, PopupMenu menu) {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        SystemTray tray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        //NOT SUPPORTED YET
        Image image = toolkit.getImage("icon.png");

        TrayIcon trayIcon = new TrayIcon(image, title, menu);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println(e);
        }
        trayIcon.displayMessage(title, message, iconType);
    }


    /**
     * Display a toast notification.
     *
     * @param ownerStage
     * @param toastText
     * @param ms
     */
    public static void toast(Stage ownerStage, String toastText, double ms) {
        Stage toastStage = new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(toastText);
        text.setFont(Font.font("Verdana", 30));
        text.setFill(javafx.scene.paint.Color.RED);

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-padding: 10px;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        toastStage.setX(primScreenBounds.getWidth() / 2);
        toastStage.setY(primScreenBounds.getHeight() - primScreenBounds.getHeight() / 8);
        toastStage.show();


        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(1), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey1);
        try {
            fadeInTimeline.setOnFinished(ae -> {
                new Thread(() -> {
                    try {
                        Thread.sleep(((long) ms - 1000));
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Timeline fadeOutTimeline = new Timeline();
                    KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(1000), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
                    fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                    fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
                    fadeOutTimeline.play();
                    try {
                        toastStage.close();
                    }catch (IllegalStateException e){

                    }
                }).start();
            });
            fadeInTimeline.play();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }

    /**
     * WAFFLE - Windows Authentication Framework
     * @return TODO
     */
    public static boolean waffle() {
        //TODO https://tuhrig.de/a-windows-sso-for-java-on-client-and-server/
        return false;
    }
}