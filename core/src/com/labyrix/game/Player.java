package com.labyrix.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {

    private int id;
    private String name;
    private Image playerImage;
    private Vector2 position;
    private PathField currentField;
    private String roundStatus = null;
    private float movementSpeed = 1f;
    private int remainingCheats = 2;
    private int numberOfFails = 0;
    private int counterReducedMovementSpeed = 0;
    private TrapEvent activeEvent = null; //eventuell in die Enumeration umändern
    private static Board board = null;

    Player(String name, String playerImagePath, PathField currentField, int xPos, int yPos, Board board) {
        this.name = name;
        this.playerImage = new Image(playerImagePath);
        this.currentField = currentField;
//current startposition for testcases
        position = new Vector2(xPos, yPos);
        if (this.board == null) {
            this.board = board;
        }
    }


    public void render(SpriteBatch batch) {
        batch.draw(playerImage.getImg(), position.x, position.y);
    }

    public void update() {
        movePlayer();
    }


    public void movePlayer() {
        if (Gdx.input.justTouched()) {
            if (this.currentField.getFollowingFields().size() == 1) {
                this.currentField = this.currentField.getFollowingField(0);
                this.position.x = this.currentField.getCoordinates().x + 64;
                this.position.y = this.currentField.getCoordinates().y + 184;

                if (this.currentField.getFollowingFields().size() > 1) {
                    int i = 0;
                    for (PathField pf: this.currentField.getFollowingFields()) {
                        System.out.println("Test");
                        Image img =  new Image("kreisIndicator.png");
                        Vector2 v = new Vector2(pf.getCoordinates().x, pf.getCoordinates().y);
                        img.setCoordinates(v);
                        if (i == 0) {
                            board.setSelectionArrorUp(img);
                        } else if (i == 1) {
                            board.setSelectionArrorRight(img);
                        } else if (i == 2) {
                            board.setSelectionArrorLeft(img);
                        }
                        i++;
                    }
                }
            }
            //if more than one following field - just chose one random field
            else if (this.currentField.getFollowingFields().size() > 1) {
                //Arrow Spawn for all 4 possible followingFields
                //if touched one of them/field -> move there;

                //(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat())
                
                int followingFieldIndex = (int) (Math.random()*10);
                followingFieldIndex = (followingFieldIndex)  % currentField.getFollowingFields().size();

                currentField = currentField.getFollowingField(followingFieldIndex);
                position.x = currentField.getCoordinates().x + 64;
                position.y = currentField.getCoordinates().y + 184;
                board.setSelectionArrorUp(null);
                board.setSelectionArrorDown(null);
                board.setSelectionArrorRight(null);
                board.setSelectionArrorLeft(null);

            }
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public TrapEvent getActiveEvent() {
        return activeEvent;
    }

    public void setActiveEvent(TrapEvent activeEvent) {
        this.activeEvent = activeEvent;
    }

    public int getRemainingCheats() {
        return remainingCheats;
    }

    public void setRemainingCheats(int remainingCheats) {
        this.remainingCheats = remainingCheats;
    }

    public int getNumberOfFails() {
        return numberOfFails;
    }

    public void setNumberOfFails(int numberOfFails) {
        this.numberOfFails = numberOfFails;
    }

    public int getCounterReducedMovementSpeed() {
        return counterReducedMovementSpeed;
    }

    public void setCounterReducedMovementSpeed(int counterReducedMovementSpeed) {
        this.counterReducedMovementSpeed = counterReducedMovementSpeed;
    }

    public String getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(String roundStatus) {
        this.roundStatus = roundStatus;
    }

    public Image getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(Image playerImage) {
        this.playerImage = playerImage;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public PathField getCurrentField() {
        return currentField;
    }

    public void setCurrentField(PathField currentField) {
        this.currentField = currentField;
    }
}
