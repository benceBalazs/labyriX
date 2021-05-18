package com.labyrix.game.Models;

import com.labyrix.game.Defusions.BombDefuse;
import com.labyrix.game.Defusions.MovementDefuse;
import com.labyrix.game.ENUMS.TrapDefuseMethod;
import com.labyrix.game.ENUMS.TrapEventName;

import java.util.Random;

public class TrapEvent {
    private TrapEventName event;
    private Image eventImage;
    private TrapDefuseMethod defuseMethod;

    public TrapEvent (){
        this.event = randomTrapEvent();

        switch (event){
            case ZOMBIE:
                //TODO eventImage = null;
                defuseMethod = TrapDefuseMethod.STOPMOVING;
                break;
            case BOMB:
                //TODO eventImage = null;
                defuseMethod = TrapDefuseMethod.DEFUSEBOMB;
                break;
            case PITFALL:
                //TODO eventImage = null;
                defuseMethod = TrapDefuseMethod.CLIMBUP;
                break;
            case QUICKSAND:
                //TODO eventImage = null;
                defuseMethod = TrapDefuseMethod.CRAWLOUT;
                break;
            case ALIENKIDNAP:
                //TODO eventImage = null;
                defuseMethod = TrapDefuseMethod.WAVE;
                break;
            default:
                //TODO throw new IllegalArgumentException();
        }

    }

    public TrapEventName randomTrapEvent() {
        return TrapEventName.values()[new Random().nextInt(TrapEventName.values().length)];
    }

    public boolean TrapDefuse() throws InterruptedException {
        boolean result = false;
        MovementDefuse sensorDefuse = new MovementDefuse();
        //BombDefuse bombDefuse = new BombDefuse();
        switch (defuseMethod){
            case DEFUSEBOMB:
                //result = bombDefuse.defuseBomb();
                break;
            case CLIMBUP:
                result = sensorDefuse.climbUp();
                break;
            case CRAWLOUT:
                result = sensorDefuse.crawlOut();
                break;
            case STOPMOVING:
                result = sensorDefuse.dontMove();
                break;
            case WAVE:
                result = sensorDefuse.wave();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + defuseMethod);
        }
        return result;
    }

    public TrapEventName getEvent() {
        return event;
    }

    public void setEvent(TrapEventName event) {
        this.event = event;
    }

    public Image getEventImage() {
        return eventImage;
    }

    public void setEventImage(Image eventImage) {
        this.eventImage = eventImage;
    }

    public TrapDefuseMethod getDefuseMethod() {
        return defuseMethod;
    }

    public void setDefuseMethod(TrapDefuseMethod defuseMethod) {
        this.defuseMethod = defuseMethod;
    }
}
