package test;

import com.labyrix.game.Trap;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestTrap {

    /**
     * Test-Names should be created like the following convention:
     * methodName_expectedBahavior_condition
     *
     */

    private float probability;
    private Trap trap = new Trap(0.5f);

    @Before
    public void init(){
        this.probability = 0.5f;
        this.trap = new Trap(this.probability);
    }

    @Test
    public void Trap_createsTrapObjectAndReturnsIt_ifProbabilityIsBetweenZeroAndOne(){
        this.trap = null;
        this.trap = new Trap(this.probability);

        assertNotNull(this.trap);
    }


    @Test
    public void isTrapActivated_returnsTrue_asOftenAsTheProbabilityAllows(){
        float count = 0;
        int loops = 10000;

        for (int i = 0; i < loops; i++) {
            boolean trapWasActivated = this.trap.isTrapActivated();
            if (trapWasActivated){
                count++;
            }
        }

        count /= loops;

        assertEquals(this.probability, count, 0.01);
    }
}
