package com.labyrix.game;

import com.badlogic.gdx.math.Vector2;

public class NonPathField extends Field{

    public NonPathField(int id, Vector2 coordinates, Image fieldImage) {
        this.setId(id);
        this.setCoordinates(coordinates);
        this.setFieldImage(fieldImage);
    }
}
