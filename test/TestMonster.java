package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;
import Model.Monster;

public class TestMonster {
      @Test
    public void testMoveMonster_ValidMove() {
        // Crée un monstre et une carte pour les tests
        Monster monster = new Monster("Monster1");
        monster.getMap().initMap();

        // Déplace le monstre vers une case valide
        boolean moved = monster.moveMonster(1, 1);

        assertTrue(moved);
        assertEquals(CellInfo.MONSTER, monster.getMap().getMaps()[1][1]);
        assertEquals(CellInfo.EMPTY, monster.getMap().getMaps()[0][0]);
    }

    @Test
    public void testMoveMonster_InvalidMove() {
        // Crée un monstre et une carte pour les tests
        Monster monster = new Monster("Monster2");
        monster.getMap().initMap();

        // Tente de déplacer le monstre vers une case non valide (un mur)
        boolean moved = monster.moveMonster(0, 1);

        assertFalse(moved);
        assertEquals(CellInfo.MONSTER, monster.getMap().getMaps()[0][0]);
        assertEquals(CellInfo.WALL, monster.getMap().getMaps()[0][1]);
    }

    @Test
    public void testVictory() {
        // Crée un monstre et une carte pour les tests
        Monster monster = new Monster("Monster3");
        monster.getMap().initMap();

        // Configure une victoire en définissant une case de sortie
        monster.getMap().getMaps()[9][4] = CellInfo.EXIT;

        // Vérifie que la victoire est correctement détectée
        assertTrue(monster.victory(9, 4));
        assertFalse(monster.victory(0, 0));
    }
}
