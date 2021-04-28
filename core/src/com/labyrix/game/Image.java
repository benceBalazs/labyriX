package com.labyrix.game;

import com.badlogic.gdx.graphics.Texture;

public class Image {
    Texture img;

    public Image(String imgPath) {
        this.img = new Texture(imgPath);
    }

    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }
}
