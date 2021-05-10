package com.labyrix.game.Models;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PathField extends Field {
    private ArrayList<PathField> followingFields;
    private Trap trap;

    public PathField(int id, Vector2 coordinates, Image fieldImage, float trapProbability) {
        this.setId(id);
        this.setCoordinates(coordinates);
        this.setFieldImage(fieldImage);
        followingFields = new ArrayList<PathField>();
        //this.trap = new Trap(trapProbability);
    }

    public PathField(int id, Image fieldImage, float trapProbability) {
        this.setId(id);
        this.setFieldImage(fieldImage);
        followingFields = new ArrayList<PathField>();
        //this.trap = new Trap(trapProbability);
    }

    public PathField(int id, Image fieldImage) {
        this.setId(id);
        this.setFieldImage(fieldImage);

        followingFields = new ArrayList<PathField>();
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

    @Override
    public void addFollowingFields(PathField f) {
        if (followingFields.size() < 3){
            followingFields.add(f);
        }
    }

    public PathField getFollowingField(int i) {
        return followingFields.get(i);
    }

}