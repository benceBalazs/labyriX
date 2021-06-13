package com.labyrix.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryonet.Client;
import com.labyrix.game.Defusions.BombDefuse;
import com.labyrix.game.Defusions.MovementDefuse;
import com.labyrix.game.Defusions.TrapRender;
import com.labyrix.game.ENUMS.TrapEventName;
import com.labyrix.game.ENUMS.TurnValue;
import com.labyrix.game.Models.*;
import com.labyrix.game.Network.ClientNetworkHandler;

import java.util.ArrayList;

public class TurnLogic {

    private Board board;
    private Player player;
    private boolean turnDone;
    private Client client;
    private ArrowActors arrowActors;
    private TrapRender trapRender;
    int animationCounter = 20;
    private Texture turnValueText;
    private Texture dicerollImg;
    private ArrayList<Player> players = new ArrayList<Player>();
    private boolean defuseCompleted = false;
    private boolean defuseSuccess = false;

    private HudButton uncoverButton;
    private HudButton cheatButton;
    private HudButton diceButton;
    private boolean clicker;

    public TurnLogic(Board board, Player player, Camera camera) {
        this.board = board;
        this.player = player;
        this.client = ClientNetworkHandler.getInstance().getClient();
        this.turnDone = false;
        this.player.turnValue = TurnValue.DICEROLL;
        this.trapRender = new TrapRender(camera);
        arrowActors = new ArrowActors(camera);
        turnValueText = new Texture("rollDice.png");

        this.uncoverButton = new HudButton();
        this.cheatButton = new HudButton();
        this.diceButton = new HudButton();
        this.clicker = true;
    }

