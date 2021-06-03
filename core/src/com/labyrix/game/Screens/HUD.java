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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.labyrix.game.ENUMS.TrapEventName;
import com.labyrix.game.ENUMS.TurnValue;
import com.labyrix.game.LabyrixMain;
import com.labyrix.game.Models.Player;
import com.labyrix.game.TurnLogic;

public class HUD {
    private Stage stage;
    private Viewport viewport;

    private String hudSpielerName;          // Zeigt den Spielernamen
    private String hudTurnval;           // Zeigt an, in welchem Status sich der Charakter gerade befindet, Movement, Diceroll usw.
    private Integer hudRemSteps;            // Zeigt an, wie viele Schritte der Charakter während seines Zuges noch gehen kann.
    private Integer hudRemFields;           // Zeigt an, wie weit der Weg bis zum Ziel noch ist.
    private Integer hudReduMvmtSpeedUntil;  // Zeigt an, wie lange man sich noch eingeschränkt fortbewegt, nachdem man eine Falle abbekommen hat.

    private String lastEvent;
    private String secondLastEvent;
    private String thirdLastEvent;

    private Table buttonTabel;

    private ShapeRenderer shapeRenderer;
    private Label.LabelStyle labelStyle;

    private Player player;

    private SpriteBatch batch;

    private Skin skin;
    private TextButton cheatButton;

    private final LabyrixMain labyrixMain;
    private TurnLogic turnLogic;

    // https://github.com/libgdx/libgdx/wiki/Table
    // https://gamedev.stackexchange.com/questions/144814/label-does-not-maintain-correct-position-within-a-table

    public HUD(SpriteBatch spriteBatch, Player player, TurnLogic turnLogic){
        this.labyrixMain = LabyrixMain.getINSTANCE();
        this.turnLogic = turnLogic;
        this.player = player;
        this.shapeRenderer = new ShapeRenderer();
        this.batch = spriteBatch;
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        this.labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
    }

    public void render(SpriteBatch batch) {
        hudSpielerName = player.getName();
        hudRemSteps = player.getRemainingSteps();
        hudRemFields = 8; // TODO Algorithmus einbinden, der zeigt, wie viele Schritte man noch bis zum Ziel braucht.
        hudReduMvmtSpeedUntil = player.getCounterReducedMovementSpeed();  // TODO

        if (turnLogic.getTurnValue() == TurnValue.DICEROLL){
            hudTurnval = "Roll the Dice!";
        } else if (turnLogic.getTurnValue() == TurnValue.MOVEMENT){
            hudTurnval = "On my way.";
        } else if (turnLogic.getTurnValue() == TurnValue.PATHSELECTION){
            hudTurnval = "Select a Path please.";
        } else if (turnLogic.getTurnValue() == TurnValue.TRAPACTIVATED){
            TrapEventName currentTrap = this.player.getCurrentField().getTrap().getEvent().getEvent();
            if (currentTrap == TrapEventName.ZOMBIE){
                hudTurnval = "Oh nooo, a Zombie!";
            } else if (currentTrap == TrapEventName.BOMB){
                hudTurnval = "A bomb... we'll DIEEE! D:";
            } else if (currentTrap == TrapEventName.QUICKSAND){
                hudTurnval = "Quicksand? You serious?";
            } else if (currentTrap == TrapEventName.DOOR){
                hudTurnval = "*klick* AAAAAAaaaaaaahh...";
            }
        } else if (turnLogic.getTurnValue() == TurnValue.TRAPCHECK){
            hudTurnval = "Huch?";
        } else if (turnLogic.getTurnValue() == TurnValue.WON){
            hudTurnval = "YEEEEEAH :D";
        } else {
            hudTurnval = turnLogic.getTurnValue().toString();
        }

        stage = new Stage(viewport, batch);
        batch.setProjectionMatrix(stage.getCamera().combined);

        float barLenght = Gdx.graphics.getWidth() * 0.28f;
        float barHeight = Gdx.graphics.getHeight() * 0.08f;
        float xCoordinate = Gdx.graphics.getWidth() / 3f;
        float yCoordinate = Gdx.graphics.getHeight() * 0.91f;
        float yCoordinateLowerBar = Gdx.graphics.getHeight() * 0.91f - Gdx.graphics.getHeight() * 0.09f;

        createTopBarElement(xCoordinate / 2 - barLenght / 2, yCoordinate, barLenght, barHeight, "Name: ", hudSpielerName, true);
        createTopBarElement(xCoordinate / 2 - barLenght / 2 + xCoordinate, yCoordinate, barLenght, barHeight, "", hudTurnval, true);
        createTopBarElement(xCoordinate / 2 - barLenght / 2 + xCoordinate * 2, yCoordinate, barLenght, barHeight, "Steps left this Round: ", hudRemSteps.toString(), false);

        createTopBarElement(xCoordinate - barLenght / 2, yCoordinateLowerBar, barLenght, barHeight, "Distance to Target: ", hudRemFields.toString(), false);
        createTopBarElement(xCoordinate - barLenght / 2 + xCoordinate, yCoordinateLowerBar, barLenght, barHeight, "Debuff until: ", hudReduMvmtSpeedUntil.toString(), true);

        createSideBarElement();
        stage.draw();

    }

