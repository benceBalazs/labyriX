package com.labyrix.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Board {
    private Field[][] fields;

    public Board(int length, int width) {
        fields = new Field[length][width];
    }
    private static SpriteBatch batch = null;

    //Board for development
    public Board(SpriteBatch batch) {
        if (this.batch == null) {
            this.batch = batch;
        }

        Image floor = new Image("bodenLabyrixSnow.png");
        Image ziel = new Image("bodenLabyrixZiel.png");
        Image poison = new Image("bodenlabyrixdarkv2.png");
        Image stone = new Image ("bodenLabyrixstone.png");
        Image dach = new Image("bodenLabyrixdach.png");
        Image cheddar = new Image ("bodenLabyrixcheddar.png");
        Image standard = new Image ("bodenlabyrixv2.png");




        int[][] field = {{1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2},
                {3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4},
                {5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2},
                {3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4},
                {5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2},
                {3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4},
                {5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2},
                {3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4},
                {5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2},
                {3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4},
                {5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2},
                {3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4},
                {5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2},
                {3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4},
                {5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2},
                {3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4},
                {5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2},
                {3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4},
                {5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 7}};

        fields = new Field[26][26];
        int id = 1;


        for (int i = 0; i < field.length; i++) {
            for (int y = 0; y < field[i].length; y++) {
                if (field[i][y] == 1) {
                    fields[i][y] = new PathField(id,floor,(float)Math.random());
                } else if (field[i][y] == 2) {
                    fields [i][y] = new NonPathField(id,poison);
                } else if (field[i][y] == 3) {
                    fields[i][y] = new PathField(id,stone,(float)Math.random());
                } else if (field[i][y] == 4) {
                    fields[i][y] = new NonPathField(id,dach);
                } else if (field[i][y] == 5) {
                    fields[i][y] = new PathField(id,standard,(float)Math.random());
                } else if (field[i][y] == 6) {
                    fields[i][y] = new NonPathField(id,cheddar);
                } else {
                    fields[i][y] = new NonPathField(id,ziel);
                }
                id++;


            }
        }
        fields[0][0].addFollowingFields((PathField) fields[1][0]);
        fields[1][0].addFollowingFields((PathField) fields[2][0]);

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
}