    public void doTurn() throws IllegalArgumentException {
        board.getBatch().draw(turnValueText, this.player.getPosition().x - Gdx.graphics.getWidth() / 8f, this.player.getPosition().y);

        if (this.turnDone == false) {
            System.out.println(this.player.turnValue);
            switch (this.player.turnValue) {
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
    }

    public void rollDice() throws IllegalArgumentException {
        if (this.player.turnValue == TurnValue.DICEROLL && turnDone == false) {
            if (this.player.getRemainingSteps() == 0) {
                animationCounter = 120;
            }
            if (Gdx.input.getX() >= diceButton.getxCoordinateButtonBegin() && Gdx.input.getX() <= diceButton.getxCoordinateButtonEnd() && Gdx.input.getY() <= Gdx.graphics.getHeight() - diceButton.getyCoordinateButtonBegin() && Gdx.input.getY() >= Gdx.graphics.getHeight() - diceButton.getyCoordinateButtonEnd() && animationCounter == 120) {
                int steps = (int) (((Math.random() * 10) % 5 + 1) * player.getMovementSpeed());
                this.player.setRemainingSteps(steps);

                if (this.player.getCounterReducedMovementSpeed() > 0) {
                    this.player.setCounterReducedMovementSpeed(this.player.getCounterReducedMovementSpeed() - 1);
                }
                if (this.player.getCounterReducedMovementSpeed() == 0) {
                    this.player.setMovementSpeed(1);
                }
                if (this.player.getHasCheated() > 0) {
                    this.player.setHasCheated(this.player.getHasCheated()-1);
                }

                this.animationCounter = 120;
                switch (steps) {
                    case 1:
                        this.dicerollImg = new Texture("diceOne.png");
                        break;
                    case 2:
                        this.dicerollImg = new Texture("diceTwo.png");
                        break;
                    case 3:
                        this.dicerollImg = new Texture("diceThree.png");
                        break;
                    case 4:
                        this.dicerollImg = new Texture("diceFour.png");
                        break;
                    case 5:
                        this.dicerollImg = new Texture("diceFive.png");
                        break;
                    case 6:
                        this.dicerollImg = new Texture("diceSix.png");
                        break;
                    default:
                        break;
                }
            }

            if (Gdx.input.getX() >= cheatButton.getxCoordinateButtonBegin() && Gdx.input.getX() <= cheatButton.getxCoordinateButtonEnd() && Gdx.input.getY() <= Gdx.graphics.getHeight() - cheatButton.getyCoordinateButtonBegin() && Gdx.input.getY() >= Gdx.graphics.getHeight() - cheatButton.getyCoordinateButtonEnd() && this.player.getRemainingCheats() > 0 && clicker) {
                this.player.setMovementSpeed(1);
                this.player.setCounterReducedMovementSpeed(0);
                this.player.setRemainingCheats(this.player.getRemainingCheats()-1);
                this.clicker = false;
                this.cheatButton.setActive(false);
            }

            if (this.animationCounter > 0 && this.player.getRemainingSteps() > 0) {
                this.animationCounter--;
                board.getBatch().draw(dicerollImg, this.player.getPosition().x - Gdx.graphics.getWidth() / 2f, this.player.getPosition().y - Gdx.graphics.getWidth() / 4f);
            }

            if (this.animationCounter == 0 && this.player.getRemainingSteps() > 0) {
                this.animationCounter = 20;
                this.player.turnValue = TurnValue.MOVEMENT;
                this.dicerollImg = null;
            }

            if (Gdx.input.getX() >= uncoverButton.getxCoordinateButtonBegin() && Gdx.input.getX() <= uncoverButton.getxCoordinateButtonEnd() && Gdx.input.getY() <= Gdx.graphics.getHeight() - uncoverButton.getyCoordinateButtonBegin() && Gdx.input.getY() >= Gdx.graphics.getHeight() - uncoverButton.getyCoordinateButtonEnd()) {
            //Logic for Uncover Cheat
            }

        } else {
            throw new IllegalArgumentException();
        }
    }

    public void move() throws IllegalArgumentException {
        if (this.player.turnValue == TurnValue.MOVEMENT && turnDone == false) {
            this.clicker = true;
            this.cheatButton.setActive(true);

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
                this.player.turnValue = TurnValue.TRAPCHECK;
            }

            if (this.player.getCurrentField().getFollowingFields().size() == 1) {
                if (animationCounter == 0) {
                    this.player.setCurrentField(player.getCurrentField().getFollowingField(0));
                    this.player.setRemainingSteps(this.player.getRemainingSteps() - 1);
                    Vector2 playerPosition = new Vector2(player.getCurrentField().getCoordinates().x + 64, player.getCurrentField().getCoordinates().y + 184);
                    this.player.setPosition(playerPosition);
                    animationCounter = 20;
                    if (this.player.getCurrentField().getFieldImage().getImg().equals(new Texture("bodenLabyrixZiel.png"))) {
                        this.player.turnValue = TurnValue.WON;
                    }
                }
                animationCounter--;
            }
            if (this.player.getCurrentField().getFollowingFields().size() > 1 && this.player.getRemainingSteps() > 0) {
                this.player.turnValue = TurnValue.PATHSELECTION;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void selectPath() throws IllegalArgumentException {
        if (this.player.turnValue == TurnValue.PATHSELECTION && turnDone == false) {
            //Show arrows for PathSelection - selection of path in arrowActor Eventlistener
            if (this.player.getRemainingSteps() > 0) {
                turnValueText = new Texture("selectPath.png");
                System.out.println("Remaining Steps: " + this.player.getRemainingSteps());
                int i = 0;
                this.arrowActors.setInputProcess();
                for (PathField pf : this.player.getCurrentField().getFollowingFields()) {
                    //Arrow Spawn for all 4 possible followingFields
                    if (this.player.getCurrentField().getCoordinates().x < pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y < pf.getCoordinates().y) {
                        ArrowActor actorUp = new ArrowActor("arrowNewUp.png", pf.getCoordinates().x - Gdx.graphics.getWidth() / 8f, pf.getCoordinates().y - Gdx.graphics.getHeight() / 8f, "ArrowUp", this, i);

                        this.arrowActors.setArrowActorUp(actorUp);
                        this.arrowActors.getStage().addActor(actorUp);

                    }
                    if (this.player.getCurrentField().getCoordinates().x > pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y < pf.getCoordinates().y) {
                        ArrowActor actorLeft = new ArrowActor("arrowNewLeft.png", pf.getCoordinates().x - Gdx.graphics.getWidth() / 8f, pf.getCoordinates().y - Gdx.graphics.getHeight() / 8f, "ArrowLeft", this, i);
                        this.arrowActors.setArrowActorLeft(actorLeft);
                        this.arrowActors.getStage().addActor(actorLeft);
                    }
                    if (this.player.getCurrentField().getCoordinates().x > pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y > pf.getCoordinates().y) {
                        ArrowActor actorDown = new ArrowActor("arrowNewDown.png", pf.getCoordinates().x - Gdx.graphics.getWidth() / 8f, pf.getCoordinates().y - Gdx.graphics.getHeight() / 8f, "ArrowDown", this, i);
                        this.arrowActors.setArrowActorDown(actorDown);
                        this.arrowActors.getStage().addActor(actorDown);
                    }
                    if (this.player.getCurrentField().getCoordinates().x < pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y > pf.getCoordinates().y) {
                        ArrowActor actorRight = new ArrowActor("arrowNewRight.png", pf.getCoordinates().x - Gdx.graphics.getWidth() / 8f, pf.getCoordinates().y - Gdx.graphics.getHeight() / 8f, "ArrowRight", this, i);
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
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void checkTrap() throws IllegalArgumentException {
        if (this.player.turnValue == TurnValue.TRAPCHECK && turnDone == false) {
            turnValueText = new Texture("checkTrap.png");
            if (this.player.getCurrentField().getTrap().isTrapActivated() == true) {

                this.player.turnValue = TurnValue.TRAPACTIVATED;
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
                this.animationCounter = 120;
            } else {
                this.player.turnValue = TurnValue.DICEROLL;
            }
            this.turnDone = true;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void defuseTrap() throws IllegalArgumentException {
        if (this.player.turnValue == TurnValue.TRAPACTIVATED && turnDone == false) {
            if (this.animationCounter != 0 && this.trapRender.getMovementDefuse() == null && this.trapRender.getBombDefuse() == null) {
                turnValueText = new Texture("trapActive.png");
                System.out.println(this.player.getCurrentField().getTrap().getEvent().getEvent());
                int x = (int) this.player.getCurrentField().getTrap().getEvent().getEventImage().getCoordinates().x - (int) (Gdx.graphics.getWidth() / 8f);
                int y = (int) this.player.getCurrentField().getTrap().getEvent().getEventImage().getCoordinates().y - (int) (Gdx.graphics.getHeight() / 8f);
                Texture trapImg = this.player.getCurrentField().getTrap().getEvent().getEventImage().getImg();
                board.drawImg(trapImg, x, y);

                animationCounter--;
            } else {
                try {
                    if (this.player.getCurrentField().getTrap().getEvent().getEvent() == TrapEventName.BOMB) {
                        this.trapRender.setInputProcess();

                        if (this.trapRender.getBombDefuse() == null) {
                            BombDefuse defuse = new BombDefuse(this.player.getCurrentField().getCoordinates().x, this.player.getCurrentField().getCoordinates().y);
                            this.trapRender.setBombDefuse(defuse);
                            this.trapRender.addToStage(this.trapRender.getBombDefuse().getTable());

                            Timer timer = new Timer();
                            timer.scheduleTask(new Timer.Task() {
                                int count = 0;
                                boolean bombrender = true;
                                @Override
                                public void run() {
                                    if (bombrender){
                                        if (trapRender.getBombDefuse().bombResult()){
                                            trapRender.getStage().clear();
                                            bombrender = false;
                                            animationCounter = 120;
                                        }
                                        else if (count == 5){
                                            trapRender.getStage().clear();
                                            bombrender = false;
                                            animationCounter = 120;
                                        }
                                        count++;
                                    }
                                }
                            }, 1,1,7);
                        }

                        if (this.trapRender.getStage().getActors().isEmpty() == true){

                            if (animationCounter!= 0){
                                showTrapResult(trapRender.getBombDefuse().bombResult());
                            }else {
                                if (trapRender.getBombDefuse().bombResult() == true) {
                                    trapRender.setBombDefuse(null);
                                    this.player.turnValue = TurnValue.DICEROLL;
                                } else {
                                    trapRender.setBombDefuse(null);
                                    if (this.player.getNumberOfFails() < 2) {
                                        this.player.setNumberOfFails(this.player.getNumberOfFails() + 1);
                                        this.player.setMovementSpeed((float) (this.player.getMovementSpeed() * 0.75));
                                        this.animationCounter = 120;
                                    }
                                    else {
                                        this.player.setCounterReducedMovementSpeed(4);
                                        this.player.setNumberOfFails(0);
                                        this.player.turnValue = TurnValue.DICEROLL;
                                    }
                                }
                                this.turnDone = true;
                            }
                        }
                    } else {
                        if (this.trapRender.getMovementDefuse() == null){
                            MovementDefuse movementDefuse1 = new MovementDefuse(this.player.getCurrentField().getCoordinates().x,this.player.getCurrentField().getCoordinates().y, this.player.getCurrentField().getTrap().getEvent().getEvent());
                            this.trapRender.setMovementDefuse(movementDefuse1);
                            this.trapRender.addToStage(this.trapRender.getMovementDefuse().getTable());
                        } else {
                            if (!defuseCompleted){
                                if (getMovementTrapOutcome()) {
                                    animationCounter = 120;
                                    defuseSuccess = true;
                                } else {
                                    if (this.player.getNumberOfFails() < 2) {
                                        this.player.setNumberOfFails(this.player.getNumberOfFails() + 1);
                                        this.player.setMovementSpeed((float) (this.player.getMovementSpeed() * 0.75));
                                        this.animationCounter = 120;
                                    }
                                    else {
                                        this.player.setCounterReducedMovementSpeed(4);
                                        this.player.setNumberOfFails(0);
                                    }
                                    defuseSuccess = false;
                                }
                                this.trapRender.getStage().clear();
                                defuseCompleted = true;
                            }

                            if (this.trapRender.getStage().getActors().isEmpty()) {
                                if (animationCounter!= 0){
                                    showTrapResult(defuseSuccess);
                                }else {
                                    if (defuseSuccess || this.player.getNumberOfFails() == 2){
                                        this.player.turnValue = TurnValue.DICEROLL;
                                    }
                                    defuseCompleted = false;
                                    animationCounter = 120;
                                    trapRender.setMovementDefuse(null);
                                    this.turnDone = true;
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
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
            // TODO send with "client.sendTCP(new PlayerStatusRequest())" a player status to the server and wait for playerReturnServer() for all player statuses
            if (Gdx.input.justTouched()) {
                System.out.println("server has done its stuff");
                for (Player p: this.players) {
                    for (int i = 0; ((Math.random()*10) % 6) + 1 > i ; i++) {
                        if (p.getCurrentField().getFollowingFields().size() > 0) {
                            p.setCurrentField(p.getCurrentField().getFollowingField(0));
                        }
                    }
                    Vector2 playerPosition = new Vector2(p.getCurrentField().getCoordinates().x + 64, p.getCurrentField().getCoordinates().y + 184);
                    p.setPosition(playerPosition);
                }
                this.turnDone = false;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void showTrapResult(boolean succ){
        if (succ){
            Texture success = new Texture("success.png");
            board.getBatch().draw(success, this.player.getPosition().x - Gdx.graphics.getWidth() / 2f, this.player.getPosition().y - Gdx.graphics.getWidth() / 4f);
        }else{
            Texture fail = new Texture("fail.png");
            board.getBatch().draw(fail, this.player.getPosition().x - Gdx.graphics.getWidth() / 2f, this.player.getPosition().y - Gdx.graphics.getWidth() / 4f);
        }
        animationCounter--;
    }

    public boolean getMovementTrapOutcome() throws InterruptedException{
        if (this.player.getCurrentField().getTrap().getEvent().getEvent() == TrapEventName.ZOMBIE){
            return this.trapRender.getMovementDefuse().dontMove();
        }
        else if (this.player.getCurrentField().getTrap().getEvent().getEvent() == TrapEventName.QUICKSAND){
            return this.trapRender.getMovementDefuse().crawlOut();
        }
        else if (this.player.getCurrentField().getTrap().getEvent().getEvent() == TrapEventName.DOOR){
            return this.trapRender.getMovementDefuse().climbUp();
        }
        return false;
    }
      
    public void playerReturnServer(ArrayList<NetworkPlayer> networkplayers){
        /* TODO networkplayer to normal player einbinden | Unterschied nur String statt Image
            Methode wird automatisch aufgerufen nachdem jeder seinen Zug gemacht hat
         */
    }

    public ArrowActors getArrowActors() {
        return arrowActors;
    }

    public Player getPlayer() {
        return player;
    }

    public TrapRender getTrapRender() {
        return trapRender;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (this.players.size() < 3) {
            this.players.add(player);
        }
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
