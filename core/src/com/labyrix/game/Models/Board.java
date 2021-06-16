package com.labyrix.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Board {
    private Field[][] fields;

    public Board(int length, int width) {
        fields = new Field[length][width];
    }

    private static SpriteBatch batch = null;

    //Board for development
    public Board(SpriteBatch batch) throws IllegalArgumentException {
        if (this.batch == null) {
            this.batch = batch;
        }

        Image snow = new Image("bodenLabyrixSnow.png");
        Image ziel = new Image("bodenLabyrixZiel2.png");
        Image poison = new Image("bodenlabyrixdarkv2.png");
        Image stone = new Image("bodenLabyrixstone.png");
        Image dach = new Image("bodenLabyrixdach.png");
        Image cheddar = new Image("bodenLabyrixcheddar.png");
        Image floor = new Image("bodenLabyrixv3.png");


        int[][] field = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                {1, 5, 5, 5, 1, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                {1, 5, 5, 5, 1, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                {1, 5, 5, 5, 1, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                {1, 5, 5, 5, 1, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5},
                {1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2},
                {1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2},
                {1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2},
                {1, 1, 1, 1, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2},
                {2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 2, 2},
                {3, 3, 3, 3, 3, 3, 1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 3, 3, 3},
                {3, 3, 3, 3, 3, 3, 1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 3, 3, 3},
                {3, 3, 3, 3, 3, 3, 1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 3, 3, 3},
                {3, 3, 3, 3, 3, 3, 1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 3, 3, 3},
                {3, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 1, 1, 1, 1, 1, 1},
                {6, 6, 6, 6, 6, 6, 1, 6, 6, 6, 6, 6, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1},
                {6, 6, 6, 6, 6, 6, 1, 6, 6, 6, 6, 6, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1},
                {6, 6, 6, 6, 6, 6, 1, 6, 6, 6, 6, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 1},
                {6, 6, 6, 6, 6, 6, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 6, 6, 6, 1},
                {6, 6, 6, 6, 6, 6, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 6, 6, 6, 1},
                {4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 1},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 4, 4, 1},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 7}};

        if (checkBoardSize(field)) {

            fields = new Field[field.length][field[0].length];
            int id = 1;


            for (int i = 0; i < field.length; i++) {
                for (int y = 0; y < field[i].length; y++) {
                    if (field[i][y] == 1) {
                        fields[i][y] = new PathField(id, floor, (float) Math.random());
                    } else if (field[i][y] == 2) {
                        fields[i][y] = new NonPathField(id, poison);
                    } else if (field[i][y] == 3) {
                        fields[i][y] = new PathField(id, stone, (float) Math.random());
                    } else if (field[i][y] == 4) {
                        fields[i][y] = new NonPathField(id, dach);
                    } else if (field[i][y] == 5) {
                        fields[i][y] = new PathField(id, snow, (float) Math.random());
                    } else if (field[i][y] == 6) {
                        fields[i][y] = new PathField(id, cheddar, (float) Math.random());
                    } else {
                        fields[i][y] = new PathField(id, ziel, (float) Math.random());
                    }
                    id++;


                }
            }
            fields[0][0].addFollowingFields((PathField) fields[1][0]);
            fields[1][0].addFollowingFields((PathField) fields[2][0]);
            fields[2][0].addFollowingFields((PathField) fields[3][0]);
            fields[3][0].addFollowingFields((PathField) fields[4][0]);
            fields[4][0].addFollowingFields((PathField) fields[5][0]);
            fields[5][0].addFollowingFields((PathField) fields[6][0]);
            fields[6][0].addFollowingFields((PathField) fields[7][0]);
            fields[7][0].addFollowingFields((PathField) fields[8][0]);

            fields[8][0].addFollowingFields((PathField) fields[8][1]);
            fields[8][1].addFollowingFields((PathField) fields[8][2]);
            fields[8][2].addFollowingFields((PathField) fields[8][3]);
            fields[8][3].addFollowingFields((PathField) fields[8][4]);
           // fields[8][4].addFollowingFields((PathField) fields[8][5]);
            //fields[8][5].addFollowingFields((PathField) fields[8][6]);

            //fields[8][6].addFollowingFields((PathField) fields[9][6]);
            fields[9][6].addFollowingFields((PathField) fields[10][6]);
            fields[10][6].addFollowingFields((PathField) fields[11][6]);
            fields[11][6].addFollowingFields((PathField) fields[12][6]);
            fields[12][6].addFollowingFields((PathField) fields[13][6]);
            fields[13][6].addFollowingFields((PathField) fields[14][6]);
            fields[14][6].addFollowingFields((PathField) fields[15][6]);
            fields[15][6].addFollowingFields((PathField) fields[16][6]);
            fields[16][6].addFollowingFields((PathField) fields[17][6]);
            fields[17][6].addFollowingFields((PathField) fields[18][6]);
            fields[18][6].addFollowingFields((PathField) fields[19][6]);
            fields[19][6].addFollowingFields((PathField) fields[20][6]);

            fields[20][6].addFollowingFields((PathField) fields[20][7]);
            fields[20][7].addFollowingFields((PathField) fields[20][8]);
            fields[20][8].addFollowingFields((PathField) fields[20][9]);
            fields[20][9].addFollowingFields((PathField) fields[20][10]);
            fields[20][10].addFollowingFields((PathField) fields[20][11]);
            fields[20][11].addFollowingFields((PathField) fields[20][12]);
            fields[20][12].addFollowingFields((PathField) fields[20][13]);

            fields[20][13].addFollowingFields((PathField) fields[21][13]);
            fields[21][13].addFollowingFields((PathField) fields[22][13]);
            fields[22][13].addFollowingFields((PathField) fields[23][13]);

            fields[23][13].addFollowingFields((PathField) fields[23][14]);
            fields[23][14].addFollowingFields((PathField) fields[23][15]);
            fields[23][15].addFollowingFields((PathField) fields[23][16]);
            fields[23][16].addFollowingFields((PathField) fields[23][17]);
            fields[23][17].addFollowingFields((PathField) fields[23][18]);
            fields[23][18].addFollowingFields((PathField) fields[23][19]);

            fields[23][19].addFollowingFields((PathField) fields[24][19]);
            fields[24][19].addFollowingFields((PathField) fields[24][20]);
            fields[24][20].addFollowingFields((PathField) fields[24][21]);
            fields[24][21].addFollowingFields((PathField) fields[24][22]);

            fields[24][22].addFollowingFields((PathField) fields[25][22]);
            fields[25][22].addFollowingFields((PathField) fields[25][23]);
            fields[25][23].addFollowingFields((PathField) fields[25][24]);
            fields[25][24].addFollowingFields((PathField) fields[25][25]);

            fields[0][0].addFollowingFields((PathField) fields[0][1]);
            fields[0][1].addFollowingFields((PathField) fields[0][2]);
            fields[0][2].addFollowingFields((PathField) fields[0][3]);
            fields[0][3].addFollowingFields((PathField) fields[0][4]);

            fields[0][4].addFollowingFields((PathField) fields[1][4]);
            fields[1][4].addFollowingFields((PathField) fields[2][4]);
            fields[2][4].addFollowingFields((PathField) fields[3][4]);
            fields[3][4].addFollowingFields((PathField) fields[4][4]);
            fields[4][4].addFollowingFields((PathField) fields[5][4]);
            fields[5][4].addFollowingFields((PathField) fields[6][4]);
            fields[6][4].addFollowingFields((PathField) fields[7][4]);
            fields[7][4].addFollowingFields((PathField) fields[8][4]);
            fields[8][4].addFollowingFields((PathField) fields[9][4]);

            fields[9][4].addFollowingFields((PathField) fields[9][5]);
            fields[9][5].addFollowingFields((PathField) fields[9][6]);
            fields[9][6].addFollowingFields((PathField) fields[9][7]);
            fields[9][7].addFollowingFields((PathField) fields[9][8]);

            fields[9][8].addFollowingFields((PathField) fields[10][8]);
            fields[10][8].addFollowingFields((PathField) fields[11][8]);
            fields[11][8].addFollowingFields((PathField) fields[12][8]);
            fields[12][8].addFollowingFields((PathField) fields[13][8]);
            fields[13][8].addFollowingFields((PathField) fields[14][8]);

            fields[14][8].addFollowingFields((PathField) fields[14][9]);
            fields[14][9].addFollowingFields((PathField) fields[14][10]);
            fields[14][10].addFollowingFields((PathField) fields[14][11]);
            fields[14][11].addFollowingFields((PathField) fields[14][12]);

            fields[0][4].addFollowingFields((PathField) fields[0][5]);
            fields[0][5].addFollowingFields((PathField) fields[0][6]);
            fields[0][6].addFollowingFields((PathField) fields[0][7]);
            fields[0][7].addFollowingFields((PathField) fields[0][8]);

            fields[0][8].addFollowingFields((PathField) fields[1][8]);
            fields[1][8].addFollowingFields((PathField) fields[2][8]);
            fields[2][8].addFollowingFields((PathField) fields[3][8]);
            fields[3][8].addFollowingFields((PathField) fields[4][8]);

            fields[4][8].addFollowingFields((PathField) fields[5][8]);
            fields[5][8].addFollowingFields((PathField) fields[6][8]);
            fields[6][8].addFollowingFields((PathField) fields[7][8]);
            fields[7][8].addFollowingFields((PathField) fields[8][8]);
            fields[8][8].addFollowingFields((PathField) fields[9][8]);

            fields[9][8].addFollowingFields((PathField) fields[9][9]);
            fields[9][9].addFollowingFields((PathField) fields[9][10]);
            fields[9][10].addFollowingFields((PathField) fields[9][11]);
            fields[9][11].addFollowingFields((PathField) fields[9][12]);

            fields[9][12].addFollowingFields((PathField) fields[10][12]);
            fields[10][12].addFollowingFields((PathField) fields[11][12]);
            fields[11][12].addFollowingFields((PathField) fields[12][12]);
            fields[12][12].addFollowingFields((PathField) fields[13][12]);
            fields[13][12].addFollowingFields((PathField) fields[14][12]);

            fields[14][12].addFollowingFields((PathField) fields[14][13]);
            fields[14][13].addFollowingFields((PathField) fields[14][14]);
            fields[14][14].addFollowingFields((PathField) fields[14][15]);
            fields[14][15].addFollowingFields((PathField) fields[14][16]);

            fields[14][16].addFollowingFields((PathField) fields[13][16]);
            fields[13][16].addFollowingFields((PathField) fields[12][16]);
            fields[12][16].addFollowingFields((PathField) fields[11][16]);
            fields[11][16].addFollowingFields((PathField) fields[10][16]);
            fields[10][16].addFollowingFields((PathField) fields[9][16]);

            fields[9][16].addFollowingFields((PathField) fields[9][17]);
            fields[9][17].addFollowingFields((PathField) fields[9][18]);
            fields[9][18].addFollowingFields((PathField) fields[9][19]);
            fields[9][19].addFollowingFields((PathField) fields[9][20]);

            fields[9][20].addFollowingFields((PathField) fields[10][20]);
            fields[10][20].addFollowingFields((PathField) fields[11][20]);
            fields[11][20].addFollowingFields((PathField) fields[12][20]);
            fields[12][20].addFollowingFields((PathField) fields[13][20]);
            fields[13][20].addFollowingFields((PathField) fields[14][20]);

            fields[14][20].addFollowingFields((PathField) fields[14][21]);
            fields[14][21].addFollowingFields((PathField) fields[14][22]);

            fields[14][12].addFollowingFields((PathField) fields[15][12]);
            fields[15][12].addFollowingFields((PathField) fields[16][12]);
            fields[16][12].addFollowingFields((PathField) fields[17][12]);

            fields[17][12].addFollowingFields((PathField) fields[17][13]);
            fields[17][13].addFollowingFields((PathField) fields[17][14]);
            fields[17][14].addFollowingFields((PathField) fields[17][15]);
            fields[17][15].addFollowingFields((PathField) fields[17][16]);
            fields[17][16].addFollowingFields((PathField) fields[17][17]);
            fields[17][17].addFollowingFields((PathField) fields[17][18]);
            fields[17][18].addFollowingFields((PathField) fields[17][19]);
            fields[17][19].addFollowingFields((PathField) fields[17][20]);
            fields[17][20].addFollowingFields((PathField) fields[17][21]);

            fields[17][21].addFollowingFields((PathField) fields[18][21]);
            fields[18][21].addFollowingFields((PathField) fields[19][21]);
            fields[19][21].addFollowingFields((PathField) fields[20][21]);

            fields[4][8].addFollowingFields((PathField) fields[4][9]);
            fields[4][9].addFollowingFields((PathField) fields[4][10]);
            fields[4][10].addFollowingFields((PathField) fields[4][11]);
            fields[4][11].addFollowingFields((PathField) fields[4][12]);
            fields[4][12].addFollowingFields((PathField) fields[4][13]);
            fields[4][13].addFollowingFields((PathField) fields[4][14]);
            fields[4][14].addFollowingFields((PathField) fields[4][15]);
            fields[4][15].addFollowingFields((PathField) fields[4][16]);
            fields[4][16].addFollowingFields((PathField) fields[4][17]);
            fields[4][17].addFollowingFields((PathField) fields[4][18]);
            fields[4][18].addFollowingFields((PathField) fields[4][19]);
            fields[4][19].addFollowingFields((PathField) fields[4][20]);
            fields[4][20].addFollowingFields((PathField) fields[4][21]);
            fields[4][21].addFollowingFields((PathField) fields[4][22]);

            fields[4][22].addFollowingFields((PathField) fields[5][22]);
            fields[5][22].addFollowingFields((PathField) fields[6][22]);
            fields[6][22].addFollowingFields((PathField) fields[7][22]);
            fields[7][22].addFollowingFields((PathField) fields[8][22]);
            fields[8][22].addFollowingFields((PathField) fields[9][22]);
            fields[9][22].addFollowingFields((PathField) fields[10][22]);
            fields[10][22].addFollowingFields((PathField) fields[11][22]);
            fields[11][22].addFollowingFields((PathField) fields[12][22]);
            fields[12][22].addFollowingFields((PathField) fields[13][22]);
            fields[13][22].addFollowingFields((PathField) fields[14][22]);

            fields[14][22].addFollowingFields((PathField) fields[14][23]);
            fields[14][23].addFollowingFields((PathField) fields[14][24]);
            fields[14][24].addFollowingFields((PathField) fields[14][25]);

            fields[14][25].addFollowingFields((PathField) fields[15][25]);
            fields[15][25].addFollowingFields((PathField) fields[16][25]);
            fields[16][25].addFollowingFields((PathField) fields[17][25]);
            fields[17][25].addFollowingFields((PathField) fields[18][25]);
            fields[18][25].addFollowingFields((PathField) fields[19][25]);
            fields[19][25].addFollowingFields((PathField) fields[20][25]);

            fields[20][25].addFollowingFields((PathField) fields[20][24]);
            fields[20][24].addFollowingFields((PathField) fields[20][23]);
            fields[20][23].addFollowingFields((PathField) fields[20][22]);
            fields[20][22].addFollowingFields((PathField) fields[20][21]);

            fields[20][21].addFollowingFields((PathField) fields[21][21]);
            fields[21][21].addFollowingFields((PathField) fields[22][21]);

            fields[22][21].addFollowingFields((PathField) fields[22][22]);
            fields[22][22].addFollowingFields((PathField) fields[22][23]);
            fields[22][23].addFollowingFields((PathField) fields[22][24]);
            fields[22][24].addFollowingFields((PathField) fields[22][25]);

            fields[22][25].addFollowingFields((PathField) fields[23][25]);
            fields[23][25].addFollowingFields((PathField) fields[24][25]);
            fields[24][25].addFollowingFields((PathField) fields[25][25]);

            ((PathField) fields[25][25]).setWinField(true);

            //createPath(field);
        }
    }


    public Board(SpriteBatch batch, int[][] field) throws IllegalArgumentException {
        if (this.batch == null) {
            this.batch = batch;
        }

        if (checkBoardSize(field)) {
            Image snow = new Image("bodenLabyrixSnow.png");
            Image ziel = new Image("bodenLabyrixZiel.png");
            Image poison = new Image("bodenlabyrixdarkv2.png");
            Image stone = new Image("bodenLabyrixstone.png");
            Image dach = new Image("bodenLabyrixdach.png");
            Image cheddar = new Image("bodenLabyrixcheddar.png");
            Image floor = new Image("bodenLabyrixv3.png");

            fields = new Field[field.length][field[0].length];
            int id = 1;

            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    if (field[i][j] == 1) {
                        fields[i][j] = new PathField(id, floor, (float) Math.random());
                    } else if (field[i][j] == 2) {
                        fields[i][j] = new NonPathField(id, poison);
                    } else if (field[i][j] == 3) {
                        fields[i][j] = new PathField(id, stone, (float) Math.random());
                    } else if (field[i][j] == 4) {
                        fields[i][j] = new NonPathField(id, dach);
                    } else if (field[i][j] == 5) {
                        fields[i][j] = new PathField(id, snow, (float) Math.random());
                    } else if (field[i][j] == 6) {
                        fields[i][j] = new PathField(id, cheddar, (float) Math.random());
                    } else {
                        fields[i][j] = new PathField(id, ziel, (float) Math.random());
                    }
                    id++;
                }
            }
        }
    }

    public boolean checkBoardSize(int[][] field) throws IllegalArgumentException {
        for (int i = 1; i < field.length; i++) {
            if (field[i - 1].length != field[1].length) {
                throw new IllegalArgumentException();
            }
        }
        return true;
    }

    public void drawGround() {
        for (int row = fields.length - 1; row >= 0; row--) {
            for (int cols = fields[row].length - 1; cols >= 0; cols--) {
                float x = (cols - row) * 128;
                float y = (cols + row) * 64;
                fields[row][cols].setCoordinates(new Vector2(x, y));
                batch.draw(fields[row][cols].getFieldImage().getImg(), x- Gdx.graphics.getWidth()/8f, y-Gdx.graphics.getHeight()/8f);
            }
        }
    }

    public Field[][] getFields() {
        return fields;
    }

    public PathField getPathFieldByID(int id) {
        if (this.fields != null) {
            for (int i = 0; i < fields.length; i++) {
                for (int j = 0; j < fields[i].length; j++) {
                    if (fields[i][j].getId() == id) {
                        return (PathField) fields[i][j];
                    }
                }
            }
        }
        return null;
    }

    public void drawImg(Texture img, int x, int y) {
        batch.draw(img, x, y);
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
