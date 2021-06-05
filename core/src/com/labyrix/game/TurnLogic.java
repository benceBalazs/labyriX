package com.labyrix.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.kryonet.Client;
import com.labyrix.game.Defusions.BombDefuse;
import com.labyrix.game.Defusions.BombRender;
import com.labyrix.game.ENUMS.TrapEventName;
import com.labyrix.game.ENUMS.TurnValue;
import com.labyrix.game.Models.*;
import com.labyrix.game.Network.ClientNetworkHandler;

public class TurnLogic {

    private Board board;
    private Player player;
    private boolean turnDone;
    private TurnValue turnValue;
    private Client client;
    private ArrowActors arrowActors;
    private BombRender bombRender;
    int animationCounter = 20;
    private Texture turnValueText;
    HudButton uncoverButton;
    HudButton cheatButton;
    HudButton diceButton;

    public TurnLogic(Board board, Player player, Camera camera) {
        this.board = board;
        this.player = player;
        this.client = ClientNetworkHandler.getInstance().getClient();
        this.turnDone = false;
        this.turnValue = TurnValue.DICEROLL;
        this.bombRender = new BombRender(camera);
        arrowActors = new ArrowActors(camera);
        turnValueText = new Texture("rollDice.png");
        uncoverButton = new HudButton();
        cheatButton = new HudButton();
        diceButton = new HudButton();
    }

