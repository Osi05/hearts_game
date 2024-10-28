package bptn_hearts_game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bptn.bptn_hearts_game.Card;
import com.bptn.bptn_hearts_game.Game;
import com.bptn.bptn_hearts_game.Player;

public class GameTest {
    private Game game;
    private ArrayList<Player> players;

    @BeforeEach
    public void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Player1", "easy"));
        players.add(new Player("Player2", "easy"));
        players.add(new Player("Player3", "easy"));
        players.add(new Player("Player4", "easy"));

        game = new Game(players);
    }

    @Test
    public void testUpdateScores() {
        // Simulate a turn where Player1 gets the Queen of Spades and 3 Hearts
        players.get(0).addTurn(List.of(
            new Card("Spades", 12), // Queen of Spades
            new Card("Hearts", 2),
            new Card("Hearts", 3),
            new Card("Hearts", 4)
        ));

        // Simulate a turn where Player2 gets 2 Hearts
        players.get(1).addTurn(List.of(
            new Card("Hearts", 5),
            new Card("Hearts", 6)
        ));

        // Call the method to update scores
        game.updateScores();

        // Verify the scores
        assertEquals(16, game.getScores()[0]); // Player1: 13 for Queen of Spades + 3 Hearts
        assertEquals(2, game.getScores()[1]);  // Player2: 2 Hearts
        assertEquals(0, game.getScores()[2]);  // Player3: No points
        assertEquals(0, game.getScores()[3]);  // Player4: No points
    }
}
