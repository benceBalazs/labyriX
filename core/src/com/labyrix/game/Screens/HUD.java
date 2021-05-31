package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.labyrix.game.Models.Player;

public class HUD {
    private Stage stage;
    private Viewport viewport;

    private String zugSpieler;
    private Integer cheatsLeft;
    private Integer defuse;

    private Label zugSpielerLabel;
    private Label cheatsLeftLabel;
    private Label defuseLabel;

    private Label lastEventLabel;
    private Label secondLastEventLabel;
    private Label thirdLastEventLabel;

    private String lastEvent;
    private String secondLastEvent;
    private String thirdLastEvent;

    private Table tableTopBar;
    private Table tableSideBar;

    private ShapeRenderer shapeRenderer;
    private Label.LabelStyle labelStyle;

    private float scale;

    private Player player;

    SpriteBatch batch;

    // https://github.com/libgdx/libgdx/wiki/Table

    public HUD(SpriteBatch spriteBatch, Player player){
        shapeRenderer = new ShapeRenderer();
        batch = spriteBatch;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

        labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        this.player = player;

        update(player);
    }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(stage.getCamera().combined);

        //TopBar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(1, 1, 1, 1));
        shapeRenderer.rect(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.85f, Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight() * 0.1f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.90f, Gdx.graphics.getHeight() * 0.05f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight() * 0.90f, Gdx.graphics.getHeight() * 0.05f);

        shapeRenderer.setColor(new Color(0.36470588f, 0.47058824f, 0.21568627f, 1));
        shapeRenderer.rect(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.855f, Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight() * 0.09f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.90f, Gdx.graphics.getHeight() * 0.045f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight() * 0.90f, Gdx.graphics.getHeight() * 0.045f);
        shapeRenderer.end();

        //SideBar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(1, 1, 1, 1));
        shapeRenderer.rect(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.05f, Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getHeight()*0.7f);
        shapeRenderer.rect((Gdx.graphics.getWidth() * 0.8f - Gdx.graphics.getHeight() * 0.05f), Gdx.graphics.getHeight() * 0.10f, (Gdx.graphics.getWidth()*0.15f + Gdx.graphics.getHeight() * 0.05f * 2f), Gdx.graphics.getHeight()*0.6f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight() * 0.10f, Gdx.graphics.getHeight() * 0.05f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight() * 0.70f, Gdx.graphics.getHeight() * 0.05f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.10f, Gdx.graphics.getHeight() * 0.05f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.70f, Gdx.graphics.getHeight() * 0.05f);

