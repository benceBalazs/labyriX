package com.labyrix.game.Defusions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.labyrix.game.LabyrixMain;

public class BombDefuse extends Actor {
    private final LabyrixMain labyrixMain;
    private String bombcode;
    private String userinput;
    private Skin skin;
    private Table table;
    private TextButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine, clear;
    private TextField userinputText;
    private boolean bombDefuse;

    public BombDefuse(float x, float y){
        labyrixMain = LabyrixMain.getINSTANCE();
        bombDefuse = false;
        userinput = "";
        bombcode = "";

        for (int i = 0; i < 4; i++) {
            int randomNumber = (int)(Math.random()* 9 + 1);
            bombcode += randomNumber;
        }

        skin = new Skin();
        this.skin.addRegions(labyrixMain.getAssets().get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", labyrixMain.getFontBig());
        this.skin.load(Gdx.files.internal("ui/uiskins.json"));

        table = new Table(skin);
        table.setX(x - 120);
        table.setY(y + 100);

        userinputText = new TextField("userinput", skin);
        userinputText.setText(userinput);
        userinputText.setAlignment(1);

        buttonOne = new TextButton("1",skin);
        buttonTwo = new TextButton("2", skin);
        buttonThree = new TextButton("3", skin);
        buttonFour = new TextButton("4",skin);
        buttonFive = new TextButton("5", skin);
        buttonSix = new TextButton("6", skin);
        buttonSeven = new TextButton("7",skin);
        buttonEight = new TextButton("8", skin);
        buttonNine = new TextButton("9", skin);
        clear = new TextButton("clear", skin);

        buttonOne.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 1;
                updateUserInput();
            }

        } );
        buttonTwo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 2;
                updateUserInput();
            }
        } );
        buttonThree.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 3;
                updateUserInput();
            }
        } );
        buttonFour.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 4;
                updateUserInput();
            }
        } );
        buttonFive.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 5;
                updateUserInput();
            }
        } );
        buttonSix.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 6;
                updateUserInput();
            }
        } );
        buttonSeven.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 7;
                updateUserInput();
            }
        } );
        buttonEight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 8;
                updateUserInput();
            }
        } );
        buttonNine.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 9;
                updateUserInput();
            }
        } );
        clear.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput = "";
                updateUserInput();
            }
        });

        table.add((CharSequence)bombcode).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/7);
        table.add(userinputText).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/7);
        table.add(clear).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/7);
        table.row();
        table.add(buttonOne).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(buttonTwo).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(buttonThree).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.row();
        table.add(buttonFour).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(buttonFive).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(buttonSix).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.row();
        table.add(buttonSeven).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(buttonEight).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(buttonNine).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
    }

    public boolean bombResult(){
        if (userinput.equals(bombcode)){
            bombDefuse = true;
        }
        return bombDefuse;
    }

    public String getBombcode() {
        return bombcode;
    }

    public Table getTable() {
        return table;
    }

    private void updateUserInput() {
        userinputText.setText(userinput);
    }
}