    public void createTopBarElement(float xCoordinate, float yCoordinate, float barLenght, float barHeight, String labelDescription, String labelValue, boolean textCenter) {
        Table tableTopBarElement = new Table();
        tableTopBarElement.bottom();
        tableTopBarElement.setFillParent(true);

        float scaleFont = barHeight * 0.04f;

        Label currentElementLabel = new Label(labelDescription + labelValue, labelStyle);

        currentElementLabel.setFontScale(scaleFont);

        if (textCenter){
            currentElementLabel.setAlignment(Align.center);
            tableTopBarElement.add(currentElementLabel).width(barLenght).height(barHeight);
            tableTopBarElement.setPosition(xCoordinate, yCoordinate);
            tableTopBarElement.left();
            tableTopBarElement.setFillParent(true);
            //tableTopBarElement.debugAll();  //TODO delete if it is finished
        } else {
            tableTopBarElement.add(currentElementLabel).expandX().fillX();  //.width(xCoordinate).padBottom(yCoordinate);
            tableTopBarElement.setPosition(xCoordinate, yCoordinate + barHeight * 0.15f);
            //tableTopBarElement.debugAll();  //TODO delete if it is finished
        }
        stage.addActor(tableTopBarElement);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(1, 1, 1, 1));
        shapeRenderer.rect(xCoordinate, yCoordinate, barLenght, barHeight);
        shapeRenderer.circle(xCoordinate, yCoordinate + barHeight/2, barHeight/2);
        shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHeight/2, barHeight/2);

        float elementEdge = 0.005f;
        float percentHeight = Gdx.graphics.getHeight() * elementEdge;

        shapeRenderer.setColor(new Color(0.36470588f, 0.47058824f, 0.21568627f, 1));
        shapeRenderer.rect(xCoordinate, yCoordinate + percentHeight, barLenght, barHeight - percentHeight*2);
        shapeRenderer.circle(xCoordinate, yCoordinate + barHeight/2, barHeight/2 - percentHeight);
        shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHeight/2, barHeight/2 - percentHeight);

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

        buttonCreation();
    }

    public void buttonCreation () {
        this.skin = new Skin();
        this.skin.addRegions(this.labyrixMain.getAssets().get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", this.labyrixMain.getFontMedium());
        this.skin.load(Gdx.files.internal("ui/uiskins.json"));

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

        stage.addActor(buttonTabel);
    }

    /**
     * update method should be called every time something changes whats important for the hud
     *
     */

    /*
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
        tableTopBar.add(hudSpielernameLabel).width(100f).padBottom(100f);                            // TODO <-----------------------------------------
        //tableTopBar.add(hudTurnvalLabel).expandX().padBottom(tablePosition);
        //tableTopBar.add(hudRemStepsLabel).expandX().padBottom(tablePosition);
        //tableTopBar.add(hudRemFieldsLabel).expandX().padBottom(tablePosition);
        //tableTopBar.add(hudReduMvmtSpeedUntilLabel).expandX().padBottom(tablePosition);

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

     */

    public String getHudSpielerName() {
        return hudSpielerName;
    }

    public void setHudSpielerName(String spielerName) {
        this.hudSpielerName = spielerName;
    }

    public String getHudTurnval() {
        return hudTurnval;
    }

    public void setHudTurnval(String turnval) {
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
