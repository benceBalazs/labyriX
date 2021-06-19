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

    /**
     * creates a random event by randomly choosing an event name.
     * every event name has its own image and a certain defusing method.
     */
    public TrapEvent (){
        this.event = randomTrapEvent();

        switch (event){
            case ZOMBIE:
                this.eventImage = new Image("zombie.png");
                this.defuseMethod = TrapDefuseMethod.STOPMOVING;
                break;
            case BOMB:
                this.eventImage = new Image("bomb.png");
                this.defuseMethod = TrapDefuseMethod.DEFUSEBOMB;
                break;
            case DOOR:
                this.eventImage = new Image("cage.png");
                this.defuseMethod = TrapDefuseMethod.CLIMBUP;
                break;
            case QUICKSAND:
                this.eventImage = new Image("quicksand.png");
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
}
