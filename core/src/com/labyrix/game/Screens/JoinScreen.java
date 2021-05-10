package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.labyrix.game.LabyrixMain;

public class JoinScreen extends ScreenAdapter {
    SpriteBatch batch;
    Texture img;

    public static Texture backgroundTexture;


    private Stage stage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;


    private Stage stageTwo;
    private Texture myTextureTwo;
    private TextureRegion myTextureRegionTwo;
    private TextureRegionDrawable myTexRegionDrawableTwo;
    private ImageButton buttonTwo;

    public JoinScreen(){
        myTexture = new Texture(Gdx.files.internal("Join.jpg"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable);
        button.setPosition(600,170);

        stage = new Stage(new ScreenViewport());
        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);


        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                LabyrixMain.INSTANCE.setScreen(new LobbyScreen());
            }

            ;
        });

        myTextureTwo = new Texture(Gdx.files.internal("Create.jpg"));
        myTextureRegionTwo = new TextureRegion(myTextureTwo);
        myTexRegionDrawableTwo = new TextureRegionDrawable(myTextureRegionTwo);
        buttonTwo = new ImageButton(myTexRegionDrawableTwo);
        buttonTwo.setPosition(1400,170);

        stageTwo = new Stage(new ScreenViewport());
        stageTwo.addActor(buttonTwo);
        Gdx.input.setInputProcessor(stageTwo);


        buttonTwo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                LabyrixMain.INSTANCE.setScreen(new LobbyScreen());
            }

            ;
        });


        batch = new SpriteBatch();


        backgroundTexture = new Texture("Hintergrundbild.png");



    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();


        batch.draw(backgroundTexture,0,0,2600,1500);

        batch.end();



        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        stageTwo.act(Gdx.graphics.getDeltaTime());
        stageTwo.draw();

    }

    @Override
    public void dispose() {

        backgroundTexture.dispose();
    }
}
