package de.tomgrill.gdxtesting.labyrixTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.labyrix.game.Models.Board;
import com.labyrix.game.Models.Field;
import com.labyrix.game.Models.PathField;
import com.labyrix.game.Models.Player;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class TestPlayer {
    private Field[][] fields;
    private Player player;

    @Before
    public void init() {
        //creating field
        fields = new Field[10][10];

        int [][]field = {
                {1,2,2,2,2,2,2,2,2,2},
                {1,2,2,2,2,2,2,2,2,2},
                {1,1,1,1,1,2,2,2,2,2},
                {1,2,2,2,1,2,2,2,2,2},
                {1,2,2,2,1,2,2,2,2,2},
                {1,1,1,1,1,2,2,2,2,2},
                {1,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2,2,2}};

        //Creating Pathfield with fields
        for (int i = 0; i < fields.length; i++) {
            for (int y = 0; y < fields[i].length; y++) {
                fields[i][y] = new PathField();
            }
        }
        //adding following fields till the last field
        fields[0][0].addFollowingFields((PathField) fields[1][0]);
        fields[1][0].addFollowingFields((PathField) fields[2][0]);
        fields[2][0].addFollowingFields((PathField) fields[3][0]);
        fields[3][0].addFollowingFields((PathField) fields[4][0]);
        fields[4][0].addFollowingFields((PathField) fields[5][0]);
        fields[5][0].addFollowingFields((PathField) fields[6][0]);

        fields[2][0].addFollowingFields((PathField) fields[2][1]);
        fields[2][1].addFollowingFields((PathField) fields[2][2]);
        fields[2][2].addFollowingFields((PathField) fields[2][3]);
        fields[2][3].addFollowingFields((PathField) fields[2][4]);
        fields[2][4].addFollowingFields((PathField) fields[3][4]);
        fields[3][4].addFollowingFields((PathField) fields[4][4]);

        fields[4][4].addFollowingFields((PathField) fields[5][4]);
        fields[5][4].addFollowingFields((PathField) fields[5][3]);
        fields[5][3].addFollowingFields((PathField) fields[5][2]);
        fields[5][2].addFollowingFields((PathField) fields[5][1]);
        fields[5][1].addFollowingFields((PathField) fields[5][0]);

        //setting the current field from the player
        player= new Player();
        player.setCurrentField((PathField) fields[0][0]);
    }

    //Test for the maxPath Length from the Beginning to the End
    @Test
    public void maxPathLength_returnsMaxPathField (){
        int maxPath= player.maxPathLength(player.getCurrentField());
        assertEquals(14, maxPath);
    }

    //Test for the minPath Length from the Beginning to the End
    @Test
    public void minPathLength_returnsMinPathField (){
        int minPath= player.minPathLength(player.getCurrentField());
        assertEquals(6,minPath);
    }

    
}
