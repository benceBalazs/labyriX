package com.labyrix.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.labyrix.game.Models.Board;


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
    private int remainingSteps = 0;
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

    public int getRemainingSteps() {
        return remainingSteps;
    }

    public void setRemainingSteps(int remainingSteps) {
        this.remainingSteps = remainingSteps;
    }
}
