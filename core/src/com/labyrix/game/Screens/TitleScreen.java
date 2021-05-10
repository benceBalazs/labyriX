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
import com.labyrix.game.Screens.JoinScreen;

public class TitleScreen extends ScreenAdapter {
    SpriteBatch batch;
    Texture img;


    public static Texture backgroundTexture;


    private Stage stage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;

    public TitleScreen() {
        myTexture = new Texture(Gdx.files.internal("Playnow.jpg"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable);
        button.setPosition(1000,200);

        stage = new Stage(new ScreenViewport());
        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);


        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                LabyrixMain.INSTANCE.setScreen(new JoinScreen());
            }

            ;
        });


        batch = new SpriteBatch();
        img = new Texture("schriftApp.jpg");


        backgroundTexture = new Texture("bildApp.png");

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 700, 1000);


        batch.draw(backgroundTexture,0,0,2600,1500);

        batch.end();



        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();



    }



    @Override
    public void dispose() {

        img.dispose();
        backgroundTexture.dispose();
    }
}
