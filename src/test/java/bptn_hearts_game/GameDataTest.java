package bptn_hearts_game;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bptn.bptn_hearts_game.Card;
import com.bptn.bptn_hearts_game.Game;
import com.bptn.bptn_hearts_game.Player;

public class GameDataTest {
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
    public void testSaveGameData() {
        // Simulate a game and update scores
        players.get(0).addTurn(List.of(
            new Card("Spades", 12), // Queen of Spades
            new Card("Hearts", 2),
            new Card("Hearts", 3),
            new Card("Hearts", 4)
        ));
        game.updateScores();
        game.saveGameData();

        // Verify that the game_data.txt file contains the expected data
        try (BufferedReader reader = new BufferedReader(new FileReader("game_data.txt"))) {
            String line;
            boolean foundRoundData = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("Round 1")) {
                    foundRoundData = true;
                    break;
                }
            }
            assertTrue(foundRoundData, "Game data should be saved and contain 'Round 1' details.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveScores() {
        // Simulate a game and update scores
        players.get(0).addTurn(List.of(
            new Card("Spades", 12), // Queen of Spades
            new Card("Hearts", 2),
            new Card("Hearts", 3),
            new Card("Hearts", 4)
        ));
        game.updateScores();
        game.savesScores();

        // Verify that the scores.txt file contains the expected data
        try (BufferedReader reader = new BufferedReader(new FileReader("scores.txt"))) {
            String line;
            boolean foundScoreData = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("Player1: 16")) {
                    foundScoreData = true;
                    break;
                }
            }
            assertTrue(foundScoreData, "Scores should be saved and contain 'Player1: 16' details.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
