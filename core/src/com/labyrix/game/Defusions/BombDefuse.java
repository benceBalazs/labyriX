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

import java.util.ArrayList;

public class BombDefuse extends Actor {
    private final LabyrixMain labyrixMain;
    private String bombcode;
    private String userinput;
    private Skin skin;
    private Table table;
    private TextButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine, clear;
    private ArrayList<TextButton> numpadButtons = new ArrayList<TextButton>();
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

        for (int i = 1; i <= 9; i++) {
            numpadButtons.add(new TextButton("" + i,skin));
        }
        clear = new TextButton("clear", skin);

        numpadButtons.get(0).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (userinput.length()<4){
                    userinput += 1;
                }
                updateUserInputTextField();
            }
        } );
        numpadButtons.get(1).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (userinput.length()<4){
                    userinput += 2;
                }
                updateUserInputTextField();
            }
        } );
        numpadButtons.get(2).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (userinput.length()<4){
                    userinput += 3;
                }
                updateUserInputTextField();
            }
        } );
        numpadButtons.get(3).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (userinput.length()<4){
                    userinput += 4;
                }
                updateUserInputTextField();
            }
        } );
        numpadButtons.get(4).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (userinput.length()<4){
                    userinput += 5;
                }
                updateUserInputTextField();
            }
        } );
        numpadButtons.get(5).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (userinput.length()<4){
                    userinput += 6;
                }
                updateUserInputTextField();
            }
        } );
        numpadButtons.get(6).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (userinput.length()<4){
                    userinput += 7;
                }
                updateUserInputTextField();
            }
        } );
        numpadButtons.get(7).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (userinput.length()<4){
                    userinput += 8;
                }
                updateUserInputTextField();
            }
        } );
        numpadButtons.get(8).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (userinput.length()<4){
                    userinput += 9;
                }
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
        table.add(numpadButtons.get(0)).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(numpadButtons.get(1)).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(numpadButtons.get(2)).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.row();
        table.add(numpadButtons.get(3)).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(numpadButtons.get(4)).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(numpadButtons.get(5)).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.row();
        table.add(numpadButtons.get(6)).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(numpadButtons.get(7)).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
        table.add(numpadButtons.get(8)).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/6);
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
        String bombcodePart = bombcode.substring(0, userinput.length());
        if (bombcodePart.contains(userinput)){
            userinputTextfield.setColor(0,255,0,5);
        }
        else userinputTextfield.setColor(255,0,0,5);
    }
}
