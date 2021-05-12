package test;

import com.labyrix.game.Image;
import com.labyrix.game.TrapDefuseMethod;
import com.labyrix.game.TrapEvent;
import com.labyrix.game.TrapEventName;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestTrapEvent {

    TrapEvent trapEvent;
    TrapEventName trapEventName;
    Image image;
    TrapDefuseMethod defuseMethod;

    @Before
    public void init(){
        this.trapEvent = new TrapEvent();
        this.trapEventName = TrapEventName.values()[new Random().nextInt(TrapEventName.values().length)];
        //TODO this.image = new Image("badlogic.jpg"); does not work
    }

    @Test
    public void TrapEvent_createsTrapEventObject(){
        assertNotNull(trapEvent);
    }

    @Test
    public void TrapEvent_createsRandomEvent_whenTrapEventGetsCreated(){
        this.trapEvent = new TrapEvent();
        assertNotNull(this.trapEvent.getEvent());
    }

    @Test
    public void TrapEvent_getImage_whenTrapEventGetsCreated(){
        assertNotNull(trapEvent.getEventImage());
    }

    @Test
    public void TrapEvent_getDefuseMethod_whenTrapEventGetsCreated(){
        assertNotNull(trapEvent.getDefuseMethod());
    }

    @Test
    public void randomTrapEvent_givesBackRandomTrapEventName_sameProbabilityForEveryName(){
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
    public void getEvent_givesBackTrapEventName(){
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
    public void getEventImage_(){
        //TODO
    }

    @Test
    public void setEventImage_setsNewImage(){
        //TODO
        assertEquals(true, true);
    }

    @Test
    public void getDefuseMethod_(){
        //TODO
        assertEquals(true, true);
    }

    @Test
    public void setDefuseMethod_(){
        //TODO
        assertEquals(true, true);
    }

    @Test
    public void TrapEvent_irgendwas(){
        this.trapEventName = TrapEventName.ZOMBIE;
        // TODO this.image = new Image("someZombie");
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // assertEquals(trapEvent.getEventImage(), this.image);
        assertEquals(true, true);
    }

    @Test
    public void TrapEvent_irgendwas2(){
        this.trapEventName = TrapEventName.ZOMBIE;
        // TODO this.defuseMethod = null;
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // TODO assertEquals(trapEvent.getDefuseMethod(), this.defuseMethod);
        assertEquals(true, true);
    }

    @Test
    public void TrapEvent_irgendwas3(){
        this.trapEventName = TrapEventName.BOMB;
        // TODO this.image = new Image("someBomb");
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // assertEquals(trapEvent.getEventImage(), this.image);
        assertEquals(true, true);
    }

    @Test
    public void TrapEvent_irgendwas4(){
        this.trapEventName = TrapEventName.BOMB;
        // TODO this.defuseMethod = null;
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // TODO assertEquals(trapEvent.getDefuseMethod(), this.defuseMethod);
        assertEquals(true, true);
    }

    @Test
    public void TrapEvent_irgendwas5(){
        this.trapEventName = TrapEventName.PITFALL;
        // TODO this.image = new Image("somePitfall");
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // assertEquals(trapEvent.getEventImage(), this.image);
        assertEquals(true, true);
    }

    @Test
    public void TrapEvent_irgendwas6(){
        this.trapEventName = TrapEventName.PITFALL;
        // TODO this.defuseMethod = null;
        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        // TODO assertEquals(trapEvent.getDefuseMethod(), this.defuseMethod);
        assertEquals(true, true);
    }

    //TODO tests have to be adapted, when TrapEventNames change.


}
