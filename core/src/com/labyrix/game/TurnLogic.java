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
import com.labyrix.game.NetworkModels.PlayerStatusRequest;
import com.labyrix.game.NetworkModels.PlayerWinIdRequest;

import java.util.ArrayList;

public class TurnLogic {

    private Board board;
    private Player player;
    private boolean turnDone;
    private Client client;
    private UncoverRender uncoverRender;
    private ArrowActors arrowActors;
    private TrapRender trapRender;
    int animationCounter = 20;
    private Texture dicerollImg;
    private ArrayList<Player> players = new ArrayList<Player>();
    private boolean sentDataToServer = false;
    private boolean playerCheated = false;
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
        this.uncoverRender = new UncoverRender(camera);
        arrowActors = new ArrowActors(camera);

        this.uncoverButton = new HudButton();
        this.cheatButton = new HudButton();
        this.diceButton = new HudButton();
        this.clicker = true;
    }

    /** calls different Methods according to boolean turnDone and ENUM player.turnValue
     * if turnDone is false - rollDice, move, selectPath, checkTrap, defuseTrap are called; or server gets notified that player won the game
     * if turnDone is true - a method for sending an data to server is called
     * @exception: IllegalArgumentException - in case turnValue is not known.
     */
    public void doTurn() throws IllegalArgumentException {
        if (this.turnDone == false) {
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
                    if (!sentDataToServer) {
                        client.sendTCP(new PlayerWinIdRequest(true));
                        sentDataToServer = true;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Wrong Turnvalue");
            }
        } else if (this.turnDone == true) {
            if (this.sentDataToServer == false) {
                doServerStuff();
            }
        }
    }

    /**
     * Implements Diceroll
     * with Click on diceButton a random number will be generated.
     * counter for reduced movement speed, and cheat recognitions tatus (hasCheated) will be decreased
     * according to the diced number, a image will be rendered for 120 rollDice calls
     * with Click on cheatButton - the movementspeed will be set back to 1, counterReducedMovementSpeed = 0, and hasCheated will be set on 2
     * with Click on  Uncover TODO
     * Switch to turnValue MOVEMENT
     * @exception: IllegalArgumentException - in case turnValue is not DICEROLL.
     */
    public void rollDice() throws IllegalArgumentException {
        if (this.player.turnValue == TurnValue.DICEROLL && turnDone == false) {
            if (this.player.getRemainingSteps() == 0) {
                animationCounter = 120;
            }
            if (Gdx.input.justTouched() && Gdx.input.getX() >= diceButton.getxCoordinateButtonBegin() && Gdx.input.getX() <= diceButton.getxCoordinateButtonEnd() && Gdx.input.getY() <= Gdx.graphics.getHeight() - diceButton.getyCoordinateButtonBegin() && Gdx.input.getY() >= Gdx.graphics.getHeight() - diceButton.getyCoordinateButtonEnd() && animationCounter == 120) {
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

                this.sentDataToServer = false;

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

            if (Gdx.input.justTouched() && Gdx.input.getX() >= cheatButton.getxCoordinateButtonBegin() && Gdx.input.getX() <= cheatButton.getxCoordinateButtonEnd() && Gdx.input.getY() <= Gdx.graphics.getHeight() - cheatButton.getyCoordinateButtonBegin() && Gdx.input.getY() >= Gdx.graphics.getHeight() - cheatButton.getyCoordinateButtonEnd() && this.player.getRemainingCheats() > 0 && clicker) {
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

            if (Gdx.input.justTouched() && Gdx.input.getX() >= uncoverButton.getxCoordinateButtonBegin() && Gdx.input.getX() <= uncoverButton.getxCoordinateButtonEnd() && Gdx.input.getY() <= Gdx.graphics.getHeight() - uncoverButton.getyCoordinateButtonBegin() && Gdx.input.getY() >= Gdx.graphics.getHeight() - uncoverButton.getyCoordinateButtonEnd()) {

                Uncover uncover = new Uncover(player,players, client);
                this.uncoverRender.setInputProcess();
                this.uncoverRender.addToStage(uncover.getTable());
                this.clicker = false;
                this.uncoverButton.setActive(false);
            }

        } else {
            throw new IllegalArgumentException("DiceRoll - Wrong TurnValue or Turn not done");

        }
    }

    /**
     * Implements Movement
     * Character moves every 20 call of this method as long as the remaining Steps are not zero
     * also check if Player is on the Winfield - switch turnValue to WON
     * if no remaining steps are left - switch turnValue to TRAPCHECK
     * if there are more than one following field - switch turnvalue to PATHSELECTION
     * @exception: IllegalArgumentException - in case turnValue is not MOVEMENT.
     */
    public void move() throws IllegalArgumentException {
        if (this.player.turnValue == TurnValue.MOVEMENT && turnDone == false) {

            this.clicker = true;
            this.cheatButton.setActive(true);
            this.uncoverRender.getStage().clear();
            this.uncoverButton.setActive(true);

            if (this.player.getCurrentField().isWinField() == true) {
                this.player.turnValue = TurnValue.WON;
            }

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
            throw new IllegalArgumentException("Move - Wrong TurnValue or Turn not done");

        }
    }

    /**
     * Implements Pathselection
     * if the character has remaining steps > 0 and there are more than one following field
     * - sets arrowactors for every possible following field
     * - player can choose which way he wants to go
     * after choosing - switch turnvalue to MOVE
     * @exception: IllegalArgumentException - in case turnValue is not SELECTPATH.
     */
    public void selectPath() throws IllegalArgumentException {
        if (this.player.turnValue == TurnValue.PATHSELECTION && turnDone == false) {
            //Show arrows for PathSelection - selection of path in arrowActor Eventlistener
            if (this.player.getRemainingSteps() > 0) {
                int i = 0;
                this.arrowActors.setInputProcess();
                for (PathField pf : this.player.getCurrentField().getFollowingFields()) {
                    //Arrow Spawn for all 4 possible followingFields
                    if (this.player.getCurrentField().getCoordinates().x < pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y < pf.getCoordinates().y) {
                        ArrowActor actorUp = new ArrowActor("arrowNewUp.png", pf.getCoordinates().x - Gdx.graphics.getWidth() / 8f, pf.getCoordinates().y - Gdx.graphics.getHeight() / 8f, this, i);

                        this.arrowActors.setArrowActorUp(actorUp);
                        this.arrowActors.getStage().addActor(actorUp);

                    }
                    if (this.player.getCurrentField().getCoordinates().x > pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y < pf.getCoordinates().y) {
                        ArrowActor actorLeft = new ArrowActor("arrowNewLeft.png", pf.getCoordinates().x - Gdx.graphics.getWidth() / 8f, pf.getCoordinates().y - Gdx.graphics.getHeight() / 8f, this, i);
                        this.arrowActors.setArrowActorLeft(actorLeft);
                        this.arrowActors.getStage().addActor(actorLeft);
                    }
                    if (this.player.getCurrentField().getCoordinates().x > pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y > pf.getCoordinates().y) {
                        ArrowActor actorDown = new ArrowActor("arrowNewDown.png", pf.getCoordinates().x - Gdx.graphics.getWidth() / 8f, pf.getCoordinates().y - Gdx.graphics.getHeight() / 8f, this, i);
                        this.arrowActors.setArrowActorDown(actorDown);
                        this.arrowActors.getStage().addActor(actorDown);
                    }
                    if (this.player.getCurrentField().getCoordinates().x < pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y > pf.getCoordinates().y) {
                        ArrowActor actorRight = new ArrowActor("arrowNewRight.png", pf.getCoordinates().x - Gdx.graphics.getWidth() / 8f, pf.getCoordinates().y - Gdx.graphics.getHeight() / 8f, this, i);
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
            throw new IllegalArgumentException("PathSelection - Wrong TurnValue or Turn not done");
        }
    }

    /**
     * Implements TrapCheck
     * Checks, if the trap on the current field of the player gets activated or not.
     * if no - switch turnValue to MOVEMENT and turndone to true
     * if yes - switch turnValue to TRAPACTIVATED and turndone to true
     * @exception: IllegalArgumentException - in case turnValue is not TRAPCHECK.
     */
    public void checkTrap() throws IllegalArgumentException {
        if (this.player.turnValue == TurnValue.TRAPCHECK && turnDone == false) {
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
            throw new IllegalArgumentException("checkTrap - Wrong TurnValue or Turn not done");
        }
    }

    /**
     * Implements TrapDefusion
     * @exception: IllegalArgumentException - in case turnValue is not TRAPACTIVATED.
     */
    public void defuseTrap() throws IllegalArgumentException {
        if (this.player.turnValue == TurnValue.TRAPACTIVATED && turnDone == false) {
            this.sentDataToServer = false;
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
            throw new IllegalArgumentException("Trapdefuse - Wrong TurnValue or Turn not done");
        }
    }


    /**
     * Implements sending data to server
     * Checks, if the turn is done and then sends the data plus sets check if data is sent on true
     * @exception: IllegalArgumentException - in case turn is not done or sentDataToServer is true.
     */
    public void doServerStuff() throws IllegalArgumentException {
        if (this.turnDone == true && !sentDataToServer) {

            NetworkPlayer np = new NetworkPlayer(this.player.getId(), this.player.getLobbyId(), this.player.getPosition(), this.player.getMaxRemainingFields(), this.player.getMinRemainingFields());

            client.sendTCP(new PlayerStatusRequest(np));
            sentDataToServer = true;

        } else {
            throw new IllegalArgumentException("Turn not Done or data already sent to server");
        }
    }


    /**
     * Wird automatisch vom aufgerufen, wenn Daten vom Server an Game geschickt werden
     * Spieler werden anhand der networkplayers upgedated und turnDone auf false gesetzt
     * @param networkplayers - ArrayListe aller Speiler
     */
    public void playerReturnServer(ArrayList<NetworkPlayer> networkplayers){

        if (this.turnDone == true) {

            for (NetworkPlayer np: networkplayers) {
                if (np.getId() != this.player.getId()) {

                    int playerindex = getPlayerIndexById(np.getId());
                    if (playerindex != -1) {
                        this.players.get(playerindex).setPosition(np.getPosition());
                        this.players.get(playerindex).setMaxRemainingFields(np.getMaxRemainingFields());
                        this.players.get(playerindex).setMinRemainingFields(np.getMinRemainingFields());
                    }
                }
            }
            this.turnDone = false;
        }
    }

    /**
     * gibt Index des Players mit der übergebenen ID zurück
     * @return pid - index des players falls gefunden, sonst -1
     */
    public int getPlayerIndexById(int id) {
        int pid = -1;
        for (Player pl : players) {
            pid++;
            if (pl.getId() == id) {
                break;
            }
        }
        return pid;
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
        } else {
            throw new RuntimeException("Already three Players!");
        }

    }

    public boolean isPlayerCheated() {
        return playerCheated;
    }

    public void setPlayerCheated(boolean playerCheated) {
        this.playerCheated = playerCheated;
    }

    public UncoverRender getUncoverRender() {
        return uncoverRender;
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
