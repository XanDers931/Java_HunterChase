package Model;

import Utils.Coordinate;
import Utils.Subject;

/**
 * La classe Hunter représente le chasseur dans le jeu "Monster Hunter". Un
 * chasseur peut se déplacer sur la grille,
 * tirer sur le monstre, et effectuer d'autres actions. Il est également un
 * sujet observable qui notifie ses observateurs
 * lorsqu'il effectue des actions qui modifient son état.
 */
public class Hunter extends Subject {

    /** Pseudo du chasseur. */
    private String nickname;

    /** Modèle du jeu associé au chasseur. */
    private GameModel gameModel;

    /** Indique si le chasseur peut se déplacer. */
    private boolean canMoove;

    /**
     * Constructeur de la classe Hunter.
     *
     * @param nickname  Pseudo du chasseur.
     * @param gameModel Modèle du jeu associé au chasseur.
     */
    public Hunter(String nickname, GameModel gameModel) {
        this.nickname = nickname;
        this.canMoove = false;
        this.gameModel = gameModel;
    }

    /**
     * Obtient les coordonnées où le chasseur a repéré le monstre pour la dernière
     * fois.
     *
     * @return Les coordonnées repérées par le chasseur.
     */
    public Coordinate getHunted() {
        return gameModel.getHunted();
    }

    /**
     * Indique si le chasseur peut se déplacer.
     *
     * @return True si le chasseur peut se déplacer, sinon False.
     */
    public boolean getCanMoove() {
        return canMoove;
    }

    /**
     * Définit si le chasseur peut se déplacer.
     *
     * @param canMoove True si le chasseur peut se déplacer, sinon False.
     */
    public void setCanMoove(boolean canMoove) {
        this.canMoove = canMoove;
    }

    /**
     * Définit les coordonnées où le chasseur a repéré le monstre pour la dernière
     * fois.
     *
     * @param coord Les coordonnées repérées par le chasseur.
     */
    public void setHunted(Coordinate coord) {
        gameModel.setHunted(coord);
    }

    /**
     * Obtient le pseudo du chasseur.
     *
     * @return Le pseudo du chasseur.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Vérifie si le chasseur a remporté la partie en tirant sur la case spécifiée.
     *
     * @param x Coordonnée x de la case.
     * @param y Coordonnée y de la case.
     * @return True si le chasseur a remporté la partie, sinon False.
     */
    public boolean victory(int x, int y) {
        return gameModel.isMonster(new Coordinate(x, y));
    }

    /**
     * Inverse l'état de la possibilité de déplacement du chasseur.
     */
    public void changeCanMoove() {
        canMoove = !canMoove;
    }

    /**
     * Effectue un tir du chasseur sur la case spécifiée.
     *
     * @param x Coordonnée x de la case cible.
     * @param y Coordonnée y de la case cible.
     */
    public void shoot(int x, int y) {
        this.gameModel.getMap().getMapShoot()[x][y] = true;
        gameModel.setHunted(new Coordinate(x, y));
        performActionThatChangesState(x, y);
    }

    /**
     * Effectue des opérations qui modifient l'état du chasseur.
     *
     * @param x Coordonnée x de l'action effectuée.
     * @param y Coordonnée y de l'action effectuée.
     */
    public void performActionThatChangesState(int x, int y) {
        // Effectuez les opérations qui modifient l'état du chasseur ici
        // Par exemple, enregistrez les coordonnées du clic ou de l'action

        Coordinate coordonnees = new Coordinate(x, y);

        // Après les modifications, appelez notifyObservers avec l'objet Coordonnees en
        // paramètre
        notifyObservers(coordonnees);
    }

    /**
     * Obtient le modèle du jeu associé au chasseur.
     *
     * @return Le modèle du jeu associé au chasseur.
     */
    public GameModel getGameModel() {
        return gameModel;
    }
}
