package com.labyrix.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Board {
    private Field[][] fields;

    public Board(int length, int width) {
        fields = new Field[length][width];
    }
    private static SpriteBatch batch = null;

    private static Image selectionArrowLeft = null;
    private static Image selectionArrowRight = null;
    private static Image selectionArrowUp = null;
    private static Image selectionArrowDown = null;

    private ArrowActors arrowActors = new ArrowActors();

    //Board for development
    public Board(SpriteBatch batch) {
        if (this.batch == null) {
            this.batch = batch;
        }

        Image floor = new Image("bodenLabyrixSnow.png");
        Image ziel = new Image("bodenLabyrixZiel.png");
        Image poison = new Image("bodenlabyrixdarkv2.png");
        fields = new Field[5][5];
        fields[0][0] = new PathField(1, floor);
        fields[0][1] = new PathField(2, floor);
        fields[0][2] = new PathField(3, floor);
        fields[0][3] = new PathField(4, floor);
        fields[0][4] = new PathField(5, floor);
        fields[1][2] = new PathField(6, floor);
        fields[2][2] = new PathField(7, floor);
        fields[3][2] = new PathField(8, floor);
        fields[4][2] = new PathField(9, floor);
        fields[1][4] = new PathField(10, floor);
        fields[2][4] = new PathField(11, floor);
        fields[3][4] = new PathField(12, floor);
        fields[4][4] = new PathField(13, floor);
        fields[4][3] = new PathField(14, ziel);


        fields[0][0].addFollowingFields((PathField) fields[0][1]);
        fields[0][1].addFollowingFields((PathField) fields[0][2]);
        fields[0][2].addFollowingFields((PathField) fields[0][3]);
        fields[0][2].addFollowingFields((PathField) fields[1][2]);
        fields[0][3].addFollowingFields((PathField) fields[0][4]);
        fields[1][2].addFollowingFields((PathField) fields[2][2]);
        fields[2][2].addFollowingFields((PathField) fields[3][2]);
        fields[3][2].addFollowingFields((PathField) fields[4][2]);
        fields[0][4].addFollowingFields((PathField) fields[1][4]);
        fields[1][4].addFollowingFields((PathField) fields[2][4]);
        fields[2][4].addFollowingFields((PathField) fields[3][4]);
        fields[3][4].addFollowingFields((PathField) fields[4][4]);
        fields[4][4].addFollowingFields((PathField) fields[4][3]);
        fields[4][2].addFollowingFields((PathField) fields[4][3]);


        fields[1][0] = new NonPathField(15, poison);
        fields[1][1] = new NonPathField(16, poison);
        fields[1][3] = new NonPathField(17, poison);
        fields[2][0] = new NonPathField(18, poison);
        fields[2][1] = new NonPathField(19, poison);
        fields[2][3] = new NonPathField(20, poison);
        fields[3][0] = new NonPathField(21, poison);
        fields[3][1] = new NonPathField(22, poison);
        fields[3][3] = new NonPathField(23, poison);
        fields[4][0] = new NonPathField(24, poison);
        fields[4][1] = new NonPathField(25, poison);
    }

    public void drawGround() {
        for (int row = fields.length - 1; row >= 0; row--) {
            for (int cols = fields[row].length - 1; cols >= 0; cols--) {
                float x = (cols - row) * 128;
                float y = (cols + row) * 64;
                fields[row][cols].setCoordinates(new Vector2(x, y));
                batch.draw(fields[row][cols].getFieldImage().getImg(), x, y);
            }
        }

        if (this.selectionArrowDown != null) {
            batch.draw(selectionArrowDown.getImg(), selectionArrowDown.getCoordinates().x, selectionArrowDown.getCoordinates().y);

        }
        if (this.selectionArrowUp != null) {
            batch.draw(selectionArrowUp.getImg(), selectionArrowUp.getCoordinates().x, selectionArrowUp.getCoordinates().y);
        }
        if (this.selectionArrowLeft != null) {
            batch.draw(selectionArrowLeft.getImg(), selectionArrowLeft.getCoordinates().x, selectionArrowLeft.getCoordinates().y);
            arrowActors.getArrowActorLeft().draw(batch, 1);
        }
        if (this.selectionArrowRight != null) {
            batch.draw(selectionArrowRight.getImg(), selectionArrowRight.getCoordinates().x, selectionArrowRight.getCoordinates().y);
        }
    }


    public ArrowActors getArrowActors() {
        return arrowActors;
    }

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public PathField getPathFieldByID(int id) {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                if (fields[i][j].getId() == id) {
                    return (PathField)fields[i][j];
                }
            }
        }
        return null;
    }

    public Image getSelectionArrowLeft() {
        return selectionArrowLeft;
    }

    public void setSelectionArrowLeft(Image selectionArrowLeft) {
        Board.selectionArrowLeft = selectionArrowLeft;
    }

    public Image getSelectionArrowRight() {
        return selectionArrowRight;
    }

    public void setSelectionArrowRight(Image selectionArrowRight) {
        Board.selectionArrowRight = selectionArrowRight;
    }

    public Image getSelectionArrowUp() {
        return selectionArrowUp;
    }

    public void setSelectionArrowUp(Image selectionArrowUp) {
        Board.selectionArrowUp = selectionArrowUp;
    }

    public Image getSelectionArrowDown() {
        return selectionArrowDown;
    }

    public void setSelectionArrowDown(Image selectionArrowDown) {
        Board.selectionArrowDown = selectionArrowDown;
    }
}
