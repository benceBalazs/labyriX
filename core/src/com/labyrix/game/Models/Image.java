package com.labyrix.game.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Image {
    Texture img;
    Vector2 coordinates;

    public Image(String imgPath) {
        this.img = new Texture(imgPath);
    }

    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }

    public Vector2 getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Vector2 coordinates) {
        this.coordinates = coordinates;
    }

    /*public void render(SpriteBatch batch, float x, float y) {
        batch.draw(this.img, x - Gdx.graphics.getWidth()/8f, y- Gdx.graphics.getHeight()/8f);
    }*/
}
