package com.labyrix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.labyrix.game.ENUMS.TurnValue;
import com.labyrix.game.LabyrixMain;
import com.labyrix.game.Models.Player;
import com.labyrix.game.TurnLogic;

public class HUD {
    private Stage stage;
    private Viewport viewport;

    private String hudSpielerName;          // Zeigt den Spielernamen
    private TurnValue hudTurnval;           // Zeigt an, in welchem Status sich der Charakter gerade befindet, Movement, Diceroll usw.
    private Integer hudRemSteps;            // Zeigt an, wie viele Schritte der Charakter während seines Zuges noch gehen kann.
    private Integer hudRemFields;           // Zeigt an, wie weit der Weg bis zum Ziel noch ist.
    private Integer hudReduMvmtSpeedUntil;  // Zeigt an, wie lange man sich noch eingeschränkt fortbewegt, nachdem man eine Falle abbekommen hat.

    private Label hudSpielernameLabel;
    private Label hudTurnvalLabel;
    private Label hudRemStepsLabel;
    private Label hudRemFieldsLabel;
    private Label hudReduMvmtSpeedUntilLabel;

    private Label lastEventLabel;
    private Label secondLastEventLabel;
    private Label thirdLastEventLabel;

    private String lastEvent;
    private String secondLastEvent;
    private String thirdLastEvent;

    private Table tableTopBar;
    private Table tableSideBar;
    private Table buttonTabel;

    private ShapeRenderer shapeRenderer;
    private Label.LabelStyle labelStyle;

    private float scale;

    private Player player;

    private SpriteBatch batch;

    private Skin skin;
    private TextButton cheatButton;

    private final LabyrixMain labyrixMain;
    private TurnLogic turnLogic;

    // https://github.com/libgdx/libgdx/wiki/Table

    public HUD(SpriteBatch spriteBatch, Player player, TurnLogic turnLogic){
        labyrixMain = LabyrixMain.getINSTANCE();
        this.turnLogic = turnLogic;
        shapeRenderer = new ShapeRenderer();
        batch = spriteBatch;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

        labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        this.player = player;

        skin = new Skin();
        skin.addRegions(labyrixMain.getAssets().get("ui/uiskin.atlas", TextureAtlas.class));
        skin.add("default-font", labyrixMain.getFontMedium());
        skin.load(Gdx.files.internal("ui/uiskins.json"));

        cheatButton = new TextButton("Cheat", skin);
        cheatButton.getLabel().setFontScale(2);

        cheatButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                lastEvent = "111111111111";
            }

        } );

        buttonTabel = new Table(skin);
        buttonTabel.setX(Gdx.graphics.getWidth() * 0.87f);
        buttonTabel.setY(Gdx.graphics.getHeight() * 0.65f);

        buttonTabel.add(cheatButton).size(Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getHeight()*0.1f);

        update(player);
    }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(stage.getCamera().combined);
        createTopBarElement(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.7f, Gdx.graphics.getWidth() * 0.25f, Gdx.graphics.getHeight() * 0.1f);
        createTopBarElement(Gdx.graphics.getWidth() * 0.30f, Gdx.graphics.getHeight() * 0.3f, Gdx.graphics.getWidth() * 0.35f, Gdx.graphics.getHeight() * 0.2f);

        createSideBarElement();
        stage.draw();

    }

    public void createTopBarElement(float xCoordinate, float yCoordinate, float barLenght, float barHight) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(1, 1, 1, 1));
        shapeRenderer.rect(xCoordinate, yCoordinate, barLenght, barHight);
        shapeRenderer.circle(xCoordinate, yCoordinate + barHight/2, barHight/2);
        shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHight/2, barHight/2);

        float elementEdge = 0.005f;
        float percentHeight = Gdx.graphics.getHeight() * elementEdge;

        shapeRenderer.setColor(new Color(0.36470588f, 0.47058824f, 0.21568627f, 1));
        shapeRenderer.rect(xCoordinate, yCoordinate + percentHeight, barLenght, barHight - percentHeight*2);
        shapeRenderer.circle(xCoordinate, yCoordinate + barHight/2, barHight/2 - percentHeight);
        shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHight/2, barHight/2 - percentHeight);

        shapeRenderer.end();
    }

    public void createSideBarElement(){
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

    }

    /**
     * update method should be called every time something changes whats important for the hud
     *
     */

    public void update(Player player){
        stage = new Stage(viewport, batch);
        scale = Gdx.graphics.getHeight() * 0.0035f;

        //topbar
        hudSpielerName = player.getName();
        hudTurnval = turnLogic.getTurnValue();
        hudRemSteps = player.getRemainingSteps();
        hudRemFields = 8; // TODO Algorithmus einbinden, der zeigt, wie viele Schritte man noch bis zum Ziel braucht.
        hudReduMvmtSpeedUntil = 5;  // TODO

        tableTopBar = new Table();
        tableTopBar.bottom();
        tableTopBar.setFillParent(true);

        hudSpielernameLabel = new Label("Name: " + hudSpielerName, labelStyle);
        hudTurnvalLabel = new Label("Status: "+ hudTurnval, labelStyle);
        hudRemStepsLabel = new Label("Schritte: "+ hudRemSteps, labelStyle);
        hudRemFieldsLabel = new Label("Rem.Fields: " + hudRemFields, labelStyle);
        hudReduMvmtSpeedUntilLabel = new Label("Debuff: " + hudReduMvmtSpeedUntil, labelStyle);

        hudSpielernameLabel.setFontScale(scale);
        hudTurnvalLabel.setFontScale(scale);
        hudRemStepsLabel.setFontScale(scale);
        hudRemFieldsLabel.setFontScale(scale);
        hudReduMvmtSpeedUntilLabel.setFontScale(scale);

        float tablePosition = Gdx.graphics.getHeight() * 0.865f;
        tableTopBar.add(hudSpielernameLabel).expandX().padBottom(tablePosition);
        tableTopBar.add(hudTurnvalLabel).expandX().padBottom(tablePosition);
        tableTopBar.add(hudRemStepsLabel).expandX().padBottom(tablePosition);
        tableTopBar.add(hudRemFieldsLabel).expandX().padBottom(tablePosition);
        tableTopBar.add(hudReduMvmtSpeedUntilLabel).expandX().padBottom(tablePosition);

        stage.addActor(tableTopBar);

        //sidebar
        thirdLastEvent = "Spieler 1" + "\n" + "activated Trap";//secondLastEvent;
        secondLastEvent = "Spieler 1" + "\n" + "defused Trap";//lastEvent;
        lastEvent = "1";  //player.getName() + "\n" + player.getRemainingSteps();    //TODO if something happens, it has to be written in here

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

        stage.addActor(buttonTabel);
    }

    public String getHudSpielerName() {
        return hudSpielerName;
    }

    public void setHudSpielerName(String spielerName) {
        this.hudSpielerName = spielerName;
    }

    public TurnValue getHudTurnval() {
        return hudTurnval;
    }

    public void setHudTurnval(TurnValue turnval) {
        this.hudTurnval = turnval;
    }

    public Integer getHudRemSteps() {
        return hudRemSteps;
    }

    public void setHudRemSteps(Integer remSteps) {
        this.hudRemSteps = remSteps;
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
