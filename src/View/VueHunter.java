package View;

import Controller.ControlHunter;
import Controller.ControlHunterBot;
import Controller.ControlHunterPlayer;
import Model.Hunter;
import Utils.Coordinate;
import Utils.Maps;
import Utils.Observer;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * La classe VueHunter est une classe qui gère l'interface utilisateur pour le
 * chasseur dans le jeu. Elle affiche la grille de jeu, les informations
 * actuelles, et elle permet au joueur (ou à l'IA) de faire des mouvements.
 */
public class VueHunter extends View.AbstractView implements Observer {
    /** Le chasseur associé à cette vue. */
    private Hunter hunter;

    /** Le panneau de grille de jeu. */
    private GridPane gridPane;

    /** Le contrôleur associé à cette vue. */
    private ControlHunter controlleur;

    /** Le stage (fenêtre) associé à cette vue. */
    private Stage stage;

    /** Le label affichant les informations actuelles. */
    private Label currentLabel;

    private VBox mainBox;

    /**
     * Constructeur de la classe VueHunter.
     *
     * @param hunter  Le chasseur associé à la vue.
     * @param control Un booléen indiquant si le chasseur est contrôlé par un
     *                joueur ou par une IA.
     */
    public VueHunter(Hunter hunter, boolean control) {
        this.hunter = hunter;
        if (!control) {
            this.controlleur = new ControlHunterPlayer(this);
        } else {
            this.controlleur = new ControlHunterBot(this);
        }
        this.stage = creerStage();
        hunter.attach(this);
    }

    /**
     * Getter pour le panneau de grille de jeu.
     *
     * @return Le panneau de grille de jeu.
     */
    public GridPane getGridPane() {
        return gridPane;
    }

    /**
     * Getter pour le chasseur associé à la vue.
     *
     * @return Le chasseur associé à la vue.
     */
    public Hunter getHunter() {
        return hunter;
    }

    /**
     * Getter pour le label affichant les informations actuelles.
     *
     * @return Le label affichant les informations actuelles.
     */
    public Label getCurrentLabel() {
        return currentLabel;
    }

    /**
     * Setter pour le label affichant les informations actuelles.
     *
     * @param currentLabel Le nouveau label affichant les informations actuelles.
     */
    public void setCurrentLabel(Label currentLabel) {
        this.currentLabel = currentLabel;
    }

    public Scene getScene() {
        return stage.getScene();
    }

    /**
     * Crée et configure le stage (fenêtre) associé à cette vue.
     *
     * @return Le stage créé.
     */
    public Stage creerStage() {
        Stage stage = new Stage();
        gridPane = new GridPane();
        HBox hbox = new HBox(new Label("HUNTER"));
        hbox.setAlignment(Pos.CENTER);

        int imageSize = 50; // Taille de l'image ajustée à 50
        chargePlateau(-1, -1, imageSize);
        controlleur.hMouvement();
        styleGridPane(gridPane, imageSize);

        int rowCount = hunter.getGameModel().getMap().getRow() + 2;
        int colCount = hunter.getGameModel().getMap().getCol() + 2;
        VBox vboxCurrent = new VBox();
        currentLabel = new Label("C'est au tour du Monstre");
        vboxCurrent.getChildren().add(currentLabel);
        vboxCurrent.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(hbox, vboxCurrent, gridPane);
        this.mainBox = vbox;
        Scene scene = new Scene(vbox, colCount * 40, rowCount * 40);
        stage.setScene(scene);
        return stage;
    }

    public VBox getMainBox() {
        return mainBox;
    }

    /**
     * Charge la grille de jeu dans le panneau de la grille.
     *
     * @param row       L'indice de la ligne cliquée.
     * @param col       L'indice de la colonne cliquée.
     * @param imageSize La taille des images.
     */
    public void chargePlateau(int row, int col, double imageSize) {
        gridPane.getChildren().clear();
        Maps map = hunter.getGameModel().getMap();
        boolean[][] mapShoot = map.getMapShoot();
        CellInfo[][] maps = map.getMaps();

        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length; j++) {
                Image image = new Image(getClass().getResourceAsStream("carrenoir.png"));
                StackPane stackPane = createStackPaneWithBorder(map.getMaps()[i][j]);
                ImageView imageView = (ImageView) stackPane.getChildren().get(0);
                imageView.setImage(image);

                if (mapShoot[i][j]) {
                    stackPane = createStackPaneWithBorder(map.getMaps()[i][j]);
                    stackPane.setStyle("-fx-border-color: red; -fx-border-width: 1;"); // Bordure rouge pour les tirs
                }
                gridPane.add(stackPane, j, i);
            }
        }
    }

    /**
     * Met à jour la grille de jeu après un mouvement du chasseur.
     */
    public void updatePlateau() {
        // Récupération des coordonnées de la case cliquée
        Coordinate clickedCoordinates = controlleur.getClickedCase();
        int clickedRow = clickedCoordinates.getRow();
        int clickedCol = clickedCoordinates.getCol();

        // Récupération du nœud correspondant à la case cliquée dans le GridPane
        Node node = getNodeByRowColumnIndex(clickedRow, clickedCol, gridPane);

        if (node != null && node instanceof StackPane) {
            StackPane existingStackPane = (StackPane) node;

            // Mise à jour de l'image de la case cliquée
            updateClickedImage(existingStackPane, clickedRow, clickedCol);

            // Vérification et ajout d'un label s'il y a un chemin
            addPathLabel(existingStackPane, clickedRow, clickedCol);

            // Réarrangement du panneau dans le GridPane
            existingStackPane.toBack();
        }
    }

    /**
     * Met à jour l'image de la case cliquée.
     *
     * @param existingStackPane Le StackPane existant pour la case cliquée.
     * @param clickedRow        L'indice de la ligne cliquée.
     * @param clickedCol        L'indice de la colonne cliquée.
     */
    private void updateClickedImage(StackPane existingStackPane, int clickedRow, int clickedCol) {
        String imagePathClicked = determineImagePath(hunter.getGameModel().getMap().getMaps()[clickedRow][clickedCol]);
        Image imageClicked = new Image(getClass().getResourceAsStream(imagePathClicked));
        ImageView existingImageView = (ImageView) existingStackPane.getChildren().get(0);
        existingImageView.setImage(imageClicked);
    }

    /**
     * Ajoute un label avec le chemin s'il y a un chemin dans la case cliquée.
     *
     * @param existingStackPane Le StackPane existant pour la case cliquée.
     * @param clickedRow        L'indice de la ligne cliquée.
     * @param clickedCol        L'indice de la colonne cliquée.
     */
    private void addPathLabel(StackPane existingStackPane, int clickedRow, int clickedCol) {
        if (hunter.getGameModel().getPath().containsKey(new Coordinate(clickedRow, clickedCol))) {
            int path = hunter.getGameModel().getPath(new Coordinate(clickedRow, clickedCol));
            Label label = new Label(String.valueOf(path));
            existingStackPane.getChildren().add(label);
        }
    }

    /**
     * Getter pour le stage (fenêtre) associé à cette vue.
     *
     * @return Le stage associé à cette vue.
     */
    public Stage getStage() {
        return this.stage;
    }

    @Override
    public void update(Subject subj) {
        update(subj, null);
    }

    @Override
    public void update(Subject subj, Object data) {
        updatePlateau();
    }

}
