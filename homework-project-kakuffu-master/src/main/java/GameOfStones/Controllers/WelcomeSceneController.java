package GameOfStones.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class WelcomeSceneController {

    private final FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TextField player1Field;
    @FXML
    private TextField player2Field;

    @FXML
    private Label errorLabel1;
    @FXML
    private Label errorLabel2;

    @FXML
    public void buttonPressed(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        SceneSwitchController ssc = new SceneSwitchController();
        if (button.getId().equals("startButton")) {
            if (player1Field.getText().isEmpty() && player2Field.getText().isEmpty()) {
                errorLabel1.setText("Enter Player 1's name!");
                errorLabel2.setText("Enter Player 2's name!");
            } else if (player1Field.getText().isEmpty())
                errorLabel1.setText("Enter Player 1's name!");
            else if (player2Field.getText().isEmpty())
                errorLabel2.setText("Enter Player 2's name!");
            else {
                fxmlLoader.setLocation(getClass().getResource("/FXMLs/GameScene.fxml"));
                Parent root = fxmlLoader.load();
                fxmlLoader.<GameController>getController().setPlayer1(player1Field.getText());
                fxmlLoader.<GameController>getController().setPlayer2(player2Field.getText());
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                log.info("The player1 name is set to {} and player2 name is set to {}, loading game scene", player1Field.getText(), player2Field.getText());
            }
        } else if (button.getId().equals("scoreboardButton")) {
            ssc.switchToScoreboard(actionEvent);
            log.info("Loading the Scoreboard...");
        } else if (button.getId().equals("exitButton")) {
            Platform.exit();
        }

    }

}
