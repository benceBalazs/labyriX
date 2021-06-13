package com.labyrix.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.labyrix.game.ENUMS.TurnValue;

import java.nio.file.Path;
import java.util.ArrayList;


public class Player {

    private int id;
    private int lobbyId;
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
    private int maxRemainingFields;
    private int minRemainingFields;
    public TurnValue turnValue;
    private ArrayList<Integer> listAllPath = new ArrayList<Integer>();
    private int hasCheated = 0;
    private boolean isUpdated = false;

    public Player(int id, int lobbyId, String name, String playerImagePath, PathField currentField, int xPos, int yPos, Board board) {
        this.id = id;
        this.lobbyId = lobbyId;
        this.name = name;
        this.playerImage = new Image(playerImagePath);
        this.currentField = currentField;
                
        //current startposition for testcases
        position = new Vector2(xPos, yPos);
        if (this.board == null) {
            this.board = board;
        }
    }

    public Player(String name, String playerImagePath) {
        this.name = name;
        this.playerImage = new Image(playerImagePath);
    }

    public void render(SpriteBatch batch) {
        batch.draw(playerImage.getImg(), position.x- Gdx.graphics.getWidth()/8f, position.y- Gdx.graphics.getHeight()/8f);
        maxRemainingFields = maxPathLength(currentField);
        minRemainingFields = minPathLength(currentField);
    }

    private void countingFields (Field field, int count){
        count ++;
        ArrayList<PathField> followingFieldList = field.getFollowingFields();
        for(int i= 0; i< followingFieldList.size();i++){
            countingFields(followingFieldList.get(i),count);
        }
        if (followingFieldList.size()==0){
            listAllPath.add(count);
        }
    }

    private int maxPathLength (Field field){
        countingFields(field,0);
        int maxPath = 0;
        for (int i = 0; i< listAllPath.size(); i++){
            if(listAllPath.get(i)> maxPath){
                maxPath = listAllPath.get(i);
            }
        }
        listAllPath = new ArrayList<Integer>();
        return maxPath;
    }

    private int minPathLength (Field field){
        countingFields(field, 0);
        int minPath = listAllPath.get(0);
        for (int i = 0; i< listAllPath.size(); i++){
            if(listAllPath.get(i)< minPath){
                minPath = listAllPath.get(i);
            }
        }
        listAllPath = new ArrayList<Integer>();
        return minPath;
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

    public int getMaxRemainingFields() {
        return maxRemainingFields;
    }

    public int getMinRemainingFields() {
        return minRemainingFields;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TurnValue getTurnValue() {
        return turnValue;
    }

    public void setTurnValue(TurnValue turnValue) {
        this.turnValue = turnValue;
    }

    public int getHasCheated() {
        return hasCheated;
    }

    public void setHasCheated(int hasCheated) {
        this.hasCheated = hasCheated;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(int lobbyId) {
        this.lobbyId = lobbyId;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public void setMaxRemainingFields(int maxRemainingFields) {
        this.maxRemainingFields = maxRemainingFields;
    }

    public void setMinRemainingFields(int minRemainingFields) {
        this.minRemainingFields = minRemainingFields;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", lobbyId=" + lobbyId +
                ", name='" + name + '\'' +
                ", playerImage=" + playerImage +
                ", position=" + position +
                ", currentField=" + currentField +
                ", roundStatus='" + roundStatus + '\'' +
                ", movementSpeed=" + movementSpeed +
                ", remainingCheats=" + remainingCheats +
                ", numberOfFails=" + numberOfFails +
                ", counterReducedMovementSpeed=" + counterReducedMovementSpeed +
                ", remainingSteps=" + remainingSteps +
                ", maxRemainingFields=" + maxRemainingFields +
                ", minRemainingFields=" + minRemainingFields +
                ", turnValue=" + turnValue +
                ", listAllPath=" + listAllPath +
                ", hasCheated=" + hasCheated +
                ", isUpdated=" + isUpdated +
                '}';
    }
}
