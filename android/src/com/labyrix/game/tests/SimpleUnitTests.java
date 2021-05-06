package com.labyrix.game.tests;

import com.labyrix.game.Trap;
import static org.junit.Assert.assertEquals;
import org.testng.annotations.Test;

public class SimpleUnitTests {
    
    @Test
    public void testingTrapRandomMethod(){
        float probability = 0.5f;
        Trap trap = new Trap(probability);
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