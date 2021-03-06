package com.labyrix.game.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.labyrix.game.TurnLogic;
import com.labyrix.game.ENUMS.TurnValue;

public class ArrowActor extends Actor {
    Sprite texture;

    /** Erstellt die ArrowActors - weißt Image und Position zu
     * zuweisung des TouchDown Eventlisteners - Wird der Actor geklickt, wird der jeweilige Pfad gewählt
     * @param: imgPath - Pfad für das Image, welches dem Actor zugewiesen wird
     * @param: xPos - x Koordinate des Actors
     * @param: yPos - y Koordinate des Actors
     * @param: turnlogic - Turnlogic, in der der betroffene Player gespeichert wird
     * @param: fieldindex - Index des Feldes in der Liste der Folgefelder des aktuell betretenen Pathfields
     * @return:
     */
    public ArrowActor(String imgPath, float xPos, float yPos, final TurnLogic tl, final int fieldindex) {
        Texture t = new Texture(imgPath);
        texture = new Sprite(t);
        texture.setPosition(xPos, yPos);
        texture.setSize(256f, 256f);
        setBounds(texture.getX(), texture.getY(), texture.getWidth(), texture.getHeight());
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(tl.getPlayer().getCurrentField().getFollowingField(fieldindex).getId());
                tl.getPlayer().setCurrentField(tl.getPlayer().getCurrentField().getFollowingField(fieldindex));
                Vector2 playerPosition = new Vector2(tl.getPlayer().getCurrentField().getCoordinates().x + 64, tl.getPlayer().getCurrentField().getCoordinates().y + 184);
                tl.getPlayer().setPosition(playerPosition);
                tl.getPlayer().setRemainingSteps(tl.getPlayer().getRemainingSteps()-1);

                tl.getPlayer().setTurnValue(TurnValue.MOVEMENT);
                tl.getArrowActors().hide();
                tl.getArrowActors().setIsHidden(true);

                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        texture.draw(batch);
    }

}
