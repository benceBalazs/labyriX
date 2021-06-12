package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.kryonet.Client;
import com.labyrix.game.LabyrixMain;
import com.labyrix.game.Models.NetworkPlayer;
import com.labyrix.game.Network.ClientNetworkHandler;
import com.labyrix.game.NetworkModels.ChangeLobbyToGameRequest;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class LobbyScreen extends ScreenAdapter {

    private final LabyrixMain labyrixMain;
    private Stage stage;
    private Skin skinMedium;
    private TextButton buttonPlay, playerProfile1, playerProfile2, playerProfile3, playerProfile4;
    private Image logoImg, backgroundImg;
    private Label lobbycode;
    private ArrayList<NetworkPlayer> networkPlayers = new ArrayList<NetworkPlayer>();
    public ClientNetworkHandler clientNetworkHandler;
    public Client client;
    private String lobbyCodeReturn;
    private JoinScreen joinScreen;
    private boolean inLobby;
    private int mainPlayerId;

    public LobbyScreen(JoinScreen joinScreen) {
        this.joinScreen = joinScreen;
        this.labyrixMain = LabyrixMain.getINSTANCE();
        this.stage = new Stage(new FitViewport(labyrixMain.getWIDTH(), labyrixMain.getHEIGHT(), labyrixMain.getCamera()));
        clientNetworkHandler = ClientNetworkHandler.getInstance();
        clientNetworkHandler.addLobbyToClient(this);
        client = clientNetworkHandler.getClient();
        inLobby = false;
    }

    public void changeToGame(){
        labyrixMain.setScreen(labyrixMain.getGameScreen());
    }

    public ArrayList<NetworkPlayer> getNetworkPlayers() {
        return networkPlayers;
    }

    public void setNetworkPlayers(ArrayList<NetworkPlayer> networkPlayers) {
        this.networkPlayers = networkPlayers;
    }

    public Label getLobbycode() {
        return lobbycode;
    }

    public void setLobbycode(String lobbycode) {
        this.lobbycode.setText("Code: "+lobbycode);
    }

    public boolean isInLobby() {
        return inLobby;
    }

    public void setInLobby(boolean inLobby) {
        this.inLobby = inLobby;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        this.lobbyCodeReturn = joinScreen.getLobbyCodeReturn();
        this.skinMedium = new Skin();
        this.skinMedium.addRegions(labyrixMain.getAssets().get("ui/uiskin.atlas", TextureAtlas.class));
        this.skinMedium.add("default-font", labyrixMain.getFontMedium());
        this.skinMedium.load(Gdx.files.internal("ui/uiskins.json"));
        initScreen();
        this.networkPlayers.addAll(joinScreen.getNetworkPlayers());
        updatePlayers(networkPlayers.size());
        inLobby = true;
    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public int getMainPlayerId() {
        return mainPlayerId;
    }

    public void setMainPlayerId(int mainPlayerId) {
        this.mainPlayerId = mainPlayerId;
    }

    public void updatePlayers(int size) {
        if(playerProfile1 != null){
            stage.getActors().removeValue(playerProfile1,true);
        }
        if(playerProfile2 != null){
            stage.getActors().removeValue(playerProfile2,true);
        }
        if(playerProfile3 != null){
            stage.getActors().removeValue(playerProfile3,true);
        }
        if(playerProfile4 != null){
            stage.getActors().removeValue(playerProfile4,true);
        }
        switch (size){
            case(1):
                playerProfile1 = new TextButton(networkPlayers.get(0).getName(), skinMedium, "default");
                playerProfile1.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile1.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) + playerProfile1.getHeight()*1.875f);
                playerProfile1.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile1);

                playerProfile2 = new TextButton("Waiting ....", skinMedium, "default");
                playerProfile2.setColor(Color.GRAY);
                playerProfile2.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile2.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) + playerProfile1.getHeight()*0.625f);
                playerProfile2.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile2);

                playerProfile3 = new TextButton("Waiting ....", skinMedium, "default");
                playerProfile3.setColor(Color.GRAY);
                playerProfile3.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile3.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) - playerProfile1.getHeight()*0.625f);
                playerProfile3.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile3);

                playerProfile4 = new TextButton("Waiting ....", skinMedium, "default");
                playerProfile4.setColor(Color.GRAY);
                playerProfile4.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile4.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) - playerProfile1.getHeight()*1.875f);
                playerProfile4.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile4);
                break;
            case(2):
                playerProfile1 = new TextButton(networkPlayers.get(0).getName(), skinMedium, "default");
                playerProfile1.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile1.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) + playerProfile1.getHeight()*1.875f);
                playerProfile1.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile1);

                playerProfile2 = new TextButton(networkPlayers.get(1).getName(), skinMedium, "default");
                playerProfile2.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile2.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) + playerProfile1.getHeight()*0.625f);
                playerProfile2.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile2);

                playerProfile3 = new TextButton("Waiting ....", skinMedium, "default");
                playerProfile3.setColor(Color.GRAY);
                playerProfile3.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile3.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) - playerProfile1.getHeight()*0.625f);
                playerProfile3.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile3);

                playerProfile4 = new TextButton("Waiting ....", skinMedium, "default");
                playerProfile4.setColor(Color.GRAY);
                playerProfile4.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile4.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) - playerProfile1.getHeight()*1.875f);
                playerProfile4.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile4);
                break;
            case(3):
                playerProfile1 = new TextButton(networkPlayers.get(0).getName(), skinMedium, "default");
                playerProfile1.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile1.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) + playerProfile1.getHeight()*1.875f);
                playerProfile1.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile1);

                playerProfile2 = new TextButton(networkPlayers.get(1).getName(), skinMedium, "default");
                playerProfile2.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile2.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) + playerProfile1.getHeight()*0.625f);
                playerProfile2.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile2);

                playerProfile3 = new TextButton(networkPlayers.get(2).getName(), skinMedium, "default");
                playerProfile3.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile3.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) - playerProfile1.getHeight()*0.625f);
                playerProfile3.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile3);

                playerProfile4 = new TextButton("Waiting ....", skinMedium, "default");
                playerProfile4.setColor(Color.GRAY);
                playerProfile4.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile4.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) - playerProfile1.getHeight()*1.875f);
                playerProfile4.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile4);
                break;
            case(4):
                playerProfile1 = new TextButton(networkPlayers.get(0).getName(), skinMedium, "default");
                playerProfile1.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile1.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) + playerProfile1.getHeight()*1.875f);
                playerProfile1.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile1);

                playerProfile2 = new TextButton(networkPlayers.get(1).getName(), skinMedium, "default");
                playerProfile2.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile2.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) + playerProfile1.getHeight()*0.625f);
                playerProfile2.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile2);

                playerProfile3 = new TextButton(networkPlayers.get(2).getName(), skinMedium, "default");
                playerProfile3.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile3.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) - playerProfile1.getHeight()*0.625f);
                playerProfile3.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile3);

                playerProfile4 = new TextButton(networkPlayers.get(3).getName(), skinMedium, "default");
                playerProfile4.setSize(labyrixMain.getWIDTH()/2.8f, labyrixMain.getHEIGHT()/8f);
                playerProfile4.setPosition(labyrixMain.getWIDTH()/2f - (playerProfile1.getWidth()/2) +labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/2f - (playerProfile1.getHeight()/2) - playerProfile1.getHeight()*1.875f);
                playerProfile4.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
                stage.addActor(playerProfile4);
                break;
            default:    break;
        }
    }

    private void initScreen() {
        Texture backgroundTex = labyrixMain.getAssets().get("background.png", Texture.class);
        backgroundImg = new Image(backgroundTex);
        backgroundImg.setPosition(0-Gdx.graphics.getWidth()/8f, 0-Gdx.graphics.getHeight()/8f);
        backgroundImg.setSize(Gdx.graphics.getWidth()+2*Gdx.graphics.getWidth()/8f, Gdx.graphics.getHeight()+2*Gdx.graphics.getHeight()/8f);
        backgroundImg.addAction(sequence(alpha(0),fadeIn(.5f)));

        buttonPlay = new TextButton("Play", skinMedium, "default");
        buttonPlay.setSize(labyrixMain.getWIDTH()/3.5f, labyrixMain.getHEIGHT()/10.0f);
        buttonPlay.setPosition(labyrixMain.getWIDTH()/2f-buttonPlay.getWidth()/2-labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/6f);
        buttonPlay.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                client.sendTCP(new ChangeLobbyToGameRequest());
            }
        });

        Texture splashTex = labyrixMain.getAssets().get("lobby.png", Texture.class);
        logoImg = new Image(splashTex);
        logoImg.setSize(labyrixMain.getWIDTH()/3f, labyrixMain.getHEIGHT()/5f);
        logoImg.setPosition(labyrixMain.getWIDTH()/2f-logoImg.getWidth()/2-labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/1.5f);
        logoImg.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));

        lobbycode = new Label("", skinMedium);
        lobbycode.setText("Code: "+lobbyCodeReturn);
        lobbycode.setSize(labyrixMain.getWIDTH()/5f, labyrixMain.getHEIGHT()/10.0f);
        lobbycode.setPosition(labyrixMain.getWIDTH()/2f-lobbycode.getWidth()/2-labyrixMain.getWIDTH()/4f, labyrixMain.getHEIGHT()/1.75f);

        stage.addActor(backgroundImg);
        stage.addActor(logoImg);
        stage.addActor(buttonPlay);
        stage.addActor(lobbycode);
    }
}
