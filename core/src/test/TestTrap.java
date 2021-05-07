package test;

import com.labyrix.game.Trap;

import org.graalvm.compiler.debug.Assertions;
import org.junit.*;

import java.security.InvalidParameterException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestTrap {

    /**
     * Test-Names should be created like the following convention:
     * methodName_expectedBahavior_condition
     *
     */

    private float probability;
    private Trap trap;

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

    @Test(expected = InvalidParameterException.class)
    public void Trap_throwsException_whenProbabilityIsLessThanZero(){
        this.trap = null;
        this.probability = -0.1f;
        this.trap = new Trap(this.probability);
    }

    @Test(expected = InvalidParameterException.class)
    public void Trap_throwsException_whenProbabilityIsHigherThanOne(){
        this.trap = null;
        this.probability = 1.1f;
        this.trap = new Trap(this.probability);
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
