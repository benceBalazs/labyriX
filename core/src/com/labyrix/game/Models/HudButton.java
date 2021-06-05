package com.labyrix.game.Models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.labyrix.game.ENUMS.TurnValue;
import com.labyrix.game.TurnLogic;

public class HudButton {
    Label.LabelStyle labelStyle;
    ShapeRenderer shapeRenderer;
    TurnLogic turnLogic;
    float xCoordinateButtonBegin = 0;
    float yCoordinateButtonBegin = 0;
    float xCoordinateButtonEnd = 0;
    float yCoordinateButtonEnd = 0;


    public HudButton(Label.LabelStyle labelStyle, ShapeRenderer shapeRenderer, TurnLogic turnLogic){
        this.labelStyle = labelStyle;
        this.shapeRenderer = shapeRenderer;
        this.turnLogic = turnLogic;
    }

    public HudButton(){
    }

    public Table buttonCreation (String name, float scaleFont, float xCoordinate, float yCoordinate, float lenght, float height, float edge, TurnValue turnValue) {
        Table tableSideBarButton = new Table();
        tableSideBarButton.bottom();
        tableSideBarButton.setFillParent(true);

        xCoordinateButtonBegin = xCoordinate + edge;
        xCoordinateButtonEnd = xCoordinate + edge + lenght - edge * 2f;
        yCoordinateButtonBegin = yCoordinate + height * 1.05f;
        yCoordinateButtonEnd = yCoordinate + height * 1.05f + height * 0.9f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(1, 1, 1, 1));
        shapeRenderer.rect(xCoordinate, yCoordinate + height * 1.05f, lenght, height * 0.9f);

        if (turnLogic.getTurnValue().equals(turnValue)) {
            shapeRenderer.setColor(new Color(0.07843137f, 0.10980292f, 0, 1));
            shapeRenderer.rect(xCoordinateButtonBegin, yCoordinateButtonBegin + edge, lenght - edge * 2f, height * 0.9f - edge * 2f);
        } else {
            shapeRenderer.setColor(new Color(0.53333333f, 0.53333333f, 0.53333333f, 1));
            shapeRenderer.rect(xCoordinateButtonBegin, yCoordinateButtonBegin + edge, lenght - edge * 2f, height * 0.9f - edge * 2f);
            name = "Wait...";
        }
        shapeRenderer.end();

        Label buttonName = new Label(name, labelStyle);

        buttonName.setFontScale(scaleFont*1.3f);

        buttonName.setAlignment(Align.center);
        tableSideBarButton.add(buttonName).width(lenght).height(height);
        tableSideBarButton.setPosition(xCoordinate, yCoordinate + height);
        tableSideBarButton.left();
        tableSideBarButton.setFillParent(true);
        //tableSideBarButton.debugAll();  //TODO delete if it is finished

        return tableSideBarButton;
    }

    public float getxCoordinateButtonBegin() {
        return xCoordinateButtonBegin;
    }

    public float getyCoordinateButtonBegin() {
        return yCoordinateButtonBegin;
    }

    public float getxCoordinateButtonEnd() {
        return xCoordinateButtonEnd;
    }

    public float getyCoordinateButtonEnd() {
        return yCoordinateButtonEnd;
    }
}