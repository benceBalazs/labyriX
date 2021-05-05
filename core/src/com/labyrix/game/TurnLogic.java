package com.labyrix.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.esotericsoftware.kryonet.Client;

public class TurnLogic {

    private Board board;
    private Player player;
    private boolean turnDone;
    private TurnValue turnValue;
    private Client client;

    public TurnLogic(Board board, Player player) {
        this.board = board;
        this.player = player;
        this.client = ClientNetworkHandler.getInstance().getClient();
        this.turnDone = false;
        this.turnValue = TurnValue.DICEROLL;
    }

    public void doTurn() {
        System.out.println(this.turnValue);
       if (this.turnValue == TurnValue.DICEROLL) {
           if (Gdx.input.justTouched()) {
               //player.setRemainingSteps((int) (Math.random() * 10 * player.getMovementSpeed()));

               this.player.setRemainingSteps(3);
               this.turnValue = TurnValue.MOVEMENT;
           }
       } else if (this.turnValue == TurnValue.MOVEMENT) {
           System.out.println("Remaining Steps: "+this.player.getRemainingSteps());

           if (this.player.getRemainingSteps() <= 0) {
               this.turnValue = TurnValue.TRAPCHECK;
           }

           if (this.player.getCurrentField().getFollowingFields().size() == 1) {
               if (Gdx.input.justTouched()) {

                   this.player.setCurrentField(player.getCurrentField().getFollowingField(0));
                   this.player.setRemainingSteps(this.player.getRemainingSteps()-1);
                   Vector2 playerPosition = new Vector2(player.getCurrentField().getCoordinates().x + 64, player.getCurrentField().getCoordinates().y + 184);
                   this.player.setPosition(playerPosition);
               }
           }
           if (this.player.getCurrentField().getFollowingFields().size() > 1 && this.player.getRemainingSteps() > 0) {
               this.turnValue = TurnValue.PATHSELECTION;
           }


       } else if (this.turnValue == TurnValue.PATHSELECTION) {


           System.out.println("Remaining Steps: "+this.player.getRemainingSteps());

           //TODO
           //Do PathSelection Stuff
           //Show arrows for PathSelection
           if (this.player.getRemainingSteps() > 0) {
               if (this.board.getSelectionArrowDown() == null && this.board.getSelectionArrowLeft() == null && this.board.getSelectionArrowRight() == null && this.board.getSelectionArrowUp() == null) {
                   for (PathField pf : this.player.getCurrentField().getFollowingFields()) {
                       //Arrow Spawn for all 4 possible followingFields
                       String imgPath = "kreisIndicator.png";
                       Image img = new Image(imgPath);
                       Vector2 v = new Vector2(pf.getCoordinates().x, pf.getCoordinates().y);
                       img.setCoordinates(v);

                       if (this.player.getCurrentField().getCoordinates().x < pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y < pf.getCoordinates().y) {
                           imgPath = "pfeilOben.png";
                           img.setImg(new Texture(imgPath));
                           this.board.setSelectionArrowUp(img);
                       }
                       if (this.player.getCurrentField().getCoordinates().x > pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y < pf.getCoordinates().y) {
                           imgPath = "pfeilLinks.png";
                           img.setImg(new Texture(imgPath));
                           this.board.setSelectionArrowLeft(img);

                           Actor actorLeft = new Actor();
                           actorLeft.setPosition(pf.getCoordinates().x, pf.getCoordinates().y);
                           actorLeft.setHeight(256);
                           actorLeft.setWidth(256);
                           actorLeft.setColor(Color.CYAN);
                           actorLeft.setZIndex(900);

                           actorLeft.addListener(new InputListener() {
                               @Override
                               public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                   System.out.println("touched");
                               }
                           });
                           this.board.getArrowActors().setArrowActorLeft(actorLeft);
                       }
                       if (this.player.getCurrentField().getCoordinates().x > pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y > pf.getCoordinates().y) {
                           imgPath = "pfeilHinten.png";
                           img.setImg(new Texture(imgPath));
                           this.board.setSelectionArrowDown(img);
                       }
                       if (this.player.getCurrentField().getCoordinates().x < pf.getCoordinates().x && this.player.getCurrentField().getCoordinates().y > pf.getCoordinates().y) {
                           imgPath = "pfeilRechts.png";
                           img.setImg(new Texture(imgPath));
                           this.board.setSelectionArrowRight(img);
                       }
                   }
               }


               if (Gdx.input.justTouched()) {
                   //Select Path

                   //Move to selected field

                   //Only go back to movement - if path has been selected
                   this.player.setCurrentField(this.player.getCurrentField().getFollowingField(1));
                   Vector2 playerPosition = new Vector2(this.player.getCurrentField().getCoordinates().x + 64, this.player.getCurrentField().getCoordinates().y + 184);
                   this. player.setPosition(playerPosition);
                   this.board.setSelectionArrowUp(null);
                   this.board.setSelectionArrowDown(null);
                   this.board.setSelectionArrowRight(null);
                   this.board.setSelectionArrowLeft(null);
                   this.player.setRemainingSteps(this.player.getRemainingSteps()-1);
                   this.turnValue = TurnValue.MOVEMENT;
               }
           }
       } else if (this.turnValue == TurnValue.TRAPCHECK) {
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
           //TODO
           //CODE FOR DEFUSING TRAP
           this.turnDone = true;
       }
    }


}
