package com.labyrix.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;


public class labyrixMain extends Game {
	public static labyrixMain INSTANCE;
	
	private SpriteBatch batch;
	private Board isorend;
	private Player player;
	private OrthographicCamera camera;
	private int cameraHeight = 400 * 4;
	private int cameraWidth = 200 * 4;
	private TurnLogic tl;


	public labyrixMain(){
		INSTANCE = this;
	}
  
  
	@Override
	public void create () {
    //setScreen(new startScreen());
		batch = new SpriteBatch();
		isorend = new Board(batch);
		player = new Player("Testplayer", "img_0116.png", isorend.getPathFieldByID(1), 70, 180, isorend);
		camera = new OrthographicCamera(cameraHeight, cameraWidth);
		camera.position.set(cameraHeight / 2 - 700,cameraWidth / 2, 5);
		tl = new TurnLogic(isorend, player);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		tl.doTurn();
		//player.update();
		isorend.drawGround();
		player.render(batch);
		cameraLerp( camera, player.getPosition());
		batch.end();
	}


	public void dispose () {
		batch.dispose();
	}

	public void cameraLerp(OrthographicCamera camera, Vector2 target) {
		Vector3 position = camera.position;
		position.x = camera.position.x + (target.x - camera.position.x) * .05f;
		position.y = camera.position.y + (target.y - camera.position.y) * .05f;
		camera.position.set(position);
		camera.update();
	}
}
