package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.Be.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class MovieEditController {

    @FXML private Label lblHeader;
    @FXML private TextField txtTitle;
    @FXML private TextField txtImdbRating;
    @FXML private TextField txtPersonalRating;
    @FXML private TextField txtFilePath;
    @FXML private TextField txtGenres;

    private Movie editingMovie;

    public void showCreateMode() {
        lblHeader.setText("Add Movie");
    }

    public void showEditMode(Movie movie) {
        lblHeader.setText("Edit Movie");
        editingMovie = movie;

        txtTitle.setText(movie.getName());
        txtImdbRating.setText(String.valueOf(movie.getImdbRating()));
        txtPersonalRating.setText(String.valueOf(movie.getPersonalRating()));
        txtFilePath.setText(movie.getFilePath());
    }
}
