package com.labyrix.game.Models;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class NonPathField extends Field {

    public NonPathField(int id, Image fieldImage) {
        this.setId(id);
        this.setFieldImage(fieldImage);
    }

    public NonPathField() {
    }

    @Override
    public void addFollowingFields(PathField f) {
    }

    @Override
    public ArrayList<PathField> getFollowingFields() {
        return null;
    }
}
