/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buisinnes.card;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author leore
 */
public class BuisinnesCard extends Application {
    private Label lblClasse = new Label("Classe T1a");
    private Label lblGroupe = new Label("Membres groupe : Rey, Alonso");
    private BorderPane root = new BorderPane();
    private HBox hboxCenter = new HBox();
    
    @Override
    /**
     * Développer une application JavaFX générant une carte "business" avec le nom des
membres du groupe (en bleu, gras, et d’une taille de 20 pixels), et le nom de la classe en
dessous (en vert, et d’une taille de 19 pixels). Le fond d’écran des labels doit être
blancs. Entourer chaque label d’une bordure rouge. Séparés les deux labels d’un espace
de 15 pixels. Laisser un espace de 20 pixels entre les labels et leurs panes.
     */
    public void start(Stage primaryStage) {        
        root.setPadding(new Insets(20));
        lblGroupe.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));
        lblGroupe.setTextFill(Color.BLUE);
        lblGroupe.setBorder(new Border(new BorderStroke( Color .RED,
                BorderStrokeStyle.SOLID,
                new CornerRadii(5),
                new BorderWidths(1),
                new Insets(0))));
        lblGroupe.setBackground(new Background(new BackgroundFill(color(1, 1, 1), 
                CornerRadii.EMPTY, 
                Insets.EMPTY)));
        root.setTop(lblGroupe);
        
        lblClasse.setFont(Font.font("SansSerif", FontWeight.LIGHT, 19));
        lblClasse.setTextFill(Color.GREEN);
        lblClasse.setBackground(new Background(new BackgroundFill(color(1, 1, 1), 
                CornerRadii.EMPTY, 
                Insets.EMPTY)));
        lblClasse.setBorder(new Border(new BorderStroke( Color .RED,
                BorderStrokeStyle.SOLID,
                new CornerRadii(5),
                new BorderWidths(1),
                new Insets(0))));
        hboxCenter.setPadding(new Insets(15,0,15,0));
        hboxCenter.getChildren().addAll(lblClasse);
        
        root.setCenter(hboxCenter);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Buisiness Card");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
