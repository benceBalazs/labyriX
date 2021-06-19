package com.labyrix.game.Models;

import com.labyrix.game.Defusions.MovementDefuse;
import com.labyrix.game.ENUMS.TrapDefuseMethod;
import com.labyrix.game.ENUMS.TrapEventName;

import java.util.Random;

/**
 * When a trap is activated, it is decided here which trap it is, what image it has and how a player can defuse it.
 */
public class TrapEvent {

    /**
     * event: is used to define the eventImage and the defuseMethod.
     * eventImage: if a trap gets activated, this image shows up in the game.
     * defuseMethod: if a trap gets activated, this defuse method shows up on screen.
     * sensorDefuse:        // TODO
     */
    private TrapEventName event;
    private Image eventImage;
    private TrapDefuseMethod defuseMethod;
    private MovementDefuse sensorDefuse;

    /**
     * creates a random event by randomly choosing an event name.
     * every event name has its own image and a certain defusing method.
     */
    public TrapEvent (){
        this.event = randomTrapEvent();

        switch (event){
            case ZOMBIE:
                this.eventImage = new Image("../android/assets/zombie.png");
                this.defuseMethod = TrapDefuseMethod.STOPMOVING;
                break;
            case BOMB:
                this.eventImage = new Image("../android/assets/bomb.png");
                this.defuseMethod = TrapDefuseMethod.DEFUSEBOMB;
                break;
            case DOOR:
                this.eventImage = new Image("../android/assets/cage.png");
                this.defuseMethod = TrapDefuseMethod.CLIMBUP;
                break;
            case QUICKSAND:
                this.eventImage = new Image("../android/assets/quicksand.png");
                this.defuseMethod = TrapDefuseMethod.CRAWLOUT;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * creates a random event name. is used in the constructor.
     * @return random event name. may be ZOMBIE, BOMB, QUICKSAND or DOOR
     */
    public TrapEventName randomTrapEvent() {
        return TrapEventName.values()[new Random().nextInt(TrapEventName.values().length)];
    }

    /**
     *                                                  //TODO deprecated?
     * @return
     * @throws InterruptedException
     */
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

    /**
     * getter and setter.
     */
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
