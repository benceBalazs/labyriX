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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.labyrix.game.ENUMS.TrapEventName;
import com.labyrix.game.ENUMS.TurnValue;
import com.labyrix.game.Models.HudButton;
import com.labyrix.game.Models.Player;
import com.labyrix.game.TurnLogic;

public class HUD {
    private Stage stage;
    private Viewport viewport;

    private String hudSpielerName;          // Zeigt den Spielernamen
    private String hudTurnval;              // Zeigt an, in welchem Status sich der Charakter gerade befindet, Movement, Diceroll usw.
    private Integer hudRemSteps;            // Zeigt an, wie viele Schritte der Charakter während seines Zuges noch gehen kann.
    private Integer hudRemFields;           // Zeigt an, wie weit der Weg bis zum Ziel noch ist.
    private Integer hudReduMvmtSpeedUntil;  // Zeigt an, wie lange man sich noch eingeschränkt fortbewegt, nachdem man eine Falle abbekommen hat.

    private ShapeRenderer shapeRenderer;
    private Label.LabelStyle labelStyle;

    private Player player;
    private TurnLogic turnLogic;

    private HudButton uncoverButton;
    private HudButton cheatButton;
    private HudButton diceButton;
    private final Color colorLightGreen;
    private final Color colorDarkGreen;
    private final Color colorWhite;
    private final Color colorRed;

    private int frameCounter;

    private TurnValue buttonStatus;

    // https://github.com/libgdx/libgdx/wiki/Table
    // https://gamedev.stackexchange.com/questions/144814/label-does-not-maintain-correct-position-within-a-table

    public HUD(Player player, TurnLogic turnLogic){
        this.turnLogic = turnLogic;
        this.player = player;
        this.shapeRenderer = new ShapeRenderer();
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        this.labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        this.uncoverButton = new HudButton(this.labelStyle, this.shapeRenderer, turnLogic);
        this.cheatButton = new HudButton(this.labelStyle, this.shapeRenderer, turnLogic);
        this.diceButton = new HudButton(this.labelStyle, this.shapeRenderer, turnLogic);

        this.colorLightGreen = new Color(0.36470588f, 0.47058824f, 0.21568627f, 1);
        this.colorDarkGreen = new Color(0.07843137f, 0.10980292f, 0, 1);
        this.colorWhite = new Color(1, 1, 1, 1);
        this.colorRed = new Color(1, 0 , 0, 1);

        this.frameCounter = 0;
        this.buttonStatus = TurnValue.DICEROLL;
    }

    public void render(SpriteBatch batch) {
        this.hudSpielerName = this.player.getName();
        this.hudTurnval = turnvalTranslator(this.turnLogic.getPlayer().getTurnValue());
        this.hudRemSteps = this.player.getRemainingSteps();
        this.hudRemFields = this.player.getMaxRemainingFields();                                                  // TODO an algorithm that shows the number of fields remaining to the destination must be called.
        this.hudReduMvmtSpeedUntil = player.getCounterReducedMovementSpeed();

        this.stage = new Stage(this.viewport, batch);
        batch.setProjectionMatrix(this.stage.getCamera().combined);

        // top-bars
        float xCoordinate = Gdx.graphics.getWidth() / 3f;
        float yCoordinate = Gdx.graphics.getHeight() * 0.91f;
        float barLenght = Gdx.graphics.getWidth() * 0.28f;
        float barHeight = Gdx.graphics.getHeight() * 0.08f;
        float yCoordinateLowerBar = Gdx.graphics.getHeight() * 0.91f - Gdx.graphics.getHeight() * 0.09f;

        createTopBarElement(xCoordinate / 2 - barLenght / 2, yCoordinate, barLenght, barHeight, "Name: ", this.hudSpielerName, true, false);
        createTopBarElement(xCoordinate / 2 - barLenght / 2 + xCoordinate, yCoordinate, barLenght, barHeight, "", this.hudTurnval, true, true);
        createTopBarElement(xCoordinate / 2 - barLenght / 2 + xCoordinate * 2, yCoordinate, barLenght, barHeight, "Steps left this Round: ", this.hudRemSteps.toString(), true, false);

        createTopBarElement(xCoordinate - barLenght / 2, yCoordinateLowerBar, barLenght, barHeight, "Distance to Target: ", this.hudRemFields.toString(), true, false);
        createTopBarElement(xCoordinate - barLenght / 2 + xCoordinate, yCoordinateLowerBar, barLenght, barHeight, "Debuff until: ", this.hudReduMvmtSpeedUntil.toString(), true, false);

        // side-bar
        xCoordinate = Gdx.graphics.getWidth() * 0.8f;
        yCoordinate = Gdx.graphics.getHeight() * 0.10f;
        float radius = Gdx.graphics.getHeight() * 0.05f;
        barLenght = Gdx.graphics.getWidth() * 0.15f;
        barHeight = Gdx.graphics.getHeight() * 0.60f;

        createSideBarElement(xCoordinate, yCoordinate, radius, barLenght, barHeight);

        this.stage.draw();
    }

