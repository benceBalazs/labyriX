package test;

import com.labyrix.game.TrapEvent;
import com.labyrix.game.TrapEventName;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestTrapEvent {

    TrapEvent trapEvent;

    @Before
    public void init(){
        this.trapEvent = new TrapEvent();
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

}
