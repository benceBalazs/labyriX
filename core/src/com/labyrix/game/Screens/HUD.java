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
    private final Viewport viewport;

    private String hudPlayerName;          // Zeigt den Spielernamen
    private String hudTurnVal;              // Zeigt an, in welchem Status sich der Charakter gerade befindet, Movement, Diceroll usw.
    private Integer hudRemSteps;            // Zeigt an, wie viele Schritte der Charakter während seines Zuges noch gehen kann.
    private Integer hudRemFields;           // Zeigt an, wie weit der Weg bis zum Ziel noch ist.
    private Integer hudReduMvmtSpeedUntil;  // Zeigt an, wie lange man sich noch eingeschränkt fortbewegt, nachdem man eine Falle abbekommen hat.

    private final ShapeRenderer shapeRenderer;
    private final Label.LabelStyle labelStyle;

    private final Player player;
    private final TurnLogic turnLogic;

    private HudButton uncoverButton;
    private HudButton cheatButton;
    private HudButton diceButton;
    private final Color colorLightGreen;
    private final Color colorDarkGreen;
    private final Color colorWhite;
    private final Color colorRed;

    private int frameCounter;

    private TurnValue buttonStatus;

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
        this.hudPlayerName = this.player.getName();
        this.hudTurnVal = turnValTranslator(this.turnLogic.getPlayer().getTurnValue());
        this.hudRemSteps = this.player.getRemainingSteps();
        this.hudRemFields = this.player.getMaxRemainingFields();
        this.hudReduMvmtSpeedUntil = this.player.getCounterReducedMovementSpeed();

        this.stage = new Stage(this.viewport, batch);
        batch.setProjectionMatrix(this.stage.getCamera().combined);

        // top-bars
        float xCoordinate = Gdx.graphics.getWidth() / 3f;
        float yCoordinate = Gdx.graphics.getHeight() * 0.91f;
        float barLenght = Gdx.graphics.getWidth() * 0.28f;
        float barHeight = Gdx.graphics.getHeight() * 0.08f;
        float yCoordinateLowerBar = Gdx.graphics.getHeight() * 0.91f - Gdx.graphics.getHeight() * 0.09f;

        createTopBarElement(xCoordinate / 2 - barLenght / 2, yCoordinate, barLenght, barHeight, "Name: ", this.hudPlayerName, true, false);
        createTopBarElement(xCoordinate / 2 - barLenght / 2 + xCoordinate, yCoordinate, barLenght, barHeight, "", this.hudTurnVal, true, true);
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

    private String turnValTranslator(TurnValue turnValue){
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
                return "*klick* Nooo, I'm trapped...";
            }

        } else if (turnValue == TurnValue.TRAPCHECK){
            return "Huch?";
        } else if (turnValue == TurnValue.WON){
            return "YEEEEEAH :D";
        }

        return this.turnLogic.getPlayer().getTurnValue().toString();
    }

    private void createTopBarElement(float xCoordinate, float yCoordinate, float barLength, float barHeight, String labelDescription, String labelValue, boolean textCenter, boolean signal) {
        Table tableTopBarElement = new Table();
        tableTopBarElement.bottom();
        tableTopBarElement.setFillParent(true);

        float scaleFont = barHeight * 0.04f;

        Label currentElementLabel = new Label(labelDescription + labelValue, this.labelStyle);

        currentElementLabel.setFontScale(scaleFont);

        if (textCenter){
            currentElementLabel.setAlignment(Align.center);
            tableTopBarElement.add(currentElementLabel).width(barLength).height(barHeight);
            tableTopBarElement.setPosition(xCoordinate, yCoordinate);
            tableTopBarElement.left();
            tableTopBarElement.setFillParent(true);
        } else {
            tableTopBarElement.add(currentElementLabel).expandX().fillX();
            tableTopBarElement.setPosition(xCoordinate, yCoordinate + barHeight * 0.15f);
        }

        this.stage.addActor(tableTopBarElement);

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (signal && this.turnLogic.getPlayer().getTurnValue() == TurnValue.TRAPACTIVATED && this.frameCounter < 40) {
            this.shapeRenderer.setColor(this.colorRed);
        } else {
            this.shapeRenderer.setColor(this.colorWhite);
        }

        this.shapeRenderer.rect(xCoordinate, yCoordinate, barLength, barHeight);
        this.shapeRenderer.circle(xCoordinate, yCoordinate + barHeight/2, barHeight/2);
        this.shapeRenderer.circle(xCoordinate + barLength, yCoordinate + barHeight/2, barHeight/2);

        float elementEdge = 0.005f;
        float percentHeight = Gdx.graphics.getHeight() * elementEdge;

        if (signal && this.turnLogic.getPlayer().getTurnValue() == TurnValue.TRAPACTIVATED && this.frameCounter < 15) {
            this.shapeRenderer.setColor(this.colorRed);
        } else {
            this.shapeRenderer.setColor(this.colorWhite);
            if (this.frameCounter >= 80){
                this.frameCounter = 0;
            }
        }

        this.shapeRenderer.setColor(this.colorLightGreen);
        this.shapeRenderer.rect(xCoordinate, yCoordinate + percentHeight, barLength, barHeight - percentHeight*2);
        this.shapeRenderer.circle(xCoordinate, yCoordinate + barHeight/2, barHeight/2 - percentHeight);
        this.shapeRenderer.circle(xCoordinate + barLength, yCoordinate + barHeight/2, barHeight/2 - percentHeight);

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

        this.stage.addActor(tableSideBar);

        float elementEdge = 0.005f;
        float percentHeight = Gdx.graphics.getHeight() * elementEdge;

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(this.colorWhite);
        this.shapeRenderer.circle(xCoordinate, yCoordinate, radius);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate, radius);
        this.shapeRenderer.circle(xCoordinate, yCoordinate + barHeight, radius);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHeight, radius);
        this.shapeRenderer.rect(xCoordinate, yCoordinate - radius, barLenght, barHeight + radius * 2f);
        this.shapeRenderer.rect(xCoordinate - radius, yCoordinate, barLenght + radius * 2f, barHeight);

        this.shapeRenderer.setColor(this.colorLightGreen);
        this.shapeRenderer.circle(xCoordinate, yCoordinate, radius - percentHeight);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate, radius - percentHeight);
        this.shapeRenderer.circle(xCoordinate, yCoordinate + barHeight, radius - percentHeight);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHeight, radius - percentHeight);
        this.shapeRenderer.rect(xCoordinate, yCoordinate - radius + percentHeight, barLenght, barHeight + radius * 2f - percentHeight * 2f);
        this.shapeRenderer.rect(xCoordinate - radius + percentHeight, yCoordinate, barLenght + radius * 2f - percentHeight * 2f, barHeight);

        radius = radius * 0.5f;
        barHeight = barHeight / 4f;

        this.shapeRenderer.setColor(this.colorDarkGreen);
        this.shapeRenderer.circle(xCoordinate, yCoordinate, radius);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate, radius);
        this.shapeRenderer.circle(xCoordinate, yCoordinate + barHeight, radius);
        this.shapeRenderer.circle(xCoordinate + barLenght, yCoordinate + barHeight, radius);
        this.shapeRenderer.rect(xCoordinate, yCoordinate - radius, barLenght, barHeight + radius * 2f);
        this.shapeRenderer.rect(xCoordinate - radius, yCoordinate, barLenght + radius * 2f, barHeight);
        this.shapeRenderer.end();

        this.stage.addActor(this.uncoverButton.buttonCreation("Uncover", scaleFont, xCoordinate, yCoordinate + radius * 1.5f + barHeight * 2, barLenght, barHeight, percentHeight, TurnValue.DICEROLL, 1));
        this.stage.addActor(this.cheatButton.buttonCreation("Cheat", scaleFont, xCoordinate, yCoordinate + radius * 1.5f + barHeight, barLenght, barHeight, percentHeight, this.buttonStatus, this.player.getRemainingCheats()));
        this.stage.addActor(this.diceButton.buttonCreation("Dice", scaleFont, xCoordinate, yCoordinate + radius * 1.5f, barLenght, barHeight, percentHeight, TurnValue.DICEROLL, 1));

        this.turnLogic.setUncoverButton(this.uncoverButton);
        this.turnLogic.setCheatButton(this.cheatButton);
        this.turnLogic.setDiceButton(this.diceButton);
    }

    public String getHudPlayerName() {
        return this.hudPlayerName;
    }

    public void setHudPlayerName(String spielerName) {
        this.hudPlayerName = spielerName;
    }

    public String getHudTurnVal() {
        return this.hudTurnVal;
    }

    public void setHudTurnVal(String turnval) {
        this.hudTurnVal = turnval;
    }

    public Integer getHudRemSteps() {
        return this.hudRemSteps;
    }

    public void setHudRemSteps(Integer remSteps) {
        this.hudRemSteps = remSteps;
    }

    public Integer getHudRemFields() {
        return this.hudRemFields;
    }

    public void setHudRemFields(Integer hudRemFields) {
        this.hudRemFields = hudRemFields;
    }

    public Integer getHudReduMvmtSpeedUntil() {
        return this.hudReduMvmtSpeedUntil;
    }

    public void setHudReduMvmtSpeedUntil(Integer hudReduMvmtSpeedUntil) {
        this.hudReduMvmtSpeedUntil = hudReduMvmtSpeedUntil;
    }

    public HudButton getUncoverButton() {
        return this.uncoverButton;
    }

    public void setUncoverButton(HudButton uncoverButton) {
        this.uncoverButton = uncoverButton;
    }

    public HudButton getCheatButton() {
        return this.cheatButton;
    }

    public void setCheatButton(HudButton cheatButton) {
        this.cheatButton = cheatButton;
    }

    public HudButton getDiceButton() {
        return this.diceButton;
    }

    public void setDiceButton(HudButton diceButton) {
        this.diceButton = diceButton;
    }

    public void setButtonStatus(TurnValue buttonStatus) {
        this.buttonStatus = buttonStatus;
    }
}