    private String turnvalTranslator (TurnValue turnValue){
        if (turnValue == TurnValue.DICEROLL){
            return "Roll the Dice!";
        } else if (turnValue == TurnValue.MOVEMENT){
            return "On my way.";
        } else if (turnValue == TurnValue.PATHSELECTION){
            return "Select a Path please.";
        } else if (turnValue == TurnValue.TRAPACTIVATED){
            TrapEventName currentTrap = this.player.getCurrentField().getTrap().getEvent().getEvent();

            if (currentTrap == TrapEventName.ZOMBIE){
                return "Oh nooo, a Zombie!";
            } else if (currentTrap == TrapEventName.BOMB){
                return "A bomb... we'll DIEEE! D:";
            } else if (currentTrap == TrapEventName.QUICKSAND){
                return "Quicksand? You serious?";
            } else if (currentTrap == TrapEventName.DOOR){
                return "*klick* AAAAAAaaaaaaahh...";
            }

        } else if (turnValue == TurnValue.TRAPCHECK){
            return "Huch?";
        } else if (turnValue == TurnValue.WON){
            return "YEEEEEAH :D";
        }

        return this.turnLogic.getPlayer().getTurnValue().toString();
    }

    private void createTopBarElement(float xCoordinate, float yCoordinate, float barLenght, float barHeight, String labelDescription, String labelValue, boolean textCenter, boolean signal) {
        Table tableTopBarElement = new Table();
        tableTopBarElement.bottom();
        tableTopBarElement.setFillParent(true);

        float scaleFont = barHeight * 0.04f;

        Label currentElementLabel = new Label(labelDescription + labelValue, this.labelStyle);

        currentElementLabel.setFontScale(scaleFont);

        if (textCenter){
            currentElementLabel.setAlignment(Align.center);
            tableTopBarElement.add(currentElementLabel).width(barLenght).height(barHeight);
            tableTopBarElement.setPosition(xCoordinate, yCoordinate);
            tableTopBarElement.left();
            tableTopBarElement.setFillParent(true);
            //tableTopBarElement.debugAll();
        } else {
            tableTopBarElement.add(currentElementLabel).expandX().fillX();
            tableTopBarElement.setPosition(xCoordinate, yCoordinate + barHeight * 0.15f);
            //tableTopBarElement.debugAll();
        }

        this.stage.addActor(tableTopBarElement);

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (signal && turnLogic.getPlayer().getTurnValue() == TurnValue.TRAPACTIVATED && frameCounter < 40) {
            this.shapeRenderer.setColor(colorRed);
        } else {
            this.shapeRenderer.setColor(colorWhite);
        }

        this.shapeRenderer.rect(xCoordinate, yCoordinate, barLenght, barHeight);
        this.shapeRenderer.circle(xCoordinate, yCoordinate + barHeight/2, barHeight/2);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHeight/2, barHeight/2);

        float elementEdge = 0.005f;
        float percentHeight = Gdx.graphics.getHeight() * elementEdge;

        if (signal && turnLogic.getPlayer().getTurnValue() == TurnValue.TRAPACTIVATED && frameCounter < 15) {
            this.shapeRenderer.setColor(colorRed);
        } else {
            this.shapeRenderer.setColor(colorWhite);
            if (frameCounter >= 80){
                frameCounter = 0;
            }
        }

