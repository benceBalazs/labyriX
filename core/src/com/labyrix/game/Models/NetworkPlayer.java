package com.labyrix.game.Models;

import com.badlogic.gdx.math.Vector2;
import com.labyrix.game.ENUMS.TurnValue;

import java.util.ArrayList;

public class NetworkPlayer {
    private int id;
    private int lobbyId;
    private String name;
    private String imagePath;
    private Vector2 position;
    private PathField currentField;
    private String roundStatus = null;
    private float movementSpeed = 1f;
    private int remainingCheats = 2;
    private int numberOfFails = 0;
    private int counterReducedMovementSpeed = 0;
    private int remainingSteps = 0;
    private int maxRemainingFields;
    private int minRemainingFields;
    public TurnValue turnValue;
    private int hasCheated = 0;
    private ArrayList<Integer> listAllPath = new ArrayList<Integer>();

    public NetworkPlayer(String name, String playerImagePath) {
        this.name = name;
        this.imagePath = playerImagePath;
    }

    public NetworkPlayer(int id, int lobbyId, Vector2 position, int maxRemainingFields, int minRemainingFields) {
        this.id = id;
        this.lobbyId = lobbyId;
        this.position = position;
        this.maxRemainingFields = maxRemainingFields;
        this.minRemainingFields = minRemainingFields;
    }

    public NetworkPlayer() {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(String roundStatus) {
        this.roundStatus = roundStatus;
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

    public int getRemainingSteps() {
        return remainingSteps;
    }

    public void setRemainingSteps(int remainingSteps) {
        this.remainingSteps = remainingSteps;
    }

    public int getMaxRemainingFields() {
        return maxRemainingFields;
    }

    public void setMaxRemainingFields(int maxRemainingFields) {
        this.maxRemainingFields = maxRemainingFields;
    }

    public int getMinRemainingFields() {
        return minRemainingFields;
    }

    public void setMinRemainingFields(int minRemainingFields) {
        this.minRemainingFields = minRemainingFields;
    }

    public TurnValue getTurnValue() {
        return turnValue;
    }

    public void setTurnValue(TurnValue turnValue) {
        this.turnValue = turnValue;
    }

    public ArrayList<Integer> getListAllPath() {
        return listAllPath;
    }

    public void setListAllPath(ArrayList<Integer> listAllPath) {
        this.listAllPath = listAllPath;
    }

    @Override
    public String toString() {
        return "NetworkPlayer{" +
                "id=" + id +
                ", lobbyId=" + lobbyId +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
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
                '}';
    }
}
