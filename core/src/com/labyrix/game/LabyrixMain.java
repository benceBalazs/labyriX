package com.labyrix.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.esotericsoftware.kryonet.Client;
import com.labyrix.game.Network.ClientNetworkHandler;
import com.labyrix.game.Screens.GameScreen;
import com.labyrix.game.Screens.JoinScreen;
import com.labyrix.game.Screens.LoadingScreen;
import com.labyrix.game.Screens.LobbyScreen;
import com.labyrix.game.Screens.SplashScreen;
import com.labyrix.game.Screens.TitleScreen;
import com.labyrix.game.Screens.WinnerScreen;


public class LabyrixMain extends Game {
	private static LabyrixMain INSTANCE = null;
	private int WIDTH;
	private int HEIGHT;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private AssetManager assets;
	private SplashScreen splashScreen;
	private GameScreen gameScreen;
	private LoadingScreen loadingScreen;
	private TitleScreen titleScreen;
	private LobbyScreen lobbyScreen;
	private JoinScreen joinScreen;
	private WinnerScreen winnerScreen;
	private BitmapFont fontBig, fontMedium, fontMediumError;
	private ClientNetworkHandler clientNetworkHandler;
	private Client client;

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
		clientNetworkHandler = ClientNetworkHandler.getInstance();
		client = clientNetworkHandler.getClient();
		gameScreen = new GameScreen();
		loadingScreen = new LoadingScreen();
		titleScreen = new TitleScreen();
		joinScreen = new JoinScreen();
		winnerScreen = new WinnerScreen();
		lobbyScreen = new LobbyScreen(joinScreen);
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

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public AssetManager getAssets() {
		return assets;
	}

	public void setAssets(AssetManager assets) {
		this.assets = assets;
	}

	public BitmapFont getFontBig() {
		return fontBig;
	}

	public void setFontBig(BitmapFont fontBig) {
		this.fontBig = fontBig;
	}

	public BitmapFont getFontMedium() {
		return fontMedium;
	}

	public void setFontMedium(BitmapFont fontMedium) {
		this.fontMedium = fontMedium;
	}

	public BitmapFont getFontMediumError() {
		return fontMediumError;
	}

	public void setFontMediumError(BitmapFont fontMediumError) {
		this.fontMediumError = fontMediumError;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int WIDTH) {
		this.WIDTH = WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int HEIGHT) {
		this.HEIGHT = HEIGHT;
	}

	public SplashScreen getSplashScreen() {
		return splashScreen;
	}

	public void setSplashScreen(SplashScreen splashScreen) {
		this.splashScreen = splashScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public LoadingScreen getLoadingScreen() {
		return loadingScreen;
	}

	public void setLoadingScreen(LoadingScreen loadingScreen) {
		this.loadingScreen = loadingScreen;
	}

	public TitleScreen getTitleScreen() {
		return titleScreen;
	}

	public void setTitleScreen(TitleScreen titleScreen) {
		this.titleScreen = titleScreen;
	}

	public LobbyScreen getLobbyScreen() {
		return lobbyScreen;
	}

	public void setLobbyScreen(LobbyScreen lobbyScreen) {
		this.lobbyScreen = lobbyScreen;
	}

	public JoinScreen getJoinScreen() {
		return joinScreen;
	}

	public void setJoinScreen(JoinScreen joinScreen) {
		this.joinScreen = joinScreen;
	}

	public WinnerScreen getWinnerScreen() { return winnerScreen; }

	public void setWinnerScreen(WinnerScreen winnerScreen) { this.winnerScreen = winnerScreen; }
}
