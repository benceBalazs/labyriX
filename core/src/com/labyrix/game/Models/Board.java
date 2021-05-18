package com.labyrix.game.Models;

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
        Image ziel = new Image("bodenLabyrixZiel.png");
        Image poison = new Image("bodenlabyrixdarkv2.png");
        Image stone = new Image("bodenLabyrixstone.png");
        Image dach = new Image("bodenLabyrixdach.png");
        Image cheddar = new Image("bodenLabyrixcheddar.png");
        Image floor = new Image("bodenLabyrixv3.png");


        int[][] field = {
                {1, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2},
                {1, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4},
                {1, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2},
                {1, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2},
                {1, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4},
                {1, 1, 1, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2},
                {4, 2, 1, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2},
                {1, 1, 1, 1, 1, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4},
                {1, 2, 1, 2, 1, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2},
                {1, 2, 1, 4, 1, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2},
                {1, 4, 1, 2, 1, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4},
                {1, 2, 1, 2, 1, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 4, 2, 3, 4, 4, 4, 2},
                {1, 1, 1, 1, 1, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4},
                {4, 2, 4, 2, 1, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2}, //13 4
                {4, 2, 3, 4, 1, 1, 1, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2}, //14 4
                {3, 4, 4, 2, 1, 2, 1, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4},  //15 4
                {4, 2, 4, 2, 1, 1, 1, 1, 1, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2}, //16 4, 5, 6
                {4, 2, 3, 4, 4, 2, 4, 2, 1, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2},
                {3, 4, 4, 2, 4, 2, 3, 4, 1, 1, 1, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4},
                {4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 1, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2},
                {4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 1, 1, 1, 2, 3, 4, 1, 1, 1, 2, 3, 4, 4, 2, 4, 2},
                {3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 1, 4, 1, 1, 1, 2, 1, 4, 1, 1, 1, 2, 3, 4},
                {4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 1, 1, 1, 2, 3, 4, 1, 1, 1, 2, 1, 4, 4, 2},
                {4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 1, 1, 1, 2},
                {3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 1, 4},
                {4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 4, 2, 4, 2, 3, 4, 1, 7}};

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
            /*fields[0][0].addFollowingFields((PathField) fields[1][0]);
            fields[1][0].addFollowingFields((PathField) fields[2][0]);
            fields[2][0].addFollowingFields((PathField) fields[3][0]);

            fields[3][0].addFollowingFields((PathField) fields[4][0]);
            fields[4][0].addFollowingFields((PathField) fields[5][0]);
            fields[5][0].addFollowingFields((PathField) fields[5][1]);
            fields[5][1].addFollowingFields((PathField) fields[5][2]);
            fields[5][2].addFollowingFields((PathField) fields[6][2]);
            fields[6][2].addFollowingFields((PathField) fields[7][2]);
            //Verzweigung

            fields[7][2].addFollowingFields((PathField) fields[7][3]);
            fields[7][3].addFollowingFields((PathField) fields[7][4]);
            fields[7][4].addFollowingFields((PathField) fields[8][4]);
            fields[8][4].addFollowingFields((PathField) fields[9][4]);
            fields[9][4].addFollowingFields((PathField) fields[10][4]);
            fields[10][4].addFollowingFields((PathField) fields[11][4]);
            fields[11][4].addFollowingFields((PathField) fields[12][4]);


            fields[7][2].addFollowingFields((PathField) fields[8][2]);
            fields[8][2].addFollowingFields((PathField) fields[9][2]);
            fields[9][2].addFollowingFields((PathField) fields[10][2]);
            fields[10][2].addFollowingFields((PathField) fields[11][2]);
            fields[11][2].addFollowingFields((PathField) fields[12][2]);
            fields[12][2].addFollowingFields((PathField) fields[12][3]);
            fields[12][3].addFollowingFields((PathField) fields[12][4]);
            fields[12][4].addFollowingFields((PathField) fields[13][4]);
            fields[13][4].addFollowingFields((PathField) fields[14][4]);
            fields[14][4].addFollowingFields((PathField) fields[14][5]);
            fields[14][5].addFollowingFields((PathField) fields[14][6]);
            fields[14][6].addFollowingFields((PathField) fields[15][6]);
            fields[15][6].addFollowingFields((PathField) fields[16][6]);
            fields[16][6].addFollowingFields((PathField) fields[16][7]);
            fields[16][7].addFollowingFields((PathField) fields[16][8]);

            fields[16][8].addFollowingFields((PathField) fields[17][8]);
            fields[17][8].addFollowingFields((PathField) fields[18][8]);
            fields[18][8].addFollowingFields((PathField) fields[18][9]);
            fields[18][9].addFollowingFields((PathField) fields[18][10]);
            fields[18][10].addFollowingFields((PathField) fields[19][10]);
            fields[19][10].addFollowingFields((PathField) fields[20][10]);
            fields[20][10].addFollowingFields((PathField) fields[20][11]);
            fields[20][11].addFollowingFields((PathField) fields[20][12]);
            fields[20][12].addFollowingFields((PathField) fields[21][12]);
            fields[21][12].addFollowingFields((PathField) fields[22][12]);
            fields[22][12].addFollowingFields((PathField) fields[22][13]);

            fields[22][13].addFollowingFields((PathField) fields[22][14]);
            fields[22][14].addFollowingFields((PathField) fields[21][14]);
            fields[21][14].addFollowingFields((PathField) fields[21][15]);
            fields[21][15].addFollowingFields((PathField) fields[21][16]);
            fields[21][16].addFollowingFields((PathField) fields[20][16]);
            fields[20][16].addFollowingFields((PathField) fields[20][17]);
            fields[20][17].addFollowingFields((PathField) fields[20][18]);
            fields[20][18].addFollowingFields((PathField) fields[21][18]);
            fields[21][18].addFollowingFields((PathField) fields[22][18]);
            fields[22][18].addFollowingFields((PathField) fields[22][19]);

            fields[22][19].addFollowingFields((PathField) fields[22][20]);
            fields[22][20].addFollowingFields((PathField) fields[21][20]);
            fields[21][20].addFollowingFields((PathField) fields[21][21]);
            fields[21][21].addFollowingFields((PathField) fields[21][22]);

            fields[21][22].addFollowingFields((PathField) fields[22][22]);
            fields[22][22].addFollowingFields((PathField) fields[23][22]);
            fields[23][22].addFollowingFields((PathField) fields[23][23]);
            fields[23][23].addFollowingFields((PathField) fields[23][24]);
            fields[23][24].addFollowingFields((PathField) fields[24][24]);
            fields[24][24].addFollowingFields((PathField) fields[25][24]);
            fields[25][24].addFollowingFields((PathField) fields[25][25]);*/

            createPath(field);
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
                batch.draw(fields[row][cols].getFieldImage().getImg(), x, y);
            }
        }
    }

    public void createPath(int[][] field) {
        ArrayList<Field> arrayList = new ArrayList<>();
        ArrayList<PathField> followingFields = new ArrayList<>();
        ArrayList<Integer> is = new ArrayList<>();
        ArrayList<Integer> js = new ArrayList<>();

        int i = 0, j = 0;
        is.add(i);
        js.add(j);
        followingFields.add((PathField) fields[0][0]);

        while (followingFields.size() > 0) {
            i = is.get(0);
            j = js.get(0);
            if (field[i][j] == 1 || field[i][j] == 7) {
                if (j < field[i].length - 1 && !arrayList.contains(fields[i][j + 1]) && field[i][j + 1] == 1) {
                    if (!this.fields[i][j].getFollowingFields().contains(fields[i][j + 1])) {
                        this.fields[i][j].addFollowingFields((PathField) fields[i][j + 1]);
                        followingFields.add((PathField) this.fields[i][j + 1]);
                        is.add(i);
                        js.add(j + 1);
                        arrayList.add(fields[i][j]);
                    }
                }
                if (i < field.length - 1 && !arrayList.contains(fields[i + 1][j]) && field[i + 1][j] == 1) {
                    if (!this.fields[i][j].getFollowingFields().contains(fields[i+1][j])) {
                        this.fields[i][j].addFollowingFields((PathField) fields[i + 1][j]);
                        followingFields.add((PathField) this.fields[i + 1][j]);
                        is.add(i + 1);
                        js.add(j);
                        arrayList.add(fields[i][j]);
                    }
                }
                if (j > 0 && !arrayList.contains(fields[i][j - 1]) && field[i][j - 1] == 1) {
                    if (!this.fields[i][j].getFollowingFields().contains(fields[i][j - 1])) {
                        this.fields[i][j].addFollowingFields((PathField) fields[i][j - 1]);
                        followingFields.add((PathField) this.fields[i][j - 1]);
                        is.add(i);
                        js.add(j - 1);
                        arrayList.add(fields[i][j]);
                    }
                }
                if (i > 0 && !arrayList.contains(fields[i - 1][j]) && field[i - 1][j] == 1) {
                    if (!this.fields[i][j].getFollowingFields().contains(fields[i-1][j])) {
                        this.fields[i][j].addFollowingFields((PathField) fields[i - 1][j]);
                        followingFields.add((PathField) this.fields[i - 1][j]);
                        is.add(i - 1);
                        js.add(j);
                        arrayList.add(fields[i][j]);
                    }
                }
            }
            is.remove(0);
            js.remove(0);
            followingFields.remove(0);
            arrayList.add(fields[i][j]);
        }
    }

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
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
