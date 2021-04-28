package com.labyrix.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PathField extends Field{
    private ArrayList<PathField> followingFields;
    private Trap trap;

    public PathField(int id, Vector2 coordinates, Image fieldImage, Trap trap) {
        this.setId(id);
        this.setCoordinates(coordinates);
        this.setFieldImage(fieldImage);
        this.trap = trap;
    }

    public ArrayList<PathField> getFollowingFields() {
        return followingFields;
    }

    public void setFollowingFields(ArrayList<PathField> followingFields) {
        this.followingFields = followingFields;
    }

    public Trap getTrap() {
        return trap;
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
    }
}
