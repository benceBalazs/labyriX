import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.esotericsoftware.kryonet.Client;
import com.labyrix.game.Defusions.TrapRender;
import com.labyrix.game.ENUMS.TurnValue;
import com.labyrix.game.Models.ArrowActors;
import com.labyrix.game.Models.Board;
import com.labyrix.game.Models.HudButton;
import com.labyrix.game.Models.Player;
import com.labyrix.game.Models.UncoverRender;
import com.labyrix.game.Network.ClientNetworkHandler;
import com.labyrix.game.TurnLogic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(GdxTestRunner.class)
public class TestTurnLogic {

    @Mock
    private Stage stage;

    @Mock
    private Client client;
    @Mock
    private UncoverRender uncoverRender;
    @Mock
    private ArrowActors arrowActors;
    @Mock
    private TrapRender trapRender;
    @Mock
    private Texture dicerollImg;
    @Mock
    private ArrayList<Player> players = new ArrayList<Player>();
    @Mock
    private HudButton uncoverButton;
    @Mock
    private Player player;
    @Mock
    private Camera camera;
    @Mock
    private Board board;


    @InjectMocks
    private TurnLogic turnlogic;

    @Before
    public void setup() {
       // Mockito.when(new TrapRender(camera)).thenReturn(trapRender);
        //Mockito.when(new UncoverRender(camera)).thenReturn(uncoverRender);
        //Mockito.when(new ArrowActors(camera)).thenReturn(arrowActors);
        //Mockito.when(new HudButton()).thenReturn(uncoverButton);
        //Mockito.when(ClientNetworkHandler.getInstance().getClient()).thenReturn(client);

        turnlogic = new TurnLogic();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void turnLogic_RollDice_TurnDoneNotFalse_ThrowsException() {
        turnlogic.setTurnDone(true);
        Mockito.when(player.getTurnValue()).thenReturn(TurnValue.DICEROLL);

        RuntimeException msg = assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                turnlogic.rollDice();
            }
        });
        assertEquals("DiceRoll - Wrong TurnValue or Turn not done", msg.getMessage());
    }

    @Test
    public void turnLogic_RollDice_WrongTurnValue_ThrowsException() {
        turnlogic.setTurnDone(false);
        Mockito.when(player.getTurnValue()).thenReturn(TurnValue.PATHSELECTION);

        RuntimeException msg = assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                turnlogic.rollDice();
            }
        });

        assertEquals("DiceRoll - Wrong TurnValue or Turn not done", msg.getMessage());
    }


    @Test
    public void turnLogic_Move_TurnDoneNotFalse_ThrowsException() {
        turnlogic.setTurnDone(true);
        Mockito.when(player.getTurnValue()).thenReturn(TurnValue.MOVEMENT);

        RuntimeException msg = assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                turnlogic.move();
            }
        });
        assertEquals("Move - Wrong TurnValue or Turn not done", msg.getMessage());
    }


    @Test
    public void turnLogic_Move_WrongTurnValue_ThrowsException() {
        turnlogic.setTurnDone(false);
        Mockito.when(player.getTurnValue()).thenReturn(TurnValue.PATHSELECTION);

        RuntimeException msg = assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                turnlogic.move();
            }
        });
        assertEquals("Move - Wrong TurnValue or Turn not done", msg.getMessage());
    }



    @Test
    public void turnLogic_SelectPath_TurnDoneNotFalse_ThrowsException() {
        turnlogic.setTurnDone(true);
        Mockito.when(player.getTurnValue()).thenReturn(TurnValue.PATHSELECTION);

        RuntimeException msg = assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                turnlogic.selectPath();
            }
        });
        assertEquals("PathSelection - Wrong TurnValue or Turn not done", msg.getMessage());
    }


    @Test
    public void turnLogic_SelectPath_WrongTurnValue_ThrowsException() {
        turnlogic.setTurnDone(false);
        Mockito.when(player.getTurnValue()).thenReturn(TurnValue.MOVEMENT);

        RuntimeException msg = assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                turnlogic.selectPath();
            }
        });
        assertEquals("PathSelection - Wrong TurnValue or Turn not done", msg.getMessage());
    }


    @Test
    public void turnLogic_CheckTrap_TurnDoneNotFalse_ThrowsException() {
        turnlogic.setTurnDone(true);
        Mockito.when(player.getTurnValue()).thenReturn(TurnValue.TRAPCHECK);

        RuntimeException msg = assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                turnlogic.checkTrap();
            }
        });
        assertEquals("checkTrap - Wrong TurnValue or Turn not done", msg.getMessage());
    }


    @Test
    public void turnLogic_CheckTrap_WrongTurnValue_ThrowsException() {
        turnlogic.setTurnDone(false);
        Mockito.when(player.getTurnValue()).thenReturn(TurnValue.PATHSELECTION);

        RuntimeException msg = assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                turnlogic.checkTrap();
            }
        });
        assertEquals("checkTrap - Wrong TurnValue or Turn not done", msg.getMessage());
    }

    @Test
    public void turnLogic_doServerStuff_TurnDoneFalse_ThrowsException() {
        turnlogic.setTurnDone(false);
        turnlogic.setSentDataToServer(false);

        RuntimeException msg = assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                turnlogic.doServerStuff();
            }
        });
        assertEquals("Turn not Done or data already sent to server", msg.getMessage());
    }

    @Test
    public void turnLogic_doServerStuff_SentDataToServerTrue_ThrowsException() {
        turnlogic.setTurnDone(true);
        turnlogic.setSentDataToServer(true);

        RuntimeException msg = assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                turnlogic.doServerStuff();
            }
        });
        assertEquals("Turn not Done or data already sent to server", msg.getMessage());
    }


    @After
    public void teardown() {
        turnlogic = null;
    }


}