        this.shapeRenderer.setColor(colorLightGreen);
        this.shapeRenderer.rect(xCoordinate, yCoordinate + percentHeight, barLenght, barHeight - percentHeight*2);
        this.shapeRenderer.circle(xCoordinate, yCoordinate + barHeight/2, barHeight/2 - percentHeight);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHeight/2, barHeight/2 - percentHeight);

        this.shapeRenderer.end();

        this.frameCounter++;
    }

    private void createSideBarElement(float xCoordinate, float yCoordinate, float radius, float barLenght, float barHeight){
        Table tableSideBar = new Table();

        tableSideBar.bottom();
        tableSideBar.setFillParent(true);

        float scaleFont = Gdx.graphics.getHeight() * 0.08f * 0.025f;

        Label description = new Label("Remaining Steps:", this.labelStyle);
        Label firstMultiplayerPlayer = new Label("Franz: " + 15, this.labelStyle);      //TODO there is no functionality yet. still has to be inserted.
        Label secondMultiplayerPlayer = new Label("Dieter: " + 3, this.labelStyle);     //TODO there is no functionality yet. still has to be inserted.
        Label thirdMultiplayerPlayer = new Label("Udo: " + 80, this.labelStyle);        //TODO there is no functionality yet. still has to be inserted.

        description.setFontScale(scaleFont);
        firstMultiplayerPlayer.setFontScale(scaleFont);
        secondMultiplayerPlayer.setFontScale(scaleFont);
        thirdMultiplayerPlayer.setFontScale(scaleFont);

        tableSideBar.add(description).expandX().fillX();
        tableSideBar.row();
        tableSideBar.add(firstMultiplayerPlayer).expandX().fillX();
        tableSideBar.row();
        tableSideBar.add(secondMultiplayerPlayer).expandX().fillX();
        tableSideBar.row();
        tableSideBar.add(thirdMultiplayerPlayer).expandX().fillX();
        tableSideBar.setPosition(xCoordinate, yCoordinate);
        //tableSideBar.debugAll();

        this.stage.addActor(tableSideBar);

        float elementEdge = 0.005f;
        float percentHeight = Gdx.graphics.getHeight() * elementEdge;

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(colorWhite);
        this.shapeRenderer.circle(xCoordinate, yCoordinate, radius);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate, radius);
        this.shapeRenderer.circle(xCoordinate, yCoordinate + barHeight, radius);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHeight, radius);
        this.shapeRenderer.rect(xCoordinate, yCoordinate - radius, barLenght, barHeight + radius * 2f);
        this.shapeRenderer.rect(xCoordinate - radius, yCoordinate, barLenght + radius * 2f, barHeight);

        this.shapeRenderer.setColor(colorLightGreen);
        this.shapeRenderer.circle(xCoordinate, yCoordinate, radius - percentHeight);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate, radius - percentHeight);
        this.shapeRenderer.circle(xCoordinate, yCoordinate + barHeight, radius - percentHeight);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHeight, radius - percentHeight);
        this.shapeRenderer.rect(xCoordinate, yCoordinate - radius + percentHeight, barLenght, barHeight + radius * 2f - percentHeight * 2f);
        this.shapeRenderer.rect(xCoordinate - radius + percentHeight, yCoordinate, barLenght + radius * 2f - percentHeight * 2f, barHeight);

        radius = radius * 0.5f;
        barHeight = barHeight / 4f;

        this.shapeRenderer.setColor(colorDarkGreen);
        this.shapeRenderer.circle(xCoordinate, yCoordinate, radius);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate, radius);
        this.shapeRenderer.circle(xCoordinate, yCoordinate + barHeight, radius);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHeight, radius);
        this.shapeRenderer.rect(xCoordinate, yCoordinate - radius, barLenght, barHeight + radius * 2f);
        this.shapeRenderer.rect(xCoordinate - radius, yCoordinate, barLenght + radius * 2f, barHeight);
        this.shapeRenderer.end();

        this.stage.addActor(this.uncoverButton.buttonCreation("Uncover", scaleFont, xCoordinate, yCoordinate + radius * 1.5f + barHeight * 2, barLenght, barHeight, percentHeight, TurnValue.DICEROLL, 1)); // TODO aufruf in render verschieben
        this.stage.addActor(this.cheatButton.buttonCreation("Cheat", scaleFont, xCoordinate, yCoordinate + radius * 1.5f + barHeight, barLenght, barHeight, percentHeight, buttonStatus, this.player.getRemainingCheats()));
        this.stage.addActor(this.diceButton.buttonCreation("Dice", scaleFont, xCoordinate, yCoordinate + radius * 1.5f, barLenght, barHeight, percentHeight, TurnValue.DICEROLL, 1));

        this.turnLogic.setUncoverButton(this.uncoverButton);
        this.turnLogic.setCheatButton(this.cheatButton);
        this.turnLogic.setDiceButton(this.diceButton);
    }

    public String getHudSpielerName() {
        return this.hudSpielerName;
    }

    public void setHudSpielerName(String spielerName) {
        this.hudSpielerName = spielerName;
    }

    public String getHudTurnval() {
        return this.hudTurnval;
    }

    public void setHudTurnval(String turnval) {
        this.hudTurnval = turnval;
    }

    public Integer getHudRemSteps() {
        return this.hudRemSteps;
    }

    public void setHudRemSteps(Integer remSteps) {
        this.hudRemSteps = remSteps;
    }

    public Integer getHudRemFields() {
        return hudRemFields;
    }

    public void setHudRemFields(Integer hudRemFields) {
        this.hudRemFields = hudRemFields;
    }

    public Integer getHudReduMvmtSpeedUntil() {
        return hudReduMvmtSpeedUntil;
    }

    public void setHudReduMvmtSpeedUntil(Integer hudReduMvmtSpeedUntil) {
        this.hudReduMvmtSpeedUntil = hudReduMvmtSpeedUntil;
    }

    public HudButton getUncoverButton() {
        return uncoverButton;
    }

    public void setUncoverButton(HudButton uncoverButton) {
        this.uncoverButton = uncoverButton;
    }

    public HudButton getCheatButton() {
        return cheatButton;
    }

    public void setCheatButton(HudButton cheatButton) {
        this.cheatButton = cheatButton;
    }

    public HudButton getDiceButton() {
        return diceButton;
    }

    public void setDiceButton(HudButton diceButton) {
        this.diceButton = diceButton;
    }

    public void setButtonStatus(TurnValue buttonStatus) {
        this.buttonStatus = buttonStatus;
    }
}
