package com.labyrix.game.Defusions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BombDefuse {
    private String bombcode;
    private String userinput;
    private Skin skin;
    private Table table;
    private TextButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine;
    private Viewport viewport;
    private Stage stage;

    public BombDefuse(Camera cam){
        userinput = "";
        bombcode = "";

        for (int i = 0; i < 4; i++) {
            int randomNumber = (int)(Math.random()* 9 + 1);
            bombcode += randomNumber;
        }
        //TODO Table not centered
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),cam);
        stage = new Stage(viewport);

        table = new Table(skin);

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

        buttonOne.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                userinput += 1;
            }
        });
        buttonTwo.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                userinput += 2;
            }
        });
        buttonThree.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                userinput += 3;
            }
        });
        buttonFour.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                userinput += 4;
            }
        });
        buttonFive.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                userinput += 5;
            }
        });
        buttonSix.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                userinput += 6;
            }
        });
        buttonSeven.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                userinput += 7;
            }
        });
        buttonEight.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                userinput += 8;
            }
        });
        buttonNine.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                userinput += 9;
            }
        });

        table.add("Code: ").size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8).getActor().setFontScale(5);
        table.add((CharSequence)bombcode).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/8).getActor().setFontScale(5);
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
        render();
        /*
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                stage.clear();
            }
        },10);*/

        //TODO Indicator for right/wrong input

        float time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 10000){
            if (userinput.equals(bombcode)){
                stage.clear();
                return true;
            }
            Thread.sleep(100);
        }
        stage.clear();
        return false;
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
}
