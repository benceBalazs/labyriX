package com.labyrix.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class ArrowActor extends Actor {
    Sprite texture;

    public ArrowActor(String imgPath, float xPos, float yPos, final String actorName, final TurnLogic tl, final int fieldindex) {
        Texture t = new Texture(imgPath);
        texture = new Sprite(t);
        texture.setPosition(xPos, yPos);
        setBounds(texture.getX(), texture.getY(), texture.getWidth(), texture.getHeight());
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(actorName);
                System.out.println(tl.getPlayer().getCurrentField().getFollowingField(fieldindex).getId());
                tl.getPlayer().setCurrentField(tl.getPlayer().getCurrentField().getFollowingField(fieldindex));
                Vector2 playerPosition = new Vector2(tl.getPlayer().getCurrentField().getCoordinates().x + 64, tl.getPlayer().getCurrentField().getCoordinates().y + 184);
                tl.getPlayer().setPosition(playerPosition);
                tl.getPlayer().setRemainingSteps(tl.getPlayer().getRemainingSteps()-1);

                tl.getArrowActors().setArrowActorUp(null);
                tl.getArrowActors().setArrowActorDown(null);
                tl.getArrowActors().setArrowActorRight(null);
                tl.getArrowActors().setArrowActorLeft(null);

                tl.setTurnValue(TurnValue.MOVEMENT);
                tl.getArrowActors().hide();

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
