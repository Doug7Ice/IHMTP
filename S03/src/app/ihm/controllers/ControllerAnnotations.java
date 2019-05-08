package app.ihm.controllers;
import app.beans.Annotation;
import app.helpers.ViewLib;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Léo Doug Rey
 */
public class ControllerAnnotations implements Initializable {

    @FXML
    public Button btnCancel;
    @FXML
    public Button btnSave;
    @FXML
    public TextField txtTimeCode;
    @FXML
    public TextField txtTimeScreen;
    @FXML
    public TextArea txtAreaAnnotations;
    public Stage stage;
    public Controller controller;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void closePopup(){
        stage.close();
    }

    public  void saveAnnotations(){

        String stringTxtArea = txtAreaAnnotations.getText();
        String stringTimeScreen = txtTimeScreen.getText();
        String stringTimeCode = txtTimeCode.getText();

        if(!stringTxtArea.isEmpty() && !stringTimeScreen.isEmpty() && !stringTimeCode.isEmpty() && controller.getVideoBean().getLenght() >= Double.parseDouble(stringTimeCode)){
            boolean ok = controller.newAnnotation(new Annotation(stringTxtArea,
                    Integer.parseInt(stringTimeScreen),
                    Double.parseDouble(stringTimeCode),
                    controller.getVideoBean().getTitle()));
            closePopup();
        }
        else if (!stringTxtArea.isEmpty() && stringTimeScreen.isEmpty() && !stringTimeCode.isEmpty() && controller.getVideoBean().getLenght() >= Double.parseDouble(stringTimeCode)){
            boolean ok = controller.newAnnotation(new Annotation(stringTxtArea,
                    Double.parseDouble(stringTimeCode),
                    controller.getVideoBean().getTitle()));
            closePopup();
        }
        else {
            ViewLib.displayPopup("Error","Erreur lors de la sauvegarde",
                    "Le champ time on screen ne peut être vide ou la notatation est hors durée");
        }



    }


}

