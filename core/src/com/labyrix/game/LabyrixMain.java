package com.labyrix.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.labyrix.game.Models.Board;
import com.labyrix.game.Models.Player;


public class LabyrixMain extends Game {
	public static LabyrixMain INSTANCE;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private int cameraHeight = 400 * 4;
	private int cameraWidth = 200 * 4;

	public LabyrixMain(){
		INSTANCE = this;
	}

	@Override
	public void create () {
	}

	@Override
	public void render () {

	}

	public void dispose () {
		batch.dispose();
	}

}
