package app.ihm.controllers;
import app.beans.Annotation;
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

        boolean ok = controller.newAnnotation(new Annotation(txtAreaAnnotations.getText(),
                Integer.parseInt(txtTimeScreen.getText()),
                Double.parseDouble(txtTimeCode.getText()),
                controller.getCurrentFileName()));

        //TODO GESTION ERREUR

        closePopup();

    }


}

