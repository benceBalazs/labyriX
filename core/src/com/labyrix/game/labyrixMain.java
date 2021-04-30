package com.labyrix.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class labyrixMain extends ApplicationAdapter {
	private SpriteBatch batch;
	private Board isorend;
	private Player player;
	private OrthographicCamera camera;
	private int cameraHeight = 400 * 4;
	private int cameraWidth = 200 * 4;



	@Override
	public void create () {
		batch = new SpriteBatch();
		isorend = new Board();
		player = new Player("Testplayer", "img_0116.png", isorend.getPathFieldByID(1), 70, 180);
		camera = new OrthographicCamera(cameraHeight, cameraWidth);
		camera.position.set(cameraHeight / 2 - 700,cameraWidth / 2, 5);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		player.update();
		batch.begin();
		isorend.drawGround(batch);
		player.render(batch);
		cameraLerp( camera, player.getPosition());
		batch.end();

	}
	
	@Override
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
