package test;

import Model.GameModel;
import Model.Hunter;
import Model.Monster;
import Utils.Coordinate;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestHunter {
    @Test
    public void testVictory() {
        // Taille du modèle de jeu pour l'initialisation
        int size = 10;

        // Créer un GameModel
        GameModel gameModel = new GameModel(null, null, size, 10, false);

        // Créer un Hunter pour le test
        Hunter hunter = new Hunter("TestHunter", gameModel, null);

        // Simuler la position du monstre
        Monster monster = new Monster("TestMonster", gameModel);
        monster.addCurrentCordMonster(0, 0);

        // Tester la victoire en tirant sur le monstre
        assertTrue(hunter.victory(0, 0));

        // Tester la non-victoire en tirant dans une autre position
        assertFalse(hunter.victory(1, 1));
    }

    @Test
    public void testChangeCanMove() {
        // Taille du modèle de jeu pour l'initialisation
        int size = 10;

        // Créer un GameModel
        GameModel gameModel = new GameModel(null, null, size, 10, false);

        // Créer un Hunter pour le test
        Hunter hunter = new Hunter("TestHunter", gameModel, null);

        // Vérifier l'état initial
        assertFalse(hunter.getCanMoove());

        // Changer l'état et vérifier
        hunter.changeCanMoove();
        assertTrue(hunter.getCanMoove());

        // Changer à nouveau et vérifier
        hunter.changeCanMoove();
        assertFalse(hunter.getCanMoove());
    }

    @Test
    public void testShoot() {
        // Taille du modèle de jeu pour l'initialisation
        int size = 10;

        // Créer un GameModel
        GameModel gameModel = new GameModel(null, null, size, 10, false);

        // Créer un Hunter pour le test
        Hunter hunter = new Hunter("TestHunter", gameModel, null);

        // Coordonnées de tir
        int x = 2;
        int y = 3;

        // Tirer et vérifier les coordonnées
        hunter.shoot(x, y);
        Coordinate hunted = hunter.getHunted();
        assertEquals(x, hunted.getRow());
        assertEquals(y, hunted.getCol());

        // Vérifier que la case correspondante dans la carte de tir est marquée
        assertTrue(gameModel.getMap().getMapShoot()[x][y]);
    }
}
