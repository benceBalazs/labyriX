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
                //TODO defuseMethod = null;
                break;
            case BOMB:
                //TODO eventImage = null;
                //TODO defuseMethod = null;
                break;
            case PITFALL:
                //TODO eventImage = null;
                //TODO defuseMethod = null;
                break;
            case QUICKSAND:
                //TODO eventImage = null;
                //TODO defuseMethod = null;
                break;
            case ALIENKIDNAP:
                //TODO eventImage = null;
                //TODO defuseMethod = null;
                break;
            default:
                //TODO throw new IllegalArgumentException();
        }

    }

    public TrapEventName randomTrapEvent() {
        return TrapEventName.values()[new Random().nextInt(TrapEventName.values().length)];
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