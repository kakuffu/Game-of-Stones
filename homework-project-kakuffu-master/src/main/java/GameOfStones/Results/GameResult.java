package GameOfStones.Results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * This class is used to declare the parameters that will be transferred to scoreboard.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class GameResult {
    private String player1;
    private String player2;
    private String dateStarted;
    private String winnerName;
    private int moves;
}
