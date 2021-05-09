package com.labyrix.game;

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
        boolean result = true;
        switch (defuseMethod){
            case DEFUSEBOMB:
                //TODO result = defuseBomb()
                break;
            case CLIMBUP:
                //TODO result = climbUp();
                break;
            case CRAWLOUT:
                //TODO result = crawlOut();
                break;
            case STOPMOVING:
                //TODO result = dontMove();
                break;
            case WAVE:
                //TODO result = circleWave();
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
