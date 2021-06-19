package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.kryonet.Client;
import com.labyrix.game.ENUMS.TrapEventName;
import com.labyrix.game.ENUMS.TurnValue;
import com.labyrix.game.LabyrixMain;
import com.labyrix.game.Models.Board;
import com.labyrix.game.Models.NetworkPlayer;
import com.labyrix.game.Models.Player;
import com.labyrix.game.Network.ClientNetworkHandler;
import com.labyrix.game.TurnLogic;

import java.util.ArrayList;

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
    private ClientNetworkHandler clientNetworkHandler;
    private Client client;
    private int mainPlayerId;
    private ArrayList<NetworkPlayer> networkPlayers = new ArrayList<NetworkPlayer>();

    public GameScreen() {
        this.labyrixMain = LabyrixMain.getINSTANCE();
        clientNetworkHandler = ClientNetworkHandler.getInstance();
        clientNetworkHandler.addGameToClient(this);
        client = clientNetworkHandler.getClient();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        isorend = new Board(batch);
        camera = new OrthographicCamera(cameraHeight, cameraWidth);
        camera.position.set(cameraHeight / 2 - 700,cameraWidth / 2, 5);

        for (NetworkPlayer np: networkPlayers) {
            if (np.getId() == this.mainPlayerId) {
                this.player = new Player(mainPlayerId, np.getLobbyId(), np.getName(), np.getImagePath(), isorend.getPathFieldByID(1), 70, 180, isorend);
                System.out.println(this.player.toString());
            }
        }

        tl = new TurnLogic(isorend, player, camera);
        hud = new HUD(player, tl);

        //Serverstuff - fill list of other Players
        for (NetworkPlayer np: networkPlayers) {
            if (np.getId() != this.mainPlayerId) {
                Player p = new Player(np.getId(), np.getLobbyId(), np.getName(), np.getImagePath(), isorend.getPathFieldByID(1), 70, 180, isorend);
                System.out.println(p.toString());
                tl.addPlayer(p);
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        isorend.drawGround();
        tl.doTurn();
        if (tl.getTrapRender().getStage().getActors().size == 0 || tl.getTrapRender().getStage().getActors() == null){
            if (tl.getUncoverRender().getStage().getActors().size == 0 || tl.getUncoverRender().getStage().getActors() == null){
                for (Player p: tl.getPlayers()) {
                    p.render(batch);
                }
                player.render(batch);
            }
        }
        if (tl.getPlayer().getTurnValue() == TurnValue.TRAPACTIVATED && tl.getPlayer().getCurrentField().getTrap().getEvent().getEvent() != TrapEventName.QUICKSAND) {
            tl.getPlayer().getCurrentField().getTrap().getEvent().getEventImage().render(batch, tl.getPlayer().getCurrentField().getCoordinates().x, tl.getPlayer().getCurrentField().getCoordinates().y);

        }
        cameraLerp( camera, player.getPosition());
        tl.getUncoverRender().render();
        if (tl.getArrowActors() != null) {
            tl.getArrowActors().render();
        }
        tl.getTrapRender().render();
        batch.end();

        hud.render(batch);
    }

    public void cameraLerp(OrthographicCamera camera, Vector2 target) {
        Vector3 position = camera.position;
        position.x = camera.position.x + (target.x - camera.position.x) * .05f;
        position.y = camera.position.y + (target.y - camera.position.y) * .05f;
        camera.position.set(position);
        camera.update();
    }

    public TurnLogic getTl() {
        return tl;
    }

    public void setTl(TurnLogic tl) {
        this.tl = tl;
    }

    public void switchToEnd(boolean condition){
        if (condition){
            labyrixMain.setScreen(labyrixMain.getWinnerScreen());
        }else {
            labyrixMain.setScreen(labyrixMain.getLoserScreen());
        }
        client.stop();
    }

    public int getMainPlayerId() {
        return mainPlayerId;
    }

    public void setMainPlayerId(int mainPlayerId) {
        this.mainPlayerId = mainPlayerId;
    }

    public ArrayList<NetworkPlayer> getNetworkPlayers() {
        return networkPlayers;
    }

    public void setNetworkPlayers(ArrayList<NetworkPlayer> networkPlayers) {
        this.networkPlayers = networkPlayers;
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
