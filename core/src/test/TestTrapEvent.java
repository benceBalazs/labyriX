package test;

import com.labyrix.game.TrapEvent;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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

}
