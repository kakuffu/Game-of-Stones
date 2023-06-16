package GameOfStones.GameState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class GameState {

    /**
     * Creates the board of 4x4 cells.
     */
    private char[][] board = new char[4][4];


    /**
     * Adds 'o' to the cell on its right/left to make them valid for the next move.
     *
     * @param row Row of the cell clicked.
     * @param col Column of the cell clicked.
     */

    public void addOtoRightLeft(int row, int col) {
        char nextAvailableCell = 'o';
        if (col == 0) {
            board[row][col + 1] = nextAvailableCell;
        }
        if (col == 3) {
            board[row][col - 1] = nextAvailableCell;
        }
        if (col != 0 && col != 3) {
            board[row][col + 1] = nextAvailableCell;
            board[row][col - 1] = nextAvailableCell;
        }
        Arrays.stream(board).forEach(System.out::println);
        log.info("Right and Left valid cell are set");
    }

    /**
     * Adds 'o' to the cell on its top/bottom to make them valid for the next move
     *
     * @param row Row of the cell clicked.
     * @param col Column of the cell clicked.
     */

    public void addOtoUpDown(int row, int col) {
        char nextAvailableCell = 'o';
        if (row == 0) {
            board[row + 1][col] = nextAvailableCell;
        }
        if (row == 3) {
            board[row - 1][col] = nextAvailableCell;
        }
        if (row != 0 && row != 3) {
            board[row + 1][col] = nextAvailableCell;
            board[row - 1][col] = nextAvailableCell;
        }
        Arrays.stream(board).forEach(System.out::println);
        log.info("Up and Down valid cell are set");
    }

    /**
     * Checks if the cell pressed has 'o' on it.
     *
     * @param row Row of the cell clicked.
     * @param col Column of the cell clicked.
     * @return {@code true} if move is valid, {@code false} otherwise.
     */

    public boolean isValidMove(int row, int col) {
        boolean move = false;
        if (board[row][col] == 'o') {
            move = true;
        }
        return move;
    }

    /**
     * Removes 'o' to stop player from pressing on cells.
     */
    public void removeO() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = ' ';
            }
        }
        Arrays.stream(board).forEach(System.out::println);
        log.info("Next valid move is set!");
    }
}
