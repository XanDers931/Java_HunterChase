package test;

import Main.Maps;
import Model.Hunter;
import fr.univlille.iutinfo.cam.player.perception.ICellEvent.CellInfo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestHunter {

    private Hunter hunter;

    @Before
    public void setUp() {
        hunter = new Hunter("TestHunter");
    }

    @Test
    public void testVictory() {
        Maps map = hunter.getMap();
        map.getMaps()[0][0] = CellInfo.MONSTER;
        assertTrue(hunter.victory(0, 0));

        map.getMaps()[1][1] = CellInfo.EMPTY;
        assertFalse(hunter.victory(1, 1));
    }

    @Test
    public void testChangeCanMove() {
        assertFalse(hunter.canMoove);
        hunter.changeCanMoove();
        assertTrue(hunter.canMoove);
        hunter.changeCanMoove();
        assertFalse(hunter.canMoove);
    }

    @Test
    public void testShoot() {
        Maps map = hunter.getMap();
        int x = 2;
        int y = 2;

        assertFalse(map.getMapShoot()[x][y]);
        hunter.shoot(x, y);
        assertTrue(map.getMapShoot()[x][y]);
    }


}
