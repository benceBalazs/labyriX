package com.labyrix.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class labyrixMain extends Game {
	public static labyrixMain INSTANCE;

	public labyrixMain(){
		INSTANCE = this;
	}




	@Override
	public void create () {

		setScreen(new startScreen());

	}


}
