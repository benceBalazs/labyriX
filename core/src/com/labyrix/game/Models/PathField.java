package com.labyrix.game.Models;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PathField extends Field {
    private ArrayList<PathField> followingFields;
    private Trap trap;
    private boolean isWinField = false;

       public PathField(int id, Image fieldImage, float trapProbability) {
        this.setId(id);
        this.setFieldImage(fieldImage);
        followingFields = new ArrayList<PathField>();
        this.trap = new Trap(trapProbability);
    }

    @Override
    public ArrayList<PathField> getFollowingFields() {
        return followingFields;
    }

    public Trap getTrap() {
        return trap;
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
    }

    @Override
    public void addFollowingFields(PathField f) {
        if (followingFields.size() < 3){
            followingFields.add(f);
        }
    }


    public PathField getFollowingField(int i) {

        if (followingFields.size() > i) {
            return followingFields.get(i);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isWinField() {
        return isWinField;
    }

    public void setWinField(boolean winField) {
        isWinField = winField;
    }
}
