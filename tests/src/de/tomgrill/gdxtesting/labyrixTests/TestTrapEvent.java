package de.tomgrill.gdxtesting.labyrixTests;
import com.labyrix.game.Models.Image;
import com.labyrix.game.ENUMS.TrapDefuseMethod;
import com.labyrix.game.Models.TrapEvent;
import com.labyrix.game.ENUMS.TrapEventName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import de.tomgrill.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TestTrapEvent {

    TrapEvent trapEvent;
    TrapEventName trapEventName;
    Image image;
    TrapDefuseMethod defuseMethod;

    @Before
    public void init(){
        this.trapEvent = new TrapEvent();
        this.trapEventName = TrapEventName.values()[new Random().nextInt(TrapEventName.values().length)];
        this.image = new Image("../android/assets/badlogic.jpg");
    }

    @Test
    public void TrapEvent_createsTrapEventObject(){
        assertNotNull(trapEvent);
    }

    @Test
    public void TrapEvent_createsRandomEvent(){
        this.trapEvent = new TrapEvent();
        assertNotNull(this.trapEvent.getEvent());
    }

    @Test
    public void TrapEvent_getsImageAutomatically(){
        assertNotNull(trapEvent.getEventImage());
    }

    @Test
    public void TrapEvent_getsDefuseMethodAutomatically(){
        assertNotNull(trapEvent.getDefuseMethod());
    }

    @Test
    public void randomTrapEvent_returnsRandomTrapEventName_sameProbabilityForEveryName(){
        TrapEventName[] identifier = new TrapEventName[TrapEventName.values().length];
        double[] count = new double[TrapEventName.values().length];
        int loops = 10000;      // should not be less than 10000

        for (int i = 0; i < loops; i++){
            TrapEventName trapEventName = trapEvent.randomTrapEvent();
            for (int j = 0; j < count.length; j++){
                if (identifier[j] == null){
                    identifier[j] = trapEventName;
                }
                if (identifier[j] == trapEventName){
                    count[j] += 1;
                    break;
                }
            }
        }

        boolean result = true;
        int probability = loops / TrapEventName.values().length;

        for (double v : count) {
            if (v > (probability * 1.05) || v < (probability * 0.95)) {     // difference should not be more than 5%
                result = false;
                break;
            }
        }

        assertTrue(result);
    }

    @Test
    public void getEvent_returnsTrapEventName(){
        assertNotNull(this.trapEvent.getEvent());
    }

    @Test
    public void setEvent_setsNewEvent(){
        while (this.trapEvent.getEvent() == trapEventName){
            this.trapEvent = new TrapEvent();
        }

        this.trapEvent.setEvent(trapEventName);

        assertEquals(trapEventName, trapEvent.getEvent());
    }

    @Test
    public void getEventImage_returnsCurrentEventImage(){
        //TODO
        assertEquals(true, false);
    }

    @Test
    public void setEventImage_setsNewImage(){
        //TODO
        assertEquals(true, false);
    }

    @Test
    public void getDefuseMethod_returnsCurrentDefuseMethod(){
        //TODO
        assertEquals(true, false);
    }

    @Test
    public void setDefuseMethod_setsNewDefuseMethod(){
        //TODO
        assertEquals(true, false);
    }

    @Test
    public void TrapEvent_showsZombieImage_whenEventIsZombie(){
        this.trapEventName = TrapEventName.ZOMBIE;
        // TODO this.image = new Image("someZombie");
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // assertEquals(trapEvent.getEventImage(), this.image);
        assertEquals(true, false);
    }

    @Test
    public void TrapEvent_hasZombieDefuseMethod_whenEventIsZombie(){
        this.trapEventName = TrapEventName.ZOMBIE;
        // TODO this.defuseMethod = null;
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // TODO assertEquals(trapEvent.getDefuseMethod(), this.defuseMethod);
        assertEquals(true, false);
    }

    @Test
    public void TrapEvent_showsBombImage_whenEventIsBomb(){
        this.trapEventName = TrapEventName.BOMB;
        // TODO this.image = new Image("someBomb");
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // assertEquals(trapEvent.getEventImage(), this.image);
        assertEquals(true, false);
    }

    @Test
    public void TrapEvent_TrapEvent_hasBombDefuseMethod_whenEventIsBomb(){
        this.trapEventName = TrapEventName.BOMB;
        // TODO this.defuseMethod = null;
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // TODO assertEquals(trapEvent.getDefuseMethod(), this.defuseMethod);
        assertEquals(true, false);
    }

  /*  @Test
    public void TrapEvent_showsPitfallImage_whenEventIsPitfall(){
        this.trapEventName = TrapEventName.PITFALL;
        // TODO this.image = new Image("somePitfall");
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // assertEquals(trapEvent.getEventImage(), this.image);
        assertEquals(true, false);
    }

    @Test
    public void TrapEvent_hasPitfallDefuseMethod_whenEventIsPitfall(){
        this.trapEventName = TrapEventName.PITFALL;
        // TODO this.defuseMethod = null;
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // TODO assertEquals(trapEvent.getDefuseMethod(), this.defuseMethod);
        assertEquals(true, false);
    }*/

    //TODO tests have to be adapted, when TrapEventNames change.


}
