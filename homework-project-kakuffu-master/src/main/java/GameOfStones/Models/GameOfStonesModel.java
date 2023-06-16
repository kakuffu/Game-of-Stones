package GameOfStones.Models;

import javafx.beans.property.*;

public class GameOfStonesModel {

    public static int BOARD_SIZE = 4;

    private final ReadOnlyObjectWrapper<Cell>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];
    private final ReadOnlyIntegerWrapper numberOfStones = new ReadOnlyIntegerWrapper();
    private final ReadOnlyBooleanWrapper gameOver = new ReadOnlyBooleanWrapper();


    public ReadOnlyObjectProperty<Cell> cellProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty numberOfStonesProperty(){
        return numberOfStones.getReadOnlyProperty();
    }

    public ReadOnlyBooleanProperty gameOverProperty(){
        return gameOver.getReadOnlyProperty();
    }

    /**
     * It is a constructor that creates the board array to be used for placing circles on grid.
     * Also, it is used to set the initial number of stones that can be removed and the number
     * of stones that has to be reached to end the game.
     */
    public GameOfStonesModel() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<>(Cell.STONE);
            }
        }
        numberOfStones.set(16);
        gameOver.bind(numberOfStones.isEqualTo(0));
    }

    /**
     * It changes the color of the circle on cell from gray to transparent,
     * illustrating the deletion of stone
     *
     * @param i Row of the cell clicked.
     * @param j Column of the cell clicked.
     */
    public void deleteStone(int i, int j) {
        //dummy number is used to make sure the number of coins calculated AFTER it represented on screen.
        boolean dummyNum = false;
        board[i][j].set(
                switch (board[i][j].get()) {
                    case STONE  -> {
                        dummyNum=true;
                        yield Cell.EMPTY;}
                    case EMPTY  -> Cell.EMPTY;
                }
        );
        if(dummyNum){
            numberOfStones.set(numberOfStones.get()-1);
        }
    }

}
