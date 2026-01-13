package dk.easv.privatemoviecollection;

import dk.easv.privatemoviecollection.GUI.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 645, 750);
        MainController controller = fxmlLoader.getController();
        stage.setScene(scene);
        controller.init(scene);
        stage.show();
    }
}
