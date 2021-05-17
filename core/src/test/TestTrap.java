package test;

import com.labyrix.game.Trap;
import com.labyrix.game.TrapEvent;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestTrap {

    /**
     * Test-Names should be created like the following convention:
     * methodName_expectedBehavior_condition
     * if it is not important, the condition does not have to be added.
     */

    private float probability;
    private Trap trap;

    @Before
    public void init(){
        this.probability = 0.5f;
        this.trap = new Trap(this.probability);
    }

    @Test
    public void Trap_createsTrapObject(){
        this.trap = null;
        this.trap = new Trap(this.probability);

        assertNotNull(this.trap);
    }

    @Test
    public void Trap_createsTrapEventAutomatically(){
        assertNotNull(this.trap.getEvent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void Trap_throwsException_whenProbabilityLessThanZero(){
        this.trap = null;
        this.probability = -0.1f;
        this.trap = new Trap(this.probability);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Trap_throwsException_whenProbabilityHigherThanOne(){
        this.trap = null;
        this.probability = 1.1f;
        this.trap = new Trap(this.probability);
    }

    @Test
    public void isTrapActivated_activatesTrapAsOftenAsProbabilityAllows(){
        float count = 0;
        int loops = 10000;

        for (int i = 0; i < loops; i++) {
            boolean trapWasActivated = this.trap.isTrapActivated();
            if (trapWasActivated){
                count++;
            }
        }

        count /= loops;

        assertEquals(this.probability, count, 0.015);
    }

    @Test
    public void getProbability_returnsCurrentProbability(){
        assertEquals(this.probability, this.trap.getProbability(), 0);
    }

    @Test
    public void setProbability_changesCurrentProbability(){
        this.probability = 0.135f;
        this.trap.setProbability(this.probability);
        assertEquals(this.probability, this.trap.getProbability(), 0);
    }

    @Test
    public void getEvent_returnsCurrentEvent(){
        assertNotNull(this.trap.getEvent());
    }

    @Test
    public void setEvent_changesCurrentEvent(){
        TrapEvent trapEvent = new TrapEvent();

        this.trap.setEvent(trapEvent);
        Assert.assertSame(trapEvent, this.trap.getEvent());
    }

}
