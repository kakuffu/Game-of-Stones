package GameOfStones;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;

@Slf4j
public class GameOfStonesApplication extends Application {

    @Override
    public void start(Stage Stage) throws Exception {
        log.info("Starting application...");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXMLs/WelcomeScene.fxml")));
        Stage.setTitle("Welcome to the Game Of Stones!");
        Stage.setResizable(false);
        Stage.setScene(new Scene(root));
        Stage.show();
    }
}
