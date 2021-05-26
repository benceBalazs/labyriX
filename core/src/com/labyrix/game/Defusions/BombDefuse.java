package com.labyrix.game.Defusions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.labyrix.game.LabyrixMain;

public class BombDefuse extends Actor {
    private final LabyrixMain labyrixMain;
    private String bombcode;
    private String userinput;
    private Skin skin;
    private Table table;
    private TextButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine, clear;
    private TextField userinputTextfield, bombcodeTextfield;
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

        bombcodeTextfield = new TextField("bombcode", skin);
        bombcodeTextfield.setText(bombcode);
        bombcodeTextfield.setAlignment(1);

        userinputTextfield = new TextField("userinput", skin);
        userinputTextfield.setText(userinput);
        userinputTextfield.setAlignment(1);

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
                updateUserInputTextField();
            }

        } );
        buttonTwo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 2;
                updateUserInputTextField();
            }
        } );
        buttonThree.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 3;
                updateUserInputTextField();
            }
        } );
        buttonFour.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 4;
                updateUserInputTextField();
            }
        } );
        buttonFive.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 5;
                updateUserInputTextField();
            }
        } );
        buttonSix.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 6;
                updateUserInputTextField();
            }
        } );
        buttonSeven.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 7;
                updateUserInputTextField();
            }
        } );
        buttonEight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 8;
                updateUserInputTextField();
            }
        } );
        buttonNine.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 9;
                updateUserInputTextField();
            }
        } );
        clear.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput = "";
                updateUserInputTextField();
            }
        });

        table.add(bombcodeTextfield).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(userinputTextfield).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(clear).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
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

    private void updateUserInputTextField() {
        userinputTextfield.setText(userinput);
        if (bombcode.contains(userinput)){
            userinputTextfield.setColor(0,255,0,5);
        }
        else userinputTextfield.setColor(255,0,0,5);
    }
}
