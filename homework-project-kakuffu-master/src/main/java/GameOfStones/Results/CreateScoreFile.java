package GameOfStones.Results;

import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CreateScoreFile {

    private GameScores scoresList = new GameScores();

    public CreateScoreFile() {
        File file = new File("gameScores.xml");
        if (file.exists()) {
            try {
                this.scoresList = JAXBHelper.fromXML(GameScores.class, new FileInputStream("gameScores.xml"));
            } catch (JAXBException | FileNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                if (file.createNewFile()) {
                    log.debug("Scores log file created");
                } else {
                    log.debug("Scores log file exists");
                }
            } catch (IOException ioException) {
                    ioException.printStackTrace();
            }
            scoresList.setGameScoreList(new ArrayList<>());
        }
    }

    /**
     * For adding score to the GameResult that then will be displayed on scoreboard.
     *
     * @param player1 - The first players name.
     * @param player2 - The second players name.
     * @param dateStarted - The starting date and time of the game.
     * @param winnerName - The name of the winner of the game.
     * @param moves - The total number of moves made during the game.
     */
    public void addScore(String player1, String player2, String dateStarted, String winnerName, int moves) {
        List<GameResult> scores = scoresList.getGameScoreList();
        System.out.println(player1 + player2 + dateStarted + winnerName + moves);
        scores.add(new GameResult(player1, player2, dateStarted, winnerName, moves));
        scoresList.setGameScoreList(scores);
        try {
            JAXBHelper.toXML(scoresList, new FileOutputStream("gameScores.xml"));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<GameResult> getGameScoreList() {
        return scoresList.getGameScoreList();
    }

}