        shapeRenderer.setColor(new Color(0.36470588f, 0.47058824f, 0.21568627f, 1));
        shapeRenderer.rect(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.055f, Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getHeight()*0.69f);
        shapeRenderer.rect(((Gdx.graphics.getWidth() * 0.8f - Gdx.graphics.getHeight() * 0.05f) + Gdx.graphics.getHeight() * 0.005f), Gdx.graphics.getHeight() * 0.10f, ((Gdx.graphics.getWidth()*0.15f + Gdx.graphics.getHeight() * 0.05f * 2f) - Gdx.graphics.getHeight() * 0.01f), Gdx.graphics.getHeight()*0.6f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight() * 0.10f, Gdx.graphics.getHeight() * 0.045f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight() * 0.70f, Gdx.graphics.getHeight() * 0.045f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.10f, Gdx.graphics.getHeight() * 0.045f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.70f, Gdx.graphics.getHeight() * 0.045f);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0.07843137f, 0.10980292f, 0, 1));
        shapeRenderer.rect(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.08f, Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getHeight()*0.34f);
        shapeRenderer.rect(Gdx.graphics.getWidth() * 0.8f - Gdx.graphics.getHeight() * 0.02f, Gdx.graphics.getHeight() * 0.1f, Gdx.graphics.getWidth()*0.15f + Gdx.graphics.getHeight() * 0.02f * 2, Gdx.graphics.getHeight()*0.3f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight() * 0.10f, Gdx.graphics.getHeight() * 0.02f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.10f, Gdx.graphics.getHeight() * 0.02f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight() * 0.40f, Gdx.graphics.getHeight() * 0.02f);
        shapeRenderer.circle(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.40f, Gdx.graphics.getHeight() * 0.02f);
        shapeRenderer.end();

        stage.draw();


    }

    /**
     * update method should be called every time something changes whats important for the hud
     *
     */

    public void update(Player player){
        stage = new Stage(viewport, batch);
        scale = 4;

        //topbar
        zugSpieler = player.getName();
        cheatsLeft = player.getRemainingCheats();
        defuse = 1;                                                 //TODO method call missing

        tableTopBar = new Table();
        tableTopBar.bottom();
        tableTopBar.setFillParent(true);

        zugSpielerLabel = new Label("Zug: " + zugSpieler, labelStyle);
        cheatsLeftLabel = new Label("Cheats left: "+ cheatsLeft, labelStyle);
        defuseLabel = new Label("Defuses left: "+defuse, labelStyle);

        zugSpielerLabel.setFontScale(scale);
        cheatsLeftLabel.setFontScale(scale);
        defuseLabel.setFontScale(scale);

        float tablePosition = Gdx.graphics.getHeight() * 0.865f;
        tableTopBar.add(zugSpielerLabel).expandX().padBottom(tablePosition);
        tableTopBar.add(cheatsLeftLabel).expandX().padBottom(tablePosition);
        tableTopBar.add(defuseLabel).expandX().padBottom(tablePosition);

        stage.addActor(tableTopBar);

        //sidebar
        thirdLastEvent = "Spieler 1" + "\n" + "activated Trap";//secondLastEvent;
        secondLastEvent = "Spieler 1" + "\n" + "defused Trap";//lastEvent;
        lastEvent = "Spieler 2" + "\n" + "rolled 5"; //player.getName() + "\n" + player.getRemainingSteps();    //TODO if something happens, it has to be written in here

        tableSideBar = new Table();
        tableSideBar.bottom();
        tableSideBar.setFillParent(true);

        lastEventLabel = new Label(lastEvent, labelStyle);
        secondLastEventLabel = new Label(secondLastEvent, labelStyle);
        thirdLastEventLabel = new Label(thirdLastEvent, labelStyle);

        scale *= 0.75f;
        lastEventLabel.setFontScale(scale);
        secondLastEventLabel.setFontScale(scale);
        thirdLastEventLabel.setFontScale(scale);

        //tablePosition = Gdx.graphics.getWidth() * 0.75f ;
        tableSideBar.defaults().width(Gdx.graphics.getWidth()* (-0.6f));
        tableSideBar.add(thirdLastEventLabel).padBottom(5);
        tableSideBar.row();
        tableSideBar.add(secondLastEventLabel).padBottom(5);;
        tableSideBar.row();
        tableSideBar.add(lastEventLabel).padBottom(Gdx.graphics.getHeight() * 0.09f);;

        stage.addActor(tableSideBar);
    }

    public String getZugSpieler() {
        return zugSpieler;
    }

    public void setZugSpieler(String zugSpieler) {
        this.zugSpieler = zugSpieler;
    }

    public Integer getCheatsLeft() {
        return cheatsLeft;
    }

    public void setCheatsLeft(Integer cheatsLeft) {
        this.cheatsLeft = cheatsLeft;
    }

    public Integer getDefuse() {
        return defuse;
    }

    public void setDefuse(Integer defuse) {
        this.defuse = defuse;
    }

    public String getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(String lastEvent) {
        this.lastEvent = lastEvent;
    }

    public String getSecondLastEvent() {
        return secondLastEvent;
    }

    public void setSecondLastEvent(String secondLastEvent) {
        this.secondLastEvent = secondLastEvent;
    }

    public String getThirdLastEvent() {
        return thirdLastEvent;
    }

    public void setThirdLastEvent(String thirdLastEvent) {
        this.thirdLastEvent = thirdLastEvent;
    }
}
