package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.kryonet.Client;
import com.labyrix.game.LabyrixMain;
import com.labyrix.game.Models.NetworkPlayer;
import com.labyrix.game.Models.Player;
import com.labyrix.game.Models.TextFieldFilter;
import com.labyrix.game.MusicHandler;
import com.labyrix.game.Network.ClientNetworkHandler;
import com.labyrix.game.NetworkModels.LobbyCreateRequest;
import com.labyrix.game.NetworkModels.LobbyJoinRequest;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class JoinScreen implements Screen {
    private final LabyrixMain labyrixMain;
    private Stage stage;
    private Skin skinBig, skinMedium, skinMediumError;
    private Label usernameLabel, lobbyCodeLabel;
    private TextField username, lobbyCode;
    private TextButton buttonJoin, buttonCreate;
    private Image backgroundImg;
    private ClientNetworkHandler clientNetworkHandler;
    private Client client;
    private String lobbyCodeReturn;
    private ArrayList<NetworkPlayer> networkPlayers = new ArrayList<NetworkPlayer>();
    private MusicHandler musicHandler;

    public JoinScreen(){
        this.labyrixMain = LabyrixMain.getINSTANCE();
        this.stage = new Stage(new FitViewport(labyrixMain.getWIDTH(), labyrixMain.getHEIGHT(), labyrixMain.getCamera()));
        clientNetworkHandler = ClientNetworkHandler.getInstance();
        clientNetworkHandler.addJoinToClient(this);
        client = clientNetworkHandler.getClient();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        this.musicHandler = MusicHandler.getINSTANCE();
        this.musicHandler.joinScreenMusic();
        this.skinBig = new Skin();
        this.skinBig.addRegions(labyrixMain.getAssets().get("ui/uiskin.atlas", TextureAtlas.class));
        this.skinBig.add("default-font", labyrixMain.getFontBig());
        this.skinBig.load(Gdx.files.internal("ui/uiskins.json"));
        this.skinMedium = new Skin();
        this.skinMedium.addRegions(labyrixMain.getAssets().get("ui/uiskin.atlas", TextureAtlas.class));
        this.skinMedium.add("default-font", labyrixMain.getFontMedium());
        this.skinMedium.load(Gdx.files.internal("ui/uiskins.json"));
        this.skinMediumError = new Skin();
        this.skinMediumError.addRegions(labyrixMain.getAssets().get("ui/uiskin.atlas", TextureAtlas.class));
        this.skinMediumError.add("default-font", labyrixMain.getFontMediumError());
        this.skinMediumError.load(Gdx.files.internal("ui/uiskins.json"));
        initScreen();
        clientNetworkHandler.startConnection();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    public void changeToLobby(){
        labyrixMain.setScreen(labyrixMain.getLobbyScreen());
    }

    private void initScreen() {
        Texture backgroundTex = labyrixMain.getAssets().get("background.png", Texture.class);
        backgroundImg = new Image(backgroundTex);
        backgroundImg.setPosition(0-Gdx.graphics.getWidth()/8f, 0-Gdx.graphics.getHeight()/8f);
        backgroundImg.setSize(Gdx.graphics.getWidth()+2*Gdx.graphics.getWidth()/8f, Gdx.graphics.getHeight()+2*Gdx.graphics.getHeight()/8f);
        backgroundImg.addAction(sequence(alpha(0),fadeIn(.5f)));

        buttonJoin = new TextButton("Join", skinBig, "default");
        buttonJoin.setSize(labyrixMain.getWIDTH()/3.5f, labyrixMain.getHEIGHT()/9f);
        buttonJoin.setPosition(labyrixMain.getWIDTH()/2f-buttonJoin.getWidth()*1.1f, labyrixMain.getHEIGHT()/2f-labyrixMain.getHEIGHT()/2.5f);
        buttonJoin.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonJoin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!username.getText().equals("") && !lobbyCode.getText().equals("")){
                    //TODO client send JoinRequest
                    networkPlayers.add(new NetworkPlayer(username.getText(),"DinoOrange.png"));
                    setLobbyCodeReturn(lobbyCode.getText());
                    LobbyJoinRequest lobbyJoinRequest = new LobbyJoinRequest(networkPlayers.get(0),Integer.parseInt(lobbyCode.getText()));
                    client.sendTCP(lobbyJoinRequest);
                } else if(username.getText().equals("")) {
                    username = new TextField("", skinMediumError);
                    username.setMessageText("Enter Username");
                    username.setSize(labyrixMain.getWIDTH()/2f, labyrixMain.getHEIGHT()/7f);
                    username.setAlignment(1);
                    username.setPosition(labyrixMain.getWIDTH()/2f-username.getWidth()/2f, labyrixMain.getHEIGHT()/4f+labyrixMain.getHEIGHT()/4f+labyrixMain.getHEIGHT()/8f);
                    username.setTextFieldListener(new TextField.TextFieldListener() {
                        public void keyTyped(TextField textField, char key) {
                            if (key == '\n' || key == '\r') {
                                textField.getOnscreenKeyboard().show(false);
                                stage.setKeyboardFocus(null);
                            }
                        }
                    });

                    stage.addActor(username);
                } else {
                    lobbyCode = new TextField("", skinMediumError);
                    lobbyCode.setMessageText("Enter Lobby Code");
                    lobbyCode.setSize(labyrixMain.getWIDTH()/2f, labyrixMain.getHEIGHT()/7f);
                    lobbyCode.setAlignment(1);
                    lobbyCode.setPosition(labyrixMain.getWIDTH()/2f-username.getWidth()/2f, labyrixMain.getHEIGHT()/4f+labyrixMain.getHEIGHT()/16f);
                    lobbyCode.setTextFieldFilter(new TextFieldFilter());
                    lobbyCode.setTextFieldListener(new TextField.TextFieldListener() {
                        public void keyTyped(TextField textField, char key) {
                            if (key == '\n' || key == '\r') {
                                textField.getOnscreenKeyboard().show(false);
                                stage.setKeyboardFocus(null);
                            }
                        }
                    });
                    stage.addActor(lobbyCode);
                }
            }
        });

        buttonCreate = new TextButton("Create", skinBig);
        buttonCreate.setSize(labyrixMain.getWIDTH()/3.5f, labyrixMain.getHEIGHT()/9f);
        buttonCreate.setPosition(labyrixMain.getWIDTH()/2f+buttonJoin.getWidth()*0.1f, labyrixMain.getHEIGHT()/2f-labyrixMain.getHEIGHT()/2.5f);
        buttonCreate.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        buttonCreate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!username.getText().equals("")){
                    //TODO client send CreateRequest
                    networkPlayers.add(new NetworkPlayer(username.getText(),"DinoOrange.png"));
                    LobbyCreateRequest lobbyCreateRequest = new LobbyCreateRequest(networkPlayers.get(0));
                    client.sendTCP(lobbyCreateRequest);
                } else if(username.getText().equals("")) {
                    stage.getActors().removeValue(username,true);
                    username = new TextField("", skinMediumError);
                    username.setMessageText("Enter Username");
                    username.setSize(labyrixMain.getWIDTH()/2f, labyrixMain.getHEIGHT()/7f);
                    username.setAlignment(1);
                    username.setPosition(labyrixMain.getWIDTH()/2f-username.getWidth()/2f, labyrixMain.getHEIGHT()/4f+labyrixMain.getHEIGHT()/4f+labyrixMain.getHEIGHT()/8f);
                    username.setTextFieldListener(new TextField.TextFieldListener() {
                        public void keyTyped(TextField textField, char key) {
                            if (key == '\n' || key == '\r') {
                                textField.getOnscreenKeyboard().show(false);
                                stage.setKeyboardFocus(null);
                            }
                        }
                    });

                    stage.addActor(username);
                }
            }
        });

        username = new TextField("", skinMedium);
        username.setMessageText("Enter Username");
        username.setSize(labyrixMain.getWIDTH()/2f, labyrixMain.getHEIGHT()/7f);
        username.setAlignment(1);
        username.setPosition(labyrixMain.getWIDTH()/2f-username.getWidth()/2f, labyrixMain.getHEIGHT()/4f+labyrixMain.getHEIGHT()/4f+labyrixMain.getHEIGHT()/8f);
        username.setTextFieldListener(new TextField.TextFieldListener() {
            public void keyTyped(TextField textField, char key) {
                if (key == '\n' || key == '\r') {
                    textField.getOnscreenKeyboard().show(false);
                    stage.setKeyboardFocus(null);
                }
            }
        });

        lobbyCode = new TextField("", skinMedium);
        lobbyCode.setMessageText("Enter Lobby Code");
        lobbyCode.setSize(labyrixMain.getWIDTH()/2f, labyrixMain.getHEIGHT()/7f);
        lobbyCode.setAlignment(1);
        lobbyCode.setPosition(labyrixMain.getWIDTH()/2f-username.getWidth()/2f, labyrixMain.getHEIGHT()/4f+labyrixMain.getHEIGHT()/16f);
        lobbyCode.setTextFieldFilter(new TextFieldFilter());
        lobbyCode.setFocusTraversal(false);
        lobbyCode.setTextFieldListener(new TextField.TextFieldListener() {
            public void keyTyped(TextField textField, char key) {
                if (key == '\n' || key == '\r') {
                    textField.getOnscreenKeyboard().show(false);
                    stage.setKeyboardFocus(null);
                }
            }
        });

        usernameLabel = new Label("", skinMedium);
        usernameLabel.setText("Username");
        usernameLabel.setSize(280*1f, 60*1f);
        usernameLabel.setPosition(username.getX(), username.getY()+username.getHeight()*1.15f);

        lobbyCodeLabel = new Label("", skinMedium);
        lobbyCodeLabel.setText("Lobby Code");
        lobbyCodeLabel.setSize(280*1f, 60*1f);
        lobbyCodeLabel.setPosition(lobbyCode.getX(), lobbyCode.getY()+lobbyCode.getHeight()*1.15f);

        stage.addActor(backgroundImg);
        stage.addActor(buttonCreate);
        stage.addActor(buttonJoin);
        stage.addActor(username);
        stage.addActor(lobbyCode);
        stage.addActor(usernameLabel);
        stage.addActor(lobbyCodeLabel);
    }

    public ArrayList<NetworkPlayer> getNetworkPlayers() {
        return networkPlayers;
    }

    public void setNetworkPlayers(ArrayList<NetworkPlayer> networkPlayers) {
        this.networkPlayers = networkPlayers;
    }

    public String getLobbyCodeReturn() {
        return lobbyCodeReturn;
    }

    public void setLobbyCodeReturn(String lobbyCodeReturn) {
        this.lobbyCodeReturn = lobbyCodeReturn;
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
}
