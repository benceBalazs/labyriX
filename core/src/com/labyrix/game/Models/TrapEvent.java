package com.labyrix.game.Models;

import com.labyrix.game.Defusions.MovementDefuse;
import com.labyrix.game.ENUMS.TrapDefuseMethod;
import com.labyrix.game.ENUMS.TrapEventName;

import java.util.Random;

public class TrapEvent {
    private TrapEventName event;
    private Image eventImage;
    private TrapDefuseMethod defuseMethod;
    private MovementDefuse sensorDefuse;

    public TrapEvent (){
        this.event = randomTrapEvent();

        switch (event){
            case ZOMBIE:
                this.eventImage = new Image("zombie.png");
                defuseMethod = TrapDefuseMethod.STOPMOVING;
                break;
            case BOMB:
                this.eventImage = new Image("bomb.png");
                defuseMethod = TrapDefuseMethod.DEFUSEBOMB;
                break;
            case DOOR:
                this.eventImage = new Image("cage.png");
                defuseMethod = TrapDefuseMethod.CLIMBUP;
                break;
            case QUICKSAND:
                this.eventImage = new Image("quicksand.png");
                defuseMethod = TrapDefuseMethod.CRAWLOUT;
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
        this.sensorDefuse = new MovementDefuse(3f,3f,TrapEventName.DOOR);

        switch (defuseMethod){
            case DEFUSEBOMB:
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
                //result = sensorDefuse.wave();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + defuseMethod);
        }
        System.out.println(result);
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

    public MovementDefuse getSensorDefuse() {
        return sensorDefuse;
    }
}
