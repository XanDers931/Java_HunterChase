package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import Model.GameModel;
import Model.Monster;
import Utils.Coordinate;

public class TestMonster {
    @Test
    void testMoveMonster() {
        // Taille du modèle de jeu pour l'initialisation
        int size = 10;

        // Créer un GameModel
        GameModel gameModel = new GameModel(null, null, size, 10, false);

        // Créer un Monster pour le test
        Monster monster = new Monster("TestMonster", gameModel);

        // Coordonnées actuelles du monstre
        Coordinate currentCord = monster.getCordMonster();

        // Coordonnées de la nouvelle position (vers la droite)
        int newX = currentCord.getRow();
        int newY = currentCord.getCol() + 1;

        // Effectuer le mouvement et vérifier
        assertTrue(monster.moveMonster(newX, newY));
        assertEquals(CellInfo.EMPTY, gameModel.getMap().getMaps()[currentCord.getRow()][currentCord.getCol()]);
        assertEquals(CellInfo.MONSTER, gameModel.getMap().getMaps()[newX][newY]);

        // Coordonnées actuelles du monstre après le mouvement
        Coordinate newCord = monster.getCordMonster();
        assertEquals(newX, newCord.getRow());
        // assertEquals(newY, newCord.getCol());

        // Test de mouvement vers une position non adjacente
        int invalidX = currentCord.getRow() + 2;
        int invalidY = currentCord.getCol() + 2;
        assertFalse(monster.moveMonster(invalidX, invalidY));
        assertEquals(CellInfo.EMPTY, gameModel.getMap().getMaps()[invalidX][invalidY]);

        // Test de mouvement vers un mur
        int wallX = currentCord.getRow();
        int wallY = currentCord.getCol() - 1;
        gameModel.getMap().setCellInfo(wallX, wallY, CellInfo.WALL);
        assertFalse(monster.moveMonster(wallX, wallY));
        assertEquals(CellInfo.WALL, gameModel.getMap().getMaps()[wallX][wallY]);
    }

    @Test
    void testVictory() {
        // Taille du modèle de jeu pour l'initialisation
        int size = 10;

        // Créer un GameModel
        GameModel gameModel = new GameModel(null, null, size, 10, false);

        // Créer un Monster pour le test
        Monster monster = new Monster("TestMonster", gameModel);

        // Coordonnées de la sortie
        Coordinate exitCord = gameModel.getMap().getCordUser(CellInfo.EXIT);

        // Tester la victoire en atteignant la sortie
        assertTrue(monster.victory(exitCord.getRow(), exitCord.getCol()));

        // Tester la non-victoire en restant à la position initiale du monstre
        assertFalse(monster.victory(0, 0));
    }

    @Test
    void testChangeCanMove() {
        // Taille du modèle de jeu pour l'initialisation
        int size = 10;

        // Créer un GameModel
        GameModel gameModel = new GameModel(null, null, size, 10, false);

        // Créer un Monster pour le test
        Monster monster = new Monster("TestMonster", gameModel);

        // Vérifier l'état initial
        assertTrue(monster.getCanMoove());

        // Changer l'état et vérifier
        monster.changeCanMoove();
        assertFalse(monster.getCanMoove());

        // Changer à nouveau et vérifier
        monster.changeCanMoove();
        assertTrue(monster.getCanMoove());
    }
}
