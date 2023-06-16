package GameState;

import GameOfStones.GameState.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {
    private final GameState gameState = new GameState();

    @Test
    void testAddOtoRightLeft() {
        gameState.addOtoRightLeft(2, 2);
        assertEquals(new GameState(new char[][]{
                {'x', 'x', 'x', 'x'},
                {'x', 'x', 'x', 'x'},
                {'x', 'o', 'x', 'o'},
                {'x', 'x', 'x', 'x'}
        }), gameState);
    }

    @Test
    void addOtoUpDownTest() {

    }

    @Test
    void isValidMoveTest() {
        assertTrue(new GameState(new char[][]{
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', 'o', ' ', 'o'},
                {' ', ' ', ' ', ' '}
        }).isValidMove(2,2));
        assertTrue(new GameState(new char[][]{
                {' ', ' ', ' ', ' '},
                {' ', ' ', 'o', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', 'o', ' '}
        }).isValidMove(2,2));
    }


    @Test
    void removeOTest() {
        gameState.removeO();
        assertEquals(new GameState((new char[][]{
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        })), gameState);
    }
}
