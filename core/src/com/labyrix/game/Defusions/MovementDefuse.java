package com.labyrix.game.Defusions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.labyrix.game.ENUMS.TrapEventName;
import com.labyrix.game.LabyrixMain;

public class MovementDefuse {
    private final LabyrixMain labyrixMain;
    private int successCount;
    private int failCount;
    private Skin skin;
    private Table table;
    private Image left,right,up,down;
    private TextField descriptionText;

    public MovementDefuse(float x, float y, TrapEventName trapEventName){
        labyrixMain = LabyrixMain.getINSTANCE();

        skin = new Skin();
        this.skin.addRegions(labyrixMain.getAssets().get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", labyrixMain.getFontMedium());
        this.skin.load(Gdx.files.internal("ui/uiskins.json"));

        this.left = new Image(new Texture(Gdx.files.internal("defusePfeilLinks.png")));
        this.right = new Image(new Texture(Gdx.files.internal("defusePfeilRechts.png")));
        this.up = new Image(new Texture(Gdx.files.internal("defusePfeilOben.png")));
        this.down = new Image(new Texture(Gdx.files.internal("defusePfeilUnten.png")));

        this.descriptionText = new TextField("description", skin);
        this.descriptionText.setAlignment(1);

        this.table = new Table(skin);
        this.table.setX(x - 120);
        this.table.setY(y + 100);

        if (trapEventName == TrapEventName.ZOMBIE){
            descriptionText.setText("!!!Don´t Move!!!");

            table.row();
            table.add(descriptionText).size(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);;
            table.row();
        }
        else if (trapEventName == TrapEventName.DOOR){
            descriptionText.setText("!!!Shake!!!");

            table.add(up).size(Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/4);
            table.row();
            table.add(descriptionText).size(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
            table.row();
            table.add(down).size(Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/4);
        }
        else if (trapEventName == TrapEventName.QUICKSAND){
            descriptionText.setText("!!!Shake!!!");

            table.row();
            table.add(left).size(Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/6);
            table.add(descriptionText).size(Gdx.graphics.getWidth()/5,Gdx.graphics.getHeight()/5);
            table.add(right).size(Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/6);
            table.row();
        }

        successCount = 0;
        failCount = 0;
    }

    public boolean dontMove() throws InterruptedException {
        double time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 10000) {
            if (successCount > 50) {
                successCount = 0;
                return true;
            }
            else if (failCount > 10){
                return false;
            }

            if (Gdx.input.getAccelerometerX() < 11 && Gdx.input.getAccelerometerY() < 2 && Gdx.input.getAccelerometerZ() < 2 && Gdx.input.getAccelerometerX() > 8.5 && Gdx.input.getAccelerometerY() > -2 && Gdx.input.getAccelerometerZ() > -2) {
                successCount += 1;
                System.out.println("Succ : " + successCount);
            } else {
                failCount += 1;
                System.out.println("Fail :" + failCount);
            }
            Thread.sleep(100);
        }
        return false;
    }

    public boolean climbUp() throws InterruptedException {
        double time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 10000) {
            if (successCount > 30) {
                successCount = 0;
                return true;
            }
            if (Gdx.input.getAccelerometerX() > 10 || Gdx.input.getAccelerometerX() < 8.5) {
                successCount += 1;
                System.out.println(successCount);
            }
            Thread.sleep(100);
        }
        successCount = 0;
        return false;
    }

    public boolean crawlOut() throws InterruptedException {
        double time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 10000) {
            if (successCount > 30) {
                successCount = 0;
                return true;
            }
            if (Gdx.input.getAccelerometerY() > 1 || Gdx.input.getAccelerometerY() < -1) {
                successCount += 1;
                System.out.println(successCount);
            }
            Thread.sleep(100);
        }
        return false;
    }

    public Table getTable() {
        return table;
    }
}
