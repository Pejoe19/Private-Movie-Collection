package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.Be.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ChangeRatingController {
    @FXML private TextField txtImdbRating;
    @FXML private TextField txtPersonalRating;

    private Model model;
    private Movie currentMovie;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    public void init(Movie movie) {
        this.currentMovie = movie;
        txtImdbRating.setText(String.valueOf(movie.getImdbRating()));
        txtPersonalRating.setText(String.valueOf(movie.getPersonalRating()));
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    private void onSave() {
        try {
            float newRating = Float.parseFloat(txtPersonalRating.getText().trim());

            model.updatePersonalRating(currentMovie, newRating);

            ((Stage) btnSave.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Rating must be a number").showAndWait();
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtImdbRating.getScene().getWindow();
        stage.close();
    }
}
