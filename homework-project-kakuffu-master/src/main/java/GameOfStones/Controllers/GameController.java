package GameOfStones.Controllers;

import GameOfStones.GameState.GameState;
import GameOfStones.Models.GameOfStonesModel;
import GameOfStones.Results.CreateScoreFile;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.Optional;


@Slf4j //for the log.info
public class GameController {
    //this object will be used to do moves
    private final GameOfStonesModel model = new GameOfStonesModel();

    private String player1;
    private String player2;
    private String winnerName;
    private GameState gameState;
    private int turnFlag = 0;
    private String turn;
    private boolean madeMove;
    private int countClick = 1;
    private boolean directionButtonPressed = false;
    private String direction = "";
    private int moveCount = 0;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);


    @FXML
    private GridPane gameBoardPane;
    @FXML
    private TextField numberOfStonesField;
    @FXML
    private Label turnLabel;
    @FXML
    private Label directionButtonError;
    @FXML
    private Button giveUpButton;


    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public int getMoveCount() {
        return moveCount;
    }

    @FXML
    private void initialize() {
        for (int i = 0; i < gameBoardPane.getRowCount(); i++) {
            for (int j = 0; j < gameBoardPane.getColumnCount(); j++) {
                var square = createCell(i, j);
                gameBoardPane.add(square, j, i);
            }
        }
        numberOfStonesField.textProperty().bind(model.numberOfStonesProperty().asString());
        madeMove = false;
        model.gameOverProperty().addListener(this::handleGameOver);
        resetGame();
    }


    private void resetGame() {
        gameState = new GameState();
        turnFlag = 0;
        directionButtonPressed = false;
        direction = "";
        Platform.runLater(() -> setTurn(getPlayer1()));
        Platform.runLater(() -> turnLabel.setText(turn + "'s turn!"));
        log.info("Game is resetting...");
    }

    private void handleGameOver(ObservableValue observableValue, Boolean oldGOValue, Boolean newGOValue) {
        if (newGOValue) {
            setWinnerName(getTurn());
            createResult();
            Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
            gameOverAlert.setTitle(getTurn() + " WON");
            gameOverAlert.setHeaderText(getTurn() + " took the last stone!");
            gameOverAlert.showAndWait();
            giveUpButton.setId("scoreboardButton");
            giveUpButton.setText("Scoreboard"); //changes give up button to scoreboard button when game is over
            log.info("Game is over!" + winnerName + " won the game!");
        }
    }

    private StackPane createCell(int i, int j) {
        var cell = new StackPane();
        cell.getStyleClass().add("cell");
        var stone = new Circle(50);

        stone.fillProperty().bind(
                new ObjectBinding<>() {
                    {
                        super.bind(model.cellProperty(i, j));
                    }

                    @Override
                    protected Paint computeValue() {
                        return switch (model.cellProperty(i, j).get()) {
                            case STONE -> Color.DARKSLATEGRAY;
                            case EMPTY -> Color.TRANSPARENT;
                        };
                    }
                }
        );
        cell.getChildren().add(stone);
        cell.setOnMouseClicked(this::handleMouseClick);
        return cell;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var cell = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(cell);
        var col = GridPane.getColumnIndex(cell);
        switch (model.cellProperty(row, col).get()) {
            case STONE -> {
                if (countClick == 1) {
                    if (directionButtonPressed) {
                        model.deleteStone(row, col);
                        if (Objects.equals(direction, "right/left")) {
                            gameState.addOtoRightLeft(row, col);
                        } else if (Objects.equals(direction, "up/down")) {
                            gameState.addOtoUpDown(row, col);
                        }
                        countClick = countClick + 1;
                        madeMove = true;
                        directionButtonError.setText("");
                    } else {
                        directionButtonError.setText("Choose Direction!");
                    }
                } else if (countClick == 2) {
                    if (gameState.isValidMove(row, col)) {
                        model.deleteStone(row, col);
                        gameState.removeO();
                        if (Objects.equals(direction, "right/left")) {
                            gameState.addOtoRightLeft(row, col);
                        } else if (Objects.equals(direction, "up/down")) {
                            gameState.addOtoUpDown(row, col);
                        }
                        countClick = countClick + 1;
                        madeMove = true;
                    }
                } else if (countClick == 3) {
                    if (gameState.isValidMove(row, col)) {
                        model.deleteStone(row, col);
                        gameState.removeO();
                        if (Objects.equals(direction, "right/left")) {
                            gameState.addOtoRightLeft(row, col);
                        } else if (Objects.equals(direction, "up/down")) {
                            gameState.addOtoUpDown(row, col);
                        }
                        countClick = countClick + 1;
                        madeMove = true;
                    }
                } else if (countClick == 4) {
                    if (gameState.isValidMove(row, col)) {
                        model.deleteStone(row, col);
                        gameState.removeO();
                        madeMove = true;
                        handleTurnButton();
                    }
                }
                moveCount++;
            }
            case EMPTY -> {
                log.warn("Cell is empty!");
            }
        }

    }

    private void handleTurnButton() {
        if (madeMove) {
            turnFlag = turnFlag + 1;
            if (turnFlag % 2 == 0) {
                setTurn(getPlayer1());
                turnLabel.setText(turn + "'s turn!");
                log.info(turn + "'s turn!");
            } else {
                setTurn(getPlayer2());
                turnLabel.setText(turn + "'s turn!");
                log.info(turn + "'s turn!");
            }
        }
        madeMove = false;
        countClick = 1;
        directionButtonPressed = false;
        direction = "";
    }

    @FXML
    private void handleGiveUp() {
        if (Objects.equals(turn, player1)) {
            Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
            gameOverAlert.setTitle("GAME OVER");
            gameOverAlert.setHeaderText(getPlayer1() + " gave up!");
            gameOverAlert.showAndWait();
        } else {
            Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
            gameOverAlert.setTitle("GAME OVER");
            gameOverAlert.setHeaderText(getPlayer2() + " gave up!");
            gameOverAlert.showAndWait();
        }
    }


    //using one method for all the buttons
    @FXML
    private void buttonPressed(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        SceneSwitchController ssc = new SceneSwitchController();

        if (button.getId().equals("giveUpButton")) {
            handleGiveUp();
            ssc.switchToWelcomeScene(actionEvent);
            log.info(turn + "gave up!");
        } else if (button.getId().equals("scoreboardButton")) {
            ssc.switchToScoreboard(actionEvent);
            log.info("Loading Scoreboard...");
        } else if (button.getId().equals("endTurnButton")) {
            handleTurnButton();
        } else if (button.getId().equals("playAgainButton")) {
            Alert ConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            ConfirmationAlert.initModality(Modality.APPLICATION_MODAL);
            ConfirmationAlert.setTitle("WARNING!");
            ConfirmationAlert.setHeaderText("Do you want to play again?");
            Optional<ButtonType> decision = ConfirmationAlert.showAndWait();
            if (decision.get() == ButtonType.OK) {
                ssc.switchToWelcomeScene(actionEvent);
                initialize();
                log.info("Loading main menu...");
            } else if (decision.get() == ButtonType.CANCEL) {
                log.info("Play Again Canceled!");
            }
        } else if (button.getId().equals("rightLeftButton")) {
            directionButtonPressed = true;
            direction = "right/left";
            log.info("Player chose to move right/left");
        } else if (button.getId().equals("upDownButton")) {
            directionButtonPressed = true;
            direction = "up/down";
            log.info("Player chose to move up/down");
        }
    }

    private void createResult() {
        new CreateScoreFile().addScore(player1, player2, ZonedDateTime.now().format(dateTimeFormatter), winnerName, getMoveCount());
        log.info("Creating and saving the results of the game...");
    }

}
