// Nom: 


package coffeeshoptp;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CoffeeShopView extends Application {

	private BorderPane root = new BorderPane();
	private GridPane grid = new GridPane();
	private Label titreLabel = new Label("Commande");

	private Label cafeLabel = new Label("Cafe");
	private Spinner<Integer> cafeSpinner = new Spinner<Integer>();

	private Label croissantLabel = new Label("Croissant");
	private Spinner<Integer> croissantSpinner = new Spinner<Integer>();

	private Label montantLabel = new Label("Montant à Payer");
	private TextField montantField = new TextField();

	private Label rabaisLabel = new Label("Rabais %");
	private ChoiceBox<Integer> rabaisChoiceBox = new ChoiceBox<Integer>();
	private ArrayList<Integer> rabaisOptions = new ArrayList<Integer>(Arrays.asList(0, 10, 25));
	private final int indexRabaisParDéfaut = 2;

	private CoffeeShopModel model;

	@Override
	public void init() {
		model = new CoffeeShopModel();

		SpinnerValueFactory<Integer> croissantFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 1);
		croissantFactory.setWrapAround(true);
		croissantSpinner.setValueFactory(croissantFactory);

		SpinnerValueFactory<Integer> cafeFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 1);
		cafeSpinner.setValueFactory(cafeFactory);

		rabaisChoiceBox.setItems(FXCollections.observableArrayList(rabaisOptions));
		rabaisChoiceBox.getSelectionModel().select(indexRabaisParDéfaut);

		model.cafeProperty().bind(cafeSpinner.valueProperty());
		model.croissantProperty().bind(croissantSpinner.valueProperty());
                montantField.textProperty().bind(model.montantProperty().asString());
                //
                model.rabaisProperty().bind(rabaisChoiceBox.valueProperty());
                
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("CoffeeShop");
		primaryStage.setResizable(false);

		root.setTop(titreLabel);
		root.setCenter(grid);

		grid.add(cafeLabel, 0, 1);
		grid.add(croissantLabel, 0, 2);
		grid.add(cafeSpinner, 1, 1);
		grid.add(croissantSpinner, 1, 2);
		grid.add(rabaisLabel, 0, 3);
		grid.add(rabaisChoiceBox, 1, 3);
		grid.add(montantLabel, 0, 4);
		grid.add(montantField, 1, 4);

		BorderPane.setMargin(titreLabel, new Insets(10, 0, 0, 0));
		BorderPane.setAlignment(titreLabel, Pos.CENTER);

		GridPane.setHalignment(croissantLabel, HPos.RIGHT);
		GridPane.setHalignment(rabaisLabel, HPos.RIGHT);
		GridPane.setHalignment(cafeLabel, HPos.RIGHT);
		grid.setVgap(10);
		grid.setHgap(5);
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(5));
		grid.setBorder(new Border(new BorderStroke(Color.BROWN, 
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				new BorderWidths(2), new Insets(20))));

		titreLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
		titreLabel.setTextFill(Color.BROWN);

		montantField.setMaxWidth(50);
		montantField.setEditable(false);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("CoffeeShop.css").toString());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}