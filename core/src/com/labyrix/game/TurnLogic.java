package com.labyrix.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.kryonet.Client;

import javax.management.Notification;

public class TurnLogic {

    private Board board;
    private Player player;
    private boolean turnDone;
    private TurnValue turnValue;
    private Client client;
    private ArrowActors arrowActors;
    int animationCounter = 20;
    private Texture turnValueText;


    public TurnLogic(Board board, Player player, Camera camera) {
        this.board = board;
        this.player = player;
        this.client = ClientNetworkHandler.getInstance().getClient();
        this.turnDone = false;
        this.turnValue = TurnValue.DICEROLL;
        arrowActors = new ArrowActors(camera);
        turnValueText = new Texture("rollDice.png");
    }

    public void doTurn() {
        System.out.println(this.turnValue);
        turnValueText = new Texture("rollDice.png");
       if (this.turnValue == TurnValue.DICEROLL) {

           if (Gdx.input.justTouched()) {
               this.player.setRemainingSteps((int) (((Math.random() * 10)%5+1) * player.getMovementSpeed()));
               this.turnValue = TurnValue.MOVEMENT;
           }
       } else if (this.turnValue == TurnValue.MOVEMENT) {
           turnValueText = new Texture("move.png");
           System.out.println("Remaining Steps: "+this.player.getRemainingSteps());

           if (this.player.getRemainingSteps() <= 0) {
               this.turnValue = TurnValue.TRAPCHECK;
           }

           if (this.player.getCurrentField().getFollowingFields().size() == 1) {
                if (animationCounter == 0) {
                   this.player.setCurrentField(player.getCurrentField().getFollowingField(0));
                   this.player.setRemainingSteps(this.player.getRemainingSteps()-1);
                   Vector2 playerPosition = new Vector2(player.getCurrentField().getCoordinates().x + 64, player.getCurrentField().getCoordinates().y + 184);
                   this.player.setPosition(playerPosition);
                   animationCounter = 20;
               }
           animationCounter--;
           }
           if (this.player.getCurrentField().getFollowingFields().size() > 1 && this.player.getRemainingSteps() > 0) {
               this.turnValue = TurnValue.PATHSELECTION;
           }


       } else if (this.turnValue == TurnValue.PATHSELECTION) {

           turnValueText = new Texture("selectPath.png");


           System.out.println("Remaining Steps: "+this.player.getRemainingSteps());

           //Show arrows for PathSelection - selection of path in arrowActor Eventlistener
           if (this.player.getRemainingSteps() > 0) {
               if (this.arrowActors.getArrowActorLeft() == null && this.arrowActors.getArrowActorRight() == null && this.arrowActors.getArrowActorUp() == null && this.arrowActors.getArrowActorDown() == null) {
                   int i = 0;
                   for (PathField pf : this.player.getCurrentField().getFollowingFields()) {
                       //Arrow Spawn for all 4 possible followingFields
                       if (this.player.getCurrentField().getCoordinates().x < pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y < pf.getCoordinates().y) {
                           ArrowActor actorUp = new ArrowActor("arrowNewUp.png", pf.getCoordinates().x, pf.getCoordinates().y, "ArrowUp", this, i);
                           this.arrowActors.setArrowActorRight(actorUp);
                           this.arrowActors.getStage().addActor(actorUp);
                       }
                       if (this.player.getCurrentField().getCoordinates().x > pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y < pf.getCoordinates().y) {
                           ArrowActor actorLeft = new ArrowActor("arrowNewLeft.png", pf.getCoordinates().x, pf.getCoordinates().y, "ArrowLeft", this, i);
                           this.arrowActors.setArrowActorLeft(actorLeft);
                           this.arrowActors.getStage().addActor(actorLeft);
                       }
                       if (this.player.getCurrentField().getCoordinates().x > pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y > pf.getCoordinates().y) {
                           ArrowActor actorDown = new ArrowActor("arrowNewDown.png", pf.getCoordinates().x, pf.getCoordinates().y, "ArrowDown", this, i);
                           this.arrowActors.setArrowActorRight(actorDown);
                           this.arrowActors.getStage().addActor(actorDown);
                       }
                       if (this.player.getCurrentField().getCoordinates().x < pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y > pf.getCoordinates().y) {
                           ArrowActor actorRight = new ArrowActor("arrowNewRight.png", pf.getCoordinates().x, pf.getCoordinates().y, "ArrowRight", this, i);
                           this.arrowActors.setArrowActorRight(actorRight);
                           this.arrowActors.getStage().addActor(actorRight);
                       }
                       i++;
                   }
               }
           }
       } else if (this.turnValue == TurnValue.TRAPCHECK) {

           turnValueText = new Texture("checkTrap.png");
           /*if (this.player.getCurrentField().getTrap().isTrapActivated() == true) {
               this.turnValue = TurnValue.TRAPACTIVATED;
           } else {
               this.turnValue = TurnValue.DICEROLL;
               this.turnDone = true;
           }*/

           //TODO
           //Delete this block after correct implementation of fields
           if (Gdx.input.justTouched()) {
               this.turnDone = true;
               this.turnValue = TurnValue.DICEROLL;
           }

       } else if (this.turnValue == TurnValue.TRAPACTIVATED) {

           turnValueText = new Texture("trapActive.png");
           //TODO
           //CODE FOR DEFUSING TRAP
           this.turnDone = true;
       }

        board.getBatch().draw(turnValueText, -100,-100);
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
}
