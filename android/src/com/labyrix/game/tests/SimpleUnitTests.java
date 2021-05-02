package com.labyrix.game.tests;

import com.labyrix.game.Trap;
import static org.junit.Assert.assertEquals;
import org.testng.annotations.Test;

public class SimpleUnitTests {

    @Test
    public void oneEqualsOne() {
        assertEquals(1, 1);
    }

    @Test
    public void testingTrapRandomMethod(){
        float probability = 0.5f;
        Trap trap = new Trap(probability);
        double count = 0;

        for (int i = 0; i < 1000; i++) {
            boolean trapWasActivated = trap.isTrapActivated();
            if (trapWasActivated){
                count++;
            }
        }

        count /= 1000;

        assertEquals(probability, count, 0.2);
    }

}