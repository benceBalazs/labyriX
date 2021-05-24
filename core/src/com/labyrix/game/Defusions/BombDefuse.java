package com.labyrix.game.Defusions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.labyrix.game.LabyrixMain;

public class BombDefuse{
    private String bombcode;
    private String userinput;
    private Skin skin;
    private Table table;
    private TextButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine, clear;
    private Stage stage;
    private boolean bombDefuse;

    public BombDefuse(Camera cam){
        bombDefuse = false;
        userinput = "";
        bombcode = "";
        for (int i = 0; i < 4; i++) {
            int randomNumber = (int)(Math.random()* 9 + 1);
            bombcode += randomNumber;
        }

        //TODO Table not centered
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),cam));

        table = new Table(skin);
        table.setY(500);
        table.setX(Gdx.graphics.getHeight()/2 + 450);

        buttonOne = new TextButton("1",skin);
        buttonOne.getLabel().setFontScale(5);
        buttonTwo = new TextButton("2", skin);
        buttonTwo.getLabel().setFontScale(5);
        buttonThree = new TextButton("3", skin);
        buttonThree.getLabel().setFontScale(5);
        buttonFour = new TextButton("4",skin);
        buttonFour.getLabel().setFontScale(5);
        buttonFive = new TextButton("5", skin);
        buttonFive.getLabel().setFontScale(5);
        buttonSix = new TextButton("6", skin);
        buttonSix.getLabel().setFontScale(5);
        buttonSeven = new TextButton("7",skin);
        buttonSeven.getLabel().setFontScale(5);
        buttonEight = new TextButton("8", skin);
        buttonEight.getLabel().setFontScale(5);
        buttonNine = new TextButton("9", skin);
        buttonNine.getLabel().setFontScale(5);
        clear = new TextButton("clear", skin);
        clear.getLabel().setFontScale(5);

        buttonOne.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 1;
                System.out.println(userinput);
            }
        } );
        buttonTwo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 2;
                System.out.println(userinput);
            }
        } );
        buttonThree.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 3;
                System.out.println(userinput);
            }
        } );
        buttonFour.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 4;
                System.out.println(userinput);
            }
        } );
        buttonFive.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 5;
                System.out.println(userinput);
            }
        } );
        buttonSix.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 6;
                System.out.println(userinput);
            }
        } );
        buttonSeven.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 7;
                System.out.println(userinput);
            }
        } );
        buttonEight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 8;
                System.out.println(userinput);
            }
        } );
        buttonNine.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput += 9;
                System.out.println(userinput);
            }
        } );
        clear.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                userinput = "";
                System.out.println(userinput);
            }
        });

        table.add((CharSequence)bombcode).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/7).getActor().setFontScale(5);
        table.add((CharSequence)userinput).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/7).getActor().setFontScale(5);
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
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }


    public boolean defuseBomb() throws InterruptedException{
        System.out.println(bombDefuse);

        /*
        render();
        clearStage();
        double time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 10000){
            if (userinput.equals(bombcode)){
                bombDefuse = true;
            }
            else bombDefuse = false;
            System.out.println(bombDefuse);
            Thread.sleep(10);
        }*/
        return bombDefuse;
    }

    //Color Text in specific Cell
    public void changeStringColor(){
        if (bombcode.contains(userinput)){
            table.getCell(buttonEight).getActor().getLabel().setColor(0,255,0, 0);
        }
        else table.getCell(buttonEight).getActor().getLabel().setColor(255,0,0, 0);
    }

    public void render(){
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void clearStage(){
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                stage.clear();
            }
        },8);
    }
}
