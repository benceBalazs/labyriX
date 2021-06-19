package de.tomgrill.gdxtesting.labyrixTests;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.labyrix.game.Models.Board;
import com.labyrix.game.Models.NonPathField;
import com.labyrix.game.Models.PathField;
import com.labyrix.game.Models.Trap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class TestBoard {


    private SpriteBatch batch;

    @Before
    public void init(){
        batch = new SpriteBatch();
    }

    @Test
    public void boardCreation_correctSize(){
        final int[][] board = {{1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5}};
        //create a board by hand

        Board b = null;
        Assert.assertTrue(b.checkBoardSize(board));

        b = new Board(this.batch, board);
        assertEquals(Board.class, b.getClass());
    }

    @Test
    public void boardCreation_checkWrongBoardSizesToSmall(){
        final int[][] board = {{1,2,3,4,5}, {1,2,3,4}, {1,2,3,4,5}};

        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                new Board(batch, board);
            }
        });

    }

    @Test
    public void boardCreation_checkWrongBoardSizesToBig(){
        final int[][] board = {{1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5,6}};

        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                new Board(batch, board);
            }
        });
    }

    @Test
    public void boardCreation_working(){
        int[][] board = {{1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5}};

        Board b = new Board(batch, board);

        for (int i = 0; i < b.getFields().length; i++) {
            for (int j = 0; j < b.getFields()[0].length; j++) {
                 if (board[i][j] == 1 || board[i][j] == 3 || board[i][j] == 5) {
                     assertEquals(PathField.class, b.getFields()[i][j].getClass());
                 } else {
                     assertEquals(NonPathField.class, b.getFields()[i][j].getClass());
                 }
            }
        }
    }
}

