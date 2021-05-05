package com.labyrix.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class ArrowActor extends Actor {
     Sprite texture;

    public ArrowActor(String imgPath, float xPos, float yPos) {
        Texture t = new Texture(imgPath);
        texture = new Sprite(t);
        texture.setPosition(xPos, yPos);
        setBounds(texture.getX(), texture.getY(), texture.getWidth(), texture.getHeight());
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Touch");
                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        texture.draw(batch);
    }

}
