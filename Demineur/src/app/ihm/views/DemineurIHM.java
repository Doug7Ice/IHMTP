package app.ihm.views;

import app.helpers.Vibrate;
import app.helpers.ViewLib;
import app.ihm.controllers.Controller;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

/**
 * @author Leo Doug Rey
 */
public class DemineurIHM extends Application {

    private static GridPane gridButtons = new GridPane();
    private static TextField txtNbErrors = new TextField();
    private static TextField txtNbClicks = new TextField();

    //Stages
    private Stage popupWindowSettings;
    //Composants jeu
    private BorderPane root = new BorderPane();
    private Label lblTitle = new Label("~ MineHunt ~");
    private Label lblNbErrors = new Label("Nb Errors : ");
    private Label lblNbClicks = new Label("Nb Clicks : ");
    private VBox vboxCenter = new VBox();
    private HBox hboxHaut = new HBox();
    private VBox vboxTopMenuLabel = new VBox();
    private HBox hboxErrors = new HBox();
    private HBox hboxClicks = new HBox();
    private HBox hboxBas = new HBox();
    private Button buttonShMines = new Button("Show Mines");
    private Button buttonNewGames = new Button("New Game");
    //Menus
    private MenuBar menuBar = new MenuBar();
    private Menu menuJeux = new Menu("Jeux");
    private MenuItem menuPref = new MenuItem("Préférences");
    private Menu menuLook = new Menu("Look and Feel");
    private Menu menuTheme = new Menu("Theme");
    private Menu menuTaille = new Menu("Taille");
    private Menu menuAbout = new Menu("About...");
    private MenuItem menuCopyright = new MenuItem("Copyright");

    //Menu Radio
    private ToggleGroup toggleGroupLook = new ToggleGroup();
    private RadioMenuItem menuRadioCaspianTheme = new RadioMenuItem("Caspian");
    private RadioMenuItem menuRadioModenaTheme = new RadioMenuItem("Modena");
    private RadioMenuItem menuRadioAquaFX = new RadioMenuItem("AquaFX");

    //Popup settings du jeu (menus)
    private TextField txtNbMine = new TextField("10");
    private Label lblNbMine = new Label("Nombres de mines");
    private TextField txtNbCasesX = new TextField("10");
    private Label lblNbCasesX = new Label("Nombres de cases (hauteur)");
    private TextField txtNbCasesY = new TextField("10");
    private Label lblNbCasesY = new Label("Nombres de cases (largeur)");
    private Button btnQuitPopupSettings = new Button("Sauver et fermer");
    private VBox vboxParam = new VBox();
    private HBox hboxNbMines = new HBox();
    private HBox hboxNbX = new HBox();
    private HBox hboxNbY = new HBox();
    private Slider sliderVolume;

    private Controller ctrl;
    private Stage primaryStage;

