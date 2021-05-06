package com.labyrix.game.tests;

import com.labyrix.game.Trap;
import static org.junit.Assert.assertEquals;
import org.junit.*;

public class SimpleUnitTests {

    private float probability;
    private Trap trap = new Trap(0.5f);

    @Before
    public void init(){
        probability = 0.5f;
        trap = new Trap(probability);
    }

    @Test
    public void testingTrapRandomMethod(){
        float count = 0;
        int loops = 10000;

        for (int i = 0; i < loops; i++) {
            boolean trapWasActivated = trap.isTrapActivated();
            if (trapWasActivated){
                count++;
            }
        }

        count /= loops;
        System.out.println(probability);

        assertEquals(probability, count, 0.01);
    }

}