package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import Controller.ControlMonster;
import Controller.ControlMonsterBot;
import Controller.ControlMonsterPlayer;
import Menu.Menu;
import Model.Hunter;
import Model.Monster;
import Utils.Coordinate;
import Utils.Maps;
import Utils.Observer;
import Utils.Subject;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

/**
 * La classe VueMonster est une classe qui gère l'interface utilisateur pour le
 * monstre dans le jeu. Elle affiche la grille de jeu, les informations
 * actuelles, et elle permet au joueur (ou à l'IA) de faire des mouvements.
 */
public class VueMonster implements Observer {
    /** Le monstre associé à cette vue. */
    private Monster monster;

    /** Le contrôleur associé à cette vue. */
    private ControlMonster controlleur;

    /** Le panneau de grille de jeu. */
    private GridPane gridPane;

    /** Le stage (fenêtre) associé à cette vue. */
    private Stage stage;

    /** Le label affichant les informations actuelles. */
    private Label currentLabel;

    /**
     * Setter pour le monstre associé à la vue.
     *
     * @param monster Le nouveau monstre associé à la vue.
     */
    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    /**
     * Getter pour le monstre associé à la vue.
     *
     * @return Le monstre associé à la vue.
     */
    public Monster getMonster() {
        return monster;
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
     * Constructeur de la classe VueMonster.
     *
     * @param monster Le monstre associé à la vue.
     * @param hunter  Le chasseur associé au jeu.
     * @param control Un booléen indiquant si le monstre est contrôlé par un joueur
     *                ou par une IA.
     */
    public VueMonster(Monster monster, Hunter hunter, boolean control) {
        this.monster = monster;
        if (!control) {
            this.controlleur = new ControlMonsterPlayer(this);
        } else {
            this.controlleur = new ControlMonsterBot(this);
        }
        this.stage = creerStage();
        monster.attach(this);
    }

    /**
     * Crée et configure le stage (fenêtre) associé à cette vue.
     *
     * @return Le stage créé.
     */
    public Stage creerStage() {
        Stage stage = new Stage();
        gridPane = new GridPane();
        chargePlateau();
        HBox hbox = new HBox(new Label("MONSTER"));
        hbox.setAlignment(Pos.CENTER);
        VBox vbox = new VBox();
        currentLabel = new Label("C'est au tour du Monstre");
        vbox.getChildren().add(currentLabel);
        vbox.setAlignment(Pos.CENTER);
        int imageSize = 50;
        controlleur.mMouvement();
        VBox vboxall = new VBox(hbox, vbox, gridPane);
        styleGridPane(imageSize);
        int rowCount = monster.getGameModel().getMap().getRow() + 2;
        int colCount = monster.getGameModel().getMap().getCol() + 2;
        Scene scene = new Scene(vboxall, imageSize * rowCount, imageSize * colCount);
        stage.setScene(scene);
        return stage;
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

    /**
     * Charge la grille de jeu dans le panneau de la grille.
     */
    public void chargePlateau() {
        gridPane.getChildren().clear();
        Maps map = monster.getGameModel().getMap();
        for (int i = 0; i < map.getRow(); i++) {
            for (int j = 0; j < map.getCol(); j++) {
                StackPane stackPane = createStackPaneWithBorder(map.getMaps()[i][j]);
                gridPane.add(stackPane, j, i);
            }
        }
    }

    /**
     * Crée un StackPane avec une bordure autour de l'image associée à la cellule.
     *
     * @param cellInfo La cellule pour laquelle créer le StackPane.
     * @return Le StackPane créé.
     */
    private StackPane createStackPaneWithBorder(CellInfo cellInfo) {
        StackPane stackPane = new StackPane();
        String imagePath = determineImagePath(cellInfo);
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        stackPane.getChildren().add(imageView);

        return stackPane;
    }

    /**
     * Détermine le chemin de l'image associée à la cellule spécifiée.
     *
     * @param cellInfo La cellule pour laquelle déterminer le chemin de l'image.
     * @return Le chemin de l'image associée à la cellule.
     */
    private String determineImagePath(CellInfo cellInfo) {
        switch (cellInfo) {
            case WALL:
                return "trou.jpg";
            case MONSTER:
                return "loup.jpg";
            case EXIT:
                return "monstre.avif";
            default:
                return "green.jpg";
        }
    }

    /**
     * Met à jour la grille de jeu après un mouvement du monstre.
     *
     * @param clickedRow L'indice de la ligne cliquée.
     * @param clickedCol L'indice de la colonne cliquée.
     */
    public void updatePlateau(int clickedRow, int clickedCol) {
        // Récupération des coordonnées du monstre
        Coordinate monsterCoordinates = monster.getCordMonster();
        int row = monsterCoordinates.getRow();
        int col = monsterCoordinates.getCol();

        // Récupération des nœuds correspondants aux positions cliquée et du monstre
        // dans le GridPane
        Node clickedNode = getNodeByRowColumnIndex(clickedRow, clickedCol, gridPane);
        Node monsterNode = getNodeByRowColumnIndex(row, col, gridPane);

        if (clickedNode instanceof StackPane && monsterNode instanceof StackPane) {
            StackPane existingStackPane = (StackPane) clickedNode;
            StackPane monsterStackPane = (StackPane) monsterNode;

            // Mise à jour de l'image du monstre
            updateMonsterImage(monsterStackPane);

            // Mise à jour du label du monstre
            updateMonsterLabel(monsterStackPane);

            // Réarrangement des panneaux dans le GridPane
            rearrangePanes(existingStackPane, monsterStackPane);

            // Mise à jour de l'image cliquée
            updateClickedImage(existingStackPane);

            // Application du style aux panneaux
            applyPaneStyles(existingStackPane, monsterStackPane);
        }
    }

    /**
     * Met à jour l'image du monstre à une image vide après un mouvement.
     *
     * @param monsterStackPane Le StackPane du monstre.
     */
    private void updateMonsterImage(StackPane monsterStackPane) {
        String imagePathMonster = determineImagePath(CellInfo.EMPTY);
        Image imageMonster = new Image(getClass().getResourceAsStream(imagePathMonster));
        ImageView monsterImageView = (ImageView) monsterStackPane.getChildren().get(0);
        monsterImageView.setImage(imageMonster);
    }

    /**
     * Met à jour le label du monstre avec le nombre de tours effectués.
     *
     * @param monsterStackPane Le StackPane du monstre.
     */
    private void updateMonsterLabel(StackPane monsterStackPane) {
        Label label;
        if (monsterStackPane.getChildren().size() > 1 && monsterStackPane.getChildren().get(1) instanceof Label) {
            // Si le label existe déjà, mettez à jour son texte
            label = (Label) monsterStackPane.getChildren().get(1);
            label.setText(String.valueOf(monster.getGameModel().getTurn()));
        } else {
            // Sinon, créez un nouveau label
            label = new Label(String.valueOf(monster.getGameModel().getTurn()));
            label.setTextFill(Color.BLACK);
            monsterStackPane.getChildren().add(label);
        }
    }

    /**
     * Réarrange les panneaux dans le GridPane pour mettre le monstre en
     * arrière-plan.
     *
     * @param existingStackPane Le StackPane de la cellule cliquée.
     * @param monsterStackPane  Le StackPane du monstre.
     */
    private void rearrangePanes(StackPane existingStackPane, StackPane monsterStackPane) {
        existingStackPane.toBack();
        monsterStackPane.toBack();
    }

    /**
     * Met à jour l'image de la cellule cliquée après un mouvement.
     *
     * @param existingStackPane Le StackPane de la cellule cliquée.
     */
    private void updateClickedImage(StackPane existingStackPane) {
        String imagePathClicked = determineImagePath(CellInfo.MONSTER);
        Image imageClicked = new Image(getClass().getResourceAsStream(imagePathClicked));
        ImageView existingImageView = (ImageView) existingStackPane.getChildren().get(0);
        existingImageView.setImage(imageClicked);
    }

    /**
     * Applique des styles aux panneaux pour améliorer l'apparence.
     *
     * @param existingStackPane Le StackPane de la cellule cliquée.
     * @param monsterStackPane  Le StackPane du monstre.
     */
    private void applyPaneStyles(StackPane existingStackPane, StackPane monsterStackPane) {
        existingStackPane.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        monsterStackPane.setStyle("-fx-border-color: black; -fx-border-width: 1;");
    }

    /**
     * Retourne le nœud correspondant à l'indice de ligne et de colonne spécifié
     * dans le GridPane.
     *
     * @param row      L'indice de la ligne.
     * @param column   L'indice de la colonne.
     * @param gridPane Le GridPane dans lequel chercher le nœud.
     * @return Le nœud correspondant à l'indice de ligne et de colonne, ou null si
     *         non trouvé.
     */
    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    /**
     * Applique des styles au GridPane pour améliorer l'apparence.
     *
     * @param imageSize La taille des images.
     */
    private void styleGridPane(double imageSize) {
        gridPane.setStyle("-fx-background-color: #ececec;");
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(1);
        gridPane.setVgap(1);

        for (Node node : gridPane.getChildren()) {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                stackPane.setMinSize(imageSize, imageSize);
                stackPane.setMaxSize(imageSize, imageSize);
            }
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

    /**
     * Affiche un message de victoire lorsque le monstre gagne.
     */
    public void showVictoryMessage() {
        Stage victoryStage = new Stage();
        victoryStage.setTitle("Victory!");
        Label victoryLabel = new Label("Congratulations! Monster won!");
        Button replay = new Button("Retour au menu");
        replay.setOnAction(e -> {
            Menu menu = new Menu();
            victoryStage.close();
            fermerTousLesStages();
            fermerStage();
            menu.createStage().show();
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            // Ferme toutes les fenêtres ouvertes
            victoryStage.close();
            fermerTousLesStages();
            fermerStage();
        });
        VBox vbox = new VBox(victoryLabel, replay, closeButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        Scene victoryScene = new Scene(vbox, 300, 200);
        victoryStage.setScene(victoryScene);
        victoryStage.show();
    }

    /**
     * Ferme le stage associé à cette vue.
     */
    public void fermerStage() {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Ferme tous les stages ouverts dans l'application.
     */
    private void fermerTousLesStages() {
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage && !((Stage) window).isIconified()) {
                ((Stage) window).close();
            }
        }
    }

    @Override
    public void update(Subject subj) {
        update(subj, null);
    }

    @Override
    public void update(Subject subj, Object data) {
        if (data instanceof Coordinate) {
            Coordinate coordonnees = (Coordinate) data;
            int row = coordonnees.getRow();
            int col = coordonnees.getCol();
            updatePlateau(row, col);
        }
    }
}
