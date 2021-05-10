package com.labyrix.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.labyrix.game.Screens.GameScreen;
import com.labyrix.game.Screens.JoinScreen;
import com.labyrix.game.Screens.LoadingScreen;
import com.labyrix.game.Screens.LobbyScreen;
import com.labyrix.game.Screens.SplashScreen;
import com.labyrix.game.Screens.TitleScreen;


public class LabyrixMain extends Game {
	private static LabyrixMain INSTANCE;
	private int WIDTH = 320;
	private int HEIGHT = 480;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private AssetManager assets;
	private SplashScreen splashScreen;
	private GameScreen gameScreen;
	private LoadingScreen loadingScreen;
	private TitleScreen titleScreen;
	private LobbyScreen lobbyScreen;
	private JoinScreen joinScreen;
	private BitmapFont fontBig, fontMedium, fontMediumError;

	LabyrixMain(){

	}

	public static LabyrixMain getINSTANCE(){
		if(INSTANCE == null){
			INSTANCE = new LabyrixMain();
		}
		return INSTANCE;
	}

	@Override
	public void create () {
		HEIGHT = Gdx.graphics.getHeight();
		WIDTH = Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		assets = new AssetManager();
		splashScreen = new SplashScreen();
		gameScreen = new GameScreen();
		loadingScreen = new LoadingScreen();
		titleScreen = new TitleScreen();
		joinScreen = new JoinScreen();
		lobbyScreen = new LobbyScreen();
		initFonts();
		this.setScreen(loadingScreen);
	}

	private void initFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/specialElite.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.size = this.HEIGHT/14;
		params.color = Color.WHITE;
		fontBig = generator.generateFont(params);
		params.size =  this.HEIGHT/18;
		fontMedium = generator.generateFont(params);
		params.color = Color.RED;
		fontMediumError = generator.generateFont(params);
		generator.dispose();
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		assets.dispose();
		splashScreen.dispose();
		fontBig.dispose();
	}

}
