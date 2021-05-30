package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.labyrix.game.LabyrixMain;
import com.labyrix.game.Models.Board;
import com.labyrix.game.Models.Player;
import com.labyrix.game.TurnLogic;

public class GameScreen implements Screen {
    private final LabyrixMain labyrixMain;
    private SpriteBatch batch;
    private Board isorend;
    private OrthographicCamera camera;
    private Player player;
    private int cameraHeight = Gdx.graphics.getHeight();
    private int cameraWidth = Gdx.graphics.getWidth();
    private TurnLogic tl;
    private HUD hud;

    public GameScreen() {
        this.labyrixMain = LabyrixMain.getINSTANCE();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        isorend = new Board(batch);
        player = new Player("Testplayer", "img_0116.png", isorend.getPathFieldByID(1), 70, 180, isorend);
        camera = new OrthographicCamera(cameraHeight, cameraWidth);
        camera.position.set(cameraHeight / 2 - 700,cameraWidth / 2, 5);
        tl = new TurnLogic(isorend, player, camera);
        hud = new HUD(batch, player);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        isorend.drawGround();
        tl.doTurn();
        if (tl.getBombRender().getStage().getActors().size == 0||tl.getBombRender().getStage().getActors() == null){
            player.render(batch);
        }
        cameraLerp( camera, player.getPosition());
        tl.getArrowActors().render();
        tl.getBombRender().render();
        batch.end();

        hud.render(batch);
        hud.update(player);
    }

    public void cameraLerp(OrthographicCamera camera, Vector2 target) {
        Vector3 position = camera.position;
        position.x = camera.position.x + (target.x - camera.position.x) * .05f;
        position.y = camera.position.y + (target.y - camera.position.y) * .05f;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