    private File boom = new File("src/ressources/sounds/explosion.mp3");

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        primaryStage.setResizable(true);
        ctrl = new Controller();
        buttonShMines.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> ctrl.btnShowMinesHit());
        buttonNewGames.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> ctrl.initialize(this));
        txtNbClicks.setEditable(false);
        txtNbErrors.setEditable(false);
        hboxErrors.getChildren().addAll(lblNbErrors, txtNbErrors);
        hboxClicks.getChildren().addAll(lblNbClicks, txtNbClicks);
        hboxHaut.getChildren().addAll(hboxErrors, hboxClicks);
        hboxBas.getChildren().addAll(buttonShMines, buttonNewGames);

        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 37));

        root.setAlignment(lblTitle, Pos.CENTER);
        root.setMargin(lblTitle, new Insets(10, 0, 10, 0));
        vboxTopMenuLabel.getChildren().addAll(menuBar, lblTitle);
        vboxTopMenuLabel.setAlignment(Pos.CENTER);

        root.setAlignment(hboxHaut, Pos.TOP_CENTER);
        hboxHaut.setAlignment(Pos.TOP_CENTER);
        hboxHaut.setSpacing(50);
        hboxClicks.setAlignment(Pos.TOP_LEFT);
        hboxErrors.setAlignment(Pos.TOP_LEFT);

        hboxBas.setSpacing(50);
        hboxBas.setAlignment(Pos.BOTTOM_RIGHT);
        setUserAgentStylesheet(STYLESHEET_MODENA);
        root.setMargin(hboxBas, new Insets(0, 50, 10, 0));

        //Menu add RadioMenu for the look
        menuRadioCaspianTheme.setSelected(false);
        menuRadioCaspianTheme.setOnAction((ActionEvent e) -> setUserAgentStylesheet(STYLESHEET_CASPIAN));
        menuRadioCaspianTheme.setToggleGroup(toggleGroupLook);

        menuRadioModenaTheme.setSelected(true);
        menuRadioModenaTheme.setOnAction((ActionEvent e) -> setUserAgentStylesheet(STYLESHEET_MODENA));
        menuRadioModenaTheme.setToggleGroup(toggleGroupLook);

        menuRadioAquaFX.setSelected(false);
        menuRadioAquaFX.setToggleGroup(toggleGroupLook);

        menuTheme.getItems().addAll(menuRadioModenaTheme, menuRadioCaspianTheme,menuRadioAquaFX);

        //MenuTaille add Slider
        sliderVolume = new Slider(0, 100, 30);
        CustomMenuItem cmiSlider = new CustomMenuItem();
        cmiSlider.setContent(sliderVolume);
        cmiSlider.setHideOnClick(false);
        menuTaille.getItems().add(cmiSlider);

        //

        // Add menuItems to the Menus
        menuJeux.getItems().addAll(menuPref);
        menuPref.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        menuLook.getItems().addAll(menuTheme, menuTaille);
        menuAbout.getItems().addAll(menuCopyright);
        Image menuIcon = new Image(getClass().getResourceAsStream("/ressources/images/logo.png"));
        ImageView menuIconV = new ImageView(menuIcon);
        menuIconV.setFitWidth(15);
        menuIconV.setFitHeight(15);
        menuJeux.setGraphic(menuIconV);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(menuJeux, menuLook, menuAbout);

        //Add Listener to Menus
        menuCopyright.addEventHandler(ActionEvent.ACTION, (ActionEvent event) ->
                ViewLib.displayPopup("Copyright", null, "Ce jeu à été développé par Anthony Alonso et Léo Doug Rey, 03.2019", Alert.AlertType.INFORMATION));
        menuPref.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showPopupGameSettings();
            }
        });

        //Add children on root
        root.setTop(vboxTopMenuLabel);

        //root.setCenter(hboxHaut);
        vboxCenter.setSpacing(10);
        vboxCenter.getChildren().addAll(hboxHaut, gridButtons);
        root.setCenter(vboxCenter);

        root.setBottom(hboxBas);

        Scene scene = new Scene(root, 500, 75 * getNumberCasesX());
        primaryStage.sizeToScene();

        contextMenuCreator(scene, primaryStage);

        primaryStage.setTitle(
                "MineHunt");
        primaryStage.getIcons()
                .add(new Image(this.getClass().getResourceAsStream("/ressources/images/logoWhite.png")));
        primaryStage.setScene(scene);

        root.requestFocus();

        primaryStage.setOnCloseRequest(e -> ctrl.quitCall(e));
        ctrl.initialize(this);
        primaryStage.show();
        System.out.println("GO");
    }

    private void contextMenuCreator(Scene scene, Stage primaryStage) {
        //ContextMenu for ColorPicker
        final ColorPicker colorssPicker = new ColorPicker();
        colorssPicker.setStyle("-fx-background-color: white;");

        final MenuItem otherItem = new MenuItem(null, new Label("Background Color"));

        final MenuItem resizeItem = new MenuItem(null, colorssPicker);
        resizeItem.setOnAction((ActionEvent event) ->
                root.setBackground(new Background(new BackgroundFill(colorssPicker.getValue(), null, null))));

        final ContextMenu ctxmenu = new ContextMenu(otherItem, resizeItem);

        scene.setOnMouseClicked((MouseEvent event) -> {
            if (MouseButton.SECONDARY.equals(event.getButton()))
                ctxmenu.show(primaryStage, event.getScreenX(), event.getScreenY());
        });
    }

    /**
     *
     */
    private void showPopupGameSettings() {
        if (popupWindowSettings == null) {
            popupWindowSettings = new Stage();
            popupWindowSettings.initModality(Modality.APPLICATION_MODAL);
            popupWindowSettings.setTitle("Paramètres de la partie");
            btnQuitPopupSettings.setOnAction(e -> {
                String x = txtNbCasesX.getCharacters().toString();
                String y = txtNbCasesY.getCharacters().toString();
                String m = txtNbMine.getCharacters().toString();
                if (x.equals("") || y.equals("") || m.equals("")) {
                    new Vibrate(txtNbCasesX, Orientation.HORIZONTAL).play();
                    new Vibrate(txtNbCasesY, Orientation.HORIZONTAL).play();
                    new Vibrate(txtNbMine, Orientation.HORIZONTAL).play();
                }
                ctrl.setGridGameSizeX(Integer.parseInt(x));
                ctrl.setGridGameSizeY(Integer.parseInt(y));
                ctrl.setMineQuantity(Integer.parseInt(m));
                ctrl.initialize(this);
                popupWindowSettings.close();
            });
            BorderPane layout = new BorderPane();

            txtNbMine.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                                                  String newValue) -> {
                if (!newValue.matches("30|[12][0-9]|[1-9]")) {
                    new Vibrate(txtNbMine, Orientation.HORIZONTAL).play();
                    txtNbMine.clear();
                }
            });
            hboxNbMines.getChildren().addAll(lblNbMine, txtNbMine);
            hboxNbMines.setPadding(new Insets(20));
            hboxNbMines.setAlignment(Pos.CENTER_RIGHT);
            hboxNbMines.setSpacing(10);
            txtNbCasesX.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                                                    String newValue) -> {
                if (!newValue.matches("[1-9]|10")) {
                    new Vibrate(txtNbCasesX, Orientation.HORIZONTAL).play();
                    txtNbCasesX.clear();
                }
            });
            hboxNbX.getChildren().addAll(lblNbCasesX, txtNbCasesX);
            hboxNbX.setPadding(new Insets(20));
            hboxNbX.setAlignment(Pos.CENTER_RIGHT);
            hboxNbX.setSpacing(10);

            txtNbCasesY.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue,
                                                    String newValue) -> {
                if (!newValue.matches("[1-9]|10")) {
                    new Vibrate(txtNbCasesY, Orientation.HORIZONTAL).play();
                    txtNbCasesY.clear();
                }
            });
            hboxNbY.getChildren().addAll(lblNbCasesY, txtNbCasesY);
            hboxNbY.setPadding(new Insets(20));
            hboxNbY.setAlignment(Pos.CENTER_RIGHT);
            hboxNbY.setSpacing(10);

            vboxParam.getChildren().addAll(hboxNbMines, hboxNbX, hboxNbY);

            vboxParam.setAlignment(Pos.CENTER);

            layout.setCenter(vboxParam);
            layout.setBottom(btnQuitPopupSettings);
            layout.setAlignment(btnQuitPopupSettings, Pos.BOTTOM_CENTER);
            layout.setAlignment(vboxParam, Pos.TOP_CENTER);

            Scene scene1 = new Scene(layout, 400, 300);
            popupWindowSettings.getIcons()
                    .add(new Image(this.getClass().getResourceAsStream("/ressources/images/logoWhite.png")));
            popupWindowSettings.setScene(scene1);
        }
        popupWindowSettings.showAndWait();
    }

    public void clearGameGrid() {
        gridButtons.getChildren().clear();
    }

    private int getNumberCasesX() {
        return Integer.parseInt(txtNbCasesX.getText());
    }

    private int getNumberCasesY() {
        return Integer.parseInt(txtNbCasesY.getText());
    }

    public GridPane getGridButtons() {
        return gridButtons;
    }

    public void changeSizeWindows() {
        if (primaryStage != null && getNumberCasesX() > 4) {
            primaryStage.setWidth(500);
            primaryStage.setHeight(75 * getNumberCasesX());
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(75 * getNumberCasesX());
        } else {
            primaryStage.setWidth(500);
            primaryStage.setHeight(75 * 5);
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(75 * 5);
        }
    }

    public void setTxtNbErrors(String txt) {
        new Vibrate(root, Orientation.HORIZONTAL).play();
        txtNbErrors.setText(txt);
        AudioClip explode = new AudioClip(boom.toURI().toString());
        explode.setVolume(sliderVolume.getValue());
        explode.play();

        RotateTransition rotator = new RotateTransition(Duration.millis(1000), root);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(1);
        rotator.play();

    }

    public void setTxtNbClicks(String txt) {
        txtNbClicks.setText(txt);
    }
}
