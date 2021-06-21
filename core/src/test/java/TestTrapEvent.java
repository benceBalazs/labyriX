import com.labyrix.game.Models.Image;
import com.labyrix.game.ENUMS.TrapDefuseMethod;
import com.labyrix.game.Models.TrapEvent;
import com.labyrix.game.ENUMS.TrapEventName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    }

    @Test
    public void TrapEvent_createsTrapEventObject(){
        assertNotNull(this.trapEvent);
    }

    @Test
    public void TrapEvent_createsRandomEvent(){
        assertNotNull(this.trapEvent.getEvent());
    }

    @Test
    public void TrapEvent_getsImageAutomatically(){
        assertNotNull(this.trapEvent.getEventImage());
    }

    @Test
    public void TrapEvent_getsDefuseMethodAutomatically(){
        assertNotNull(this.trapEvent.getDefuseMethod());
    }

    @Test
    public void randomTrapEvent_returnsRandomTrapEventName_sameProbabilityForEveryName(){
        TrapEventName[] identifier = new TrapEventName[TrapEventName.values().length];
        double[] count = new double[TrapEventName.values().length];
        int loops = 10000;      // should not be less than 10000

        for (int i = 0; i < loops; i++){
            TrapEventName trapEventName = this.trapEvent.randomTrapEvent();
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
    public void TrapEvent_showsZombieImage_whenEventIsZombie(){
        this.trapEventName = TrapEventName.ZOMBIE;
        this.image = new Image("../android/assets/zombie.png");

        while (this.trapEvent.getEvent() != this.trapEventName){
            this.trapEvent = new TrapEvent();
        }

        assertEquals(this.trapEvent.getEventImage().getImg().toString(), this.image.getImg().toString());
    }

    @Test
    public void TrapEvent_hasZombieDefuseMethod_whenEventIsZombie(){
        this.trapEventName = TrapEventName.ZOMBIE;
        this.defuseMethod = TrapDefuseMethod.STOPMOVING;

        while (this.trapEvent.getEvent() != this.trapEventName){
            this.trapEvent = new TrapEvent();
        }

        assertEquals(this.trapEvent.getDefuseMethod(), this.defuseMethod);
    }

    @Test
    public void TrapEvent_showsBombImage_whenEventIsBomb(){
        this.trapEventName = TrapEventName.BOMB;
        this.image = new Image("../android/assets/bomb.png");

        while (this.trapEvent.getEvent() != this.trapEventName){
            this.trapEvent = new TrapEvent();
        }

        assertEquals(this.trapEvent.getEventImage().getImg().toString(), this.image.getImg().toString());
    }

    @Test
    public void TrapEvent_hasBombDefuseMethod_whenEventIsBomb(){
        this.trapEventName = TrapEventName.BOMB;
        this.defuseMethod = TrapDefuseMethod.DEFUSEBOMB;

        while (this.trapEvent.getEvent() != this.trapEventName){
            this.trapEvent = new TrapEvent();
        }

        assertEquals(this.trapEvent.getDefuseMethod(), this.defuseMethod);
    }

    @Test
    public void TrapEvent_showsQuicksandImage_whenEventIsQuicksand(){
        this.trapEventName = TrapEventName.QUICKSAND;
        this.image = new Image("../android/assets/quicksand.png");

        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        assertEquals(trapEvent.getEventImage().getImg().toString(), this.image.getImg().toString());
    }

    @Test
    public void TrapEvent_hasQuicksandDefuseMethod_whenEventIsQuicksand() {
        this.trapEventName = TrapEventName.QUICKSAND;
        this.defuseMethod = TrapDefuseMethod.CRAWLOUT;

        while (trapEvent.getEvent() != trapEventName) {
            trapEvent = new TrapEvent();
        }

        assertEquals(trapEvent.getDefuseMethod(), this.defuseMethod);
    }

    @Test
    public void TrapEvent_showsDoorImage_whenEventIsDoor(){
        this.trapEventName = TrapEventName.DOOR;
        this.image = new Image("../android/assets/cage.png");

        while (trapEvent.getEvent() != trapEventName){
            trapEvent = new TrapEvent();
        }

        assertEquals(trapEvent.getEventImage().getImg().toString(), this.image.getImg().toString());
    }

    @Test
    public void TrapEvent_hasDoorDefuseMethod_whenEventIsDoor() {
        this.trapEventName = TrapEventName.DOOR;
        this.defuseMethod = TrapDefuseMethod.CLIMBUP;

        while (trapEvent.getEvent() != trapEventName) {
            trapEvent = new TrapEvent();
        }

        assertEquals(trapEvent.getDefuseMethod(), this.defuseMethod);
    }

}
