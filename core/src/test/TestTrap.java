package test;

import com.labyrix.game.Trap;

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class TestTrap {

    private float probability;
    private Trap trap = new Trap(0.5f);

    @Before
    public void init(){
        probability = 0.5f;
        trap = new Trap(probability);
    }

    @Test
    public void testing_isTrapActivated(){
        float count = 0;
        int loops = 10000;

        for (int i = 0; i < loops; i++) {
            boolean trapWasActivated = trap.isTrapActivated();
            if (trapWasActivated){
                count++;
            }
        }

        count /= loops;

        assertEquals(probability, count, 0.01);
    }
}
