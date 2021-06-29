package com.labyrix.game.Models;

import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Field {
    private Integer id = 0;
    private Vector2 coordinates;
    private Image fieldImage;

    public void setId(Integer id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public Integer getId() {
        return id;
    }

    public Vector2 getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Vector2 coordinates) {
        this.coordinates = coordinates;
    }

    public Image getFieldImage() {
        return fieldImage;
    }

    public void setFieldImage(Image fieldImage) {
        this.fieldImage = fieldImage;
    }

    abstract public void addFollowingFields(PathField f);
    abstract public ArrayList<PathField> getFollowingFields();
}

