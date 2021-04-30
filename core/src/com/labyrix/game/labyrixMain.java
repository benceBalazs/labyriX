package com.labyrix.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class labyrixMain extends ApplicationAdapter {
	private SpriteBatch batch;
	private Board isorend;
	private Player player;


	@Override
	public void create () {
		batch = new SpriteBatch();
		isorend = new Board();
		player = new Player("Testplayer", "img_0116.png", isorend.getPathFieldByID(1), 70, 180);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		player.update();
		batch.begin();
		isorend.drawGround(batch);
		player.render(batch);
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