    public void doTurn() throws IllegalArgumentException {
        if (this.turnDone == false) {
            System.out.println(this.turnValue);

            switch (this.turnValue) {
                case DICEROLL:
                    rollDice();
                    break;
                case MOVEMENT:
                    move();
                    break;
                case PATHSELECTION:
                    selectPath();
                    break;
                case TRAPCHECK:
                    checkTrap();
                    break;
                case TRAPACTIVATED:
                    defuseTrap();
                    break;
                case WON:
                    System.out.println("WON");
                    //SOME FANCY SERVER STUFF
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } else if (this.turnDone == true) {
            doServerStuff();
        }

        board.getBatch().draw(turnValueText, this.player.getPosition().x, this.player.getPosition().y + 180);
    }

    public void rollDice() throws IllegalArgumentException {
        if (this.turnValue == TurnValue.DICEROLL && turnDone == false) {
            if (Gdx.input.getX() >= diceButton.getxCoordinateButtonBegin() && Gdx.input.getX() <= diceButton.getxCoordinateButtonEnd() && Gdx.input.getY() <= Gdx.graphics.getHeight() - diceButton.getyCoordinateButtonBegin() && Gdx.input.getY() >= Gdx.graphics.getHeight() - diceButton.getyCoordinateButtonEnd()) {       // man muss links oben klicken, damit es geht, ich weiß aber nicht warum.
                this.player.setRemainingSteps((int) (((Math.random() * 10) % 5 + 1) * player.getMovementSpeed()));
                this.turnValue = TurnValue.MOVEMENT;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void move() throws IllegalArgumentException {
        if (this.turnValue == TurnValue.MOVEMENT && turnDone == false) {
            if (this.getArrowActors().getArrowActorDown() != null) {
                this.getArrowActors().setArrowActorDown(null);
            }
            if (this.getArrowActors().getArrowActorLeft() != null) {
                this.getArrowActors().setArrowActorLeft(null);
            }
            if (this.getArrowActors().getArrowActorRight() != null) {
                this.getArrowActors().setArrowActorRight(null);
            }
            if (this.getArrowActors().getArrowActorUp() != null) {
                this.getArrowActors().setArrowActorUp(null);
            }

            turnValueText = new Texture("move.png");
            System.out.println("Remaining Steps: " + this.player.getRemainingSteps());
            System.out.println("Following Fields: " + this.player.getCurrentField().getFollowingFields().size());


            if (this.player.getRemainingSteps() <= 0) {
                this.turnValue = TurnValue.TRAPCHECK;
            }

            if (this.player.getCurrentField().getFollowingFields().size() == 1) {
                if (animationCounter == 0) {
                    this.player.setCurrentField(player.getCurrentField().getFollowingField(0));
                    this.player.setRemainingSteps(this.player.getRemainingSteps() - 1);
                    Vector2 playerPosition = new Vector2(player.getCurrentField().getCoordinates().x + 64, player.getCurrentField().getCoordinates().y + 184);
                    this.player.setPosition(playerPosition);
                    animationCounter = 20;
                    if (this.player.getCurrentField().getFieldImage().getImg().equals(new Texture("bodenLabyrixZiel.png"))) {
                        this.turnValue = TurnValue.WON;
                    }
                }
                animationCounter--;
            }
            if (this.player.getCurrentField().getFollowingFields().size() > 1 && this.player.getRemainingSteps() > 0) {
                this.turnValue = TurnValue.PATHSELECTION;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }


    public void selectPath() throws IllegalArgumentException {
        if (this.turnValue == TurnValue.PATHSELECTION && turnDone == false) {
            //Show arrows for PathSelection - selection of path in arrowActor Eventlistener
            if (this.player.getRemainingSteps() > 0) {
                turnValueText = new Texture("selectPath.png");
                System.out.println("Remaining Steps: " + this.player.getRemainingSteps());
                if (this.arrowActors.getArrowActorLeft() == null && this.arrowActors.getArrowActorRight() == null && this.arrowActors.getArrowActorUp() == null && this.arrowActors.getArrowActorDown() == null) {
                    int i = 0;
                    this.arrowActors.setInputProcess();
                    for (PathField pf : this.player.getCurrentField().getFollowingFields()) {
                        //Arrow Spawn for all 4 possible followingFields
                        if (this.player.getCurrentField().getCoordinates().x < pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y < pf.getCoordinates().y) {
                            ArrowActor actorUp = new ArrowActor("arrowNewUp.png", pf.getCoordinates().x - Gdx.graphics.getWidth()/8f, pf.getCoordinates().y - Gdx.graphics.getHeight()/8f, "ArrowUp", this, i);
                            this.arrowActors.setArrowActorUp(actorUp);
                            this.arrowActors.getStage().addActor(actorUp);
                        }
                        if (this.player.getCurrentField().getCoordinates().x > pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y < pf.getCoordinates().y) {
                            ArrowActor actorLeft = new ArrowActor("arrowNewLeft.png", pf.getCoordinates().x - Gdx.graphics.getWidth()/8f, pf.getCoordinates().y - Gdx.graphics.getHeight()/8f, "ArrowLeft", this, i);
                            this.arrowActors.setArrowActorLeft(actorLeft);
                            this.arrowActors.getStage().addActor(actorLeft);
                        }
                        if (this.player.getCurrentField().getCoordinates().x > pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y > pf.getCoordinates().y) {
                            ArrowActor actorDown = new ArrowActor("arrowNewDown.png", pf.getCoordinates().x - Gdx.graphics.getWidth()/8f, pf.getCoordinates().y - Gdx.graphics.getHeight()/8f, "ArrowDown", this, i);
                            this.arrowActors.setArrowActorDown(actorDown);
                            this.arrowActors.getStage().addActor(actorDown);
                        }
                        if (this.player.getCurrentField().getCoordinates().x < pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y > pf.getCoordinates().y) {
                            ArrowActor actorRight = new ArrowActor("arrowNewRight.png", pf.getCoordinates().x - Gdx.graphics.getWidth()/8f, pf.getCoordinates().y - Gdx.graphics.getHeight()/8f, "ArrowRight", this, i);
                            this.arrowActors.setArrowActorRight(actorRight);
                            this.arrowActors.getStage().addActor(actorRight);
                        }
                        i++;
                        if (this.getArrowActors().isIsHidden()) {
                            this.arrowActors.render();
                            this.getArrowActors().setIsHidden(false);
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void checkTrap() throws IllegalArgumentException {
        if (this.turnValue == TurnValue.TRAPCHECK && turnDone == false) {
            turnValueText = new Texture("checkTrap.png");
            if (this.player.getCurrentField().getTrap().isTrapActivated() == true) {

                this.turnValue = TurnValue.TRAPACTIVATED;
                int x, y;
                if (this.player.getCurrentField().getTrap().getEvent().getEvent() != TrapEventName.ZOMBIE) {
                    x = (int) this.player.getCurrentField().getCoordinates().x;
                    y = (int) this.player.getCurrentField().getCoordinates().y;
                } else {
                    x = (int) this.player.getCurrentField().getFollowingField(0).getCoordinates().x + 10;
                    y = (int) this.player.getCurrentField().getFollowingField(0).getCoordinates().y + 10;
                }

                Vector2 trapCoordinates = new Vector2(x, y);
                this.player.getCurrentField().getTrap().getEvent().getEventImage().setCoordinates(trapCoordinates);
            } else {
                this.turnValue = TurnValue.DICEROLL;
            }
            this.turnDone = true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void defuseTrap() throws IllegalArgumentException {
        if (this.turnValue == TurnValue.TRAPACTIVATED && turnDone == false) {
            turnValueText = new Texture("trapActive.png");
            System.out.println(this.player.getCurrentField().getTrap().getEvent().getEvent());
            int x = (int) this.player.getCurrentField().getTrap().getEvent().getEventImage().getCoordinates().x;
            int y = (int) this.player.getCurrentField().getTrap().getEvent().getEventImage().getCoordinates().y;
            Texture trapImg = this.player.getCurrentField().getTrap().getEvent().getEventImage().getImg();
            board.drawImg(trapImg, x, y);

            if (Gdx.input.justTouched()) {

                try {
                    if (this.player.getCurrentField().getTrap().getEvent().getEvent() == TrapEventName.BOMB){
                        this.bombRender.setInputProcess();

                        if (this.bombRender.getBombDefuse() == null){
                            BombDefuse defuse = new BombDefuse(this.player.getCurrentField().getCoordinates().x,this.player.getCurrentField().getCoordinates().y);
                            this.bombRender.setBombDefuse(defuse);
                            this.bombRender.addToStage(this.bombRender.getBombDefuse().getTable());

                            Timer timer = new Timer();
                            timer.scheduleTask(new Timer.Task(){
                                @Override
                                public void run() {
                                    bombRender.getStage().clear();
                                }
                            }, 6);
                        }

                        if (this.bombRender.getStage().getActors().isEmpty() == true){
                            if (bombRender.getBombDefuse().bombResult() == true){
                                System.out.println("Success!!!!!!!!!!");
                                bombRender.setBombDefuse(null);
                                this.turnValue = TurnValue.DICEROLL;
                            }
                            else if (this.player.getNumberOfFails() < 1){
                                System.out.println("You lose!!!!!!!!");
                                this.player.setNumberOfFails(this.player.getNumberOfFails() + 1);
                                bombRender.setBombDefuse(null);
                            }
                            else {
                                bombRender.setBombDefuse(null);
                                this.turnValue = TurnValue.DICEROLL;
                            }
                            this.turnDone = true;
                        }
                    }
                    else {
                        if (this.player.getCurrentField().getTrap().getEvent().TrapDefuse() == true) {
                            this.turnValue = TurnValue.DICEROLL;
                        }
                        else if (this.player.getNumberOfFails() < 1){
                            this.player.setNumberOfFails(this.player.getNumberOfFails() + 1);
                            //TODO wait for one turn or try again
                        /*
                        this.player.setNumberOfFails(this.player.getNumberOfFails() + 1);
                        this.turnValue = TurnValue.Wait/.RECOVER;
                        this.turnDone = true;
                         */
                        }
                        else {
                            this.turnValue = TurnValue.DICEROLL;
                        }
                        this.turnDone = true;
                    }

                }catch (InterruptedException e){
                    System.out.println("Trapdefuse was Interrupted!");
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void doServerStuff() throws IllegalArgumentException {
        if (this.turnDone == true) {
            //SEND STUFF TO SERVER
            //RECEIVE UPDATES FROM SERVER
            this.turnValueText = new Texture("serverstuff.png");
            System.out.println("Server communication beep boop boop beep - Server returned voll cool ey");

            if (Gdx.input.justTouched()) {
                System.out.println("server has done its stuff");
                this.turnDone = false;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
    public ArrowActors getArrowActors() {
        return arrowActors;
    }

    public Player getPlayer() {
        return player;
    }

    public TurnValue getTurnValue() {
        return turnValue;
    }

    public void setTurnValue(TurnValue turnValue) {
        this.turnValue = turnValue;
    }

    public BombRender getBombRender() {
        return bombRender;
    }

    public void setUncoverButton(HudButton uncoverButton) {
        this.uncoverButton = uncoverButton;
    }

    public void setCheatButton(HudButton cheatButton) {
        this.cheatButton = cheatButton;
    }

    public void setDiceButton(HudButton diceButton) {
        this.diceButton = diceButton;
    }
}
