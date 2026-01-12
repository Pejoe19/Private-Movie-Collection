package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.Be.Movie;
import dk.easv.privatemoviecollection.GUI.Model;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.File;

public class MovieEditController {

    @FXML private Label lblHeader;
    @FXML private TextField txtTitle;
    @FXML private TextField txtImdbRating;
    @FXML private TextField txtPersonalRating;
    @FXML private TextField txtFilePath;
    @FXML private TextField txtCategories;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private Model model;
    private boolean createMode = true;
    private Movie currentMovie;

    public void showCreateMode() {

        lblHeader.setText("Add Movie");
        createMode = true;
        btnSave.setText("Save Movie");
    }

    public void showEditMode() {
        lblHeader.setText("Edit Movie");
        createMode = false;
        btnSave.setText("Save Changes");
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setMovie(Movie movie) {
        this.currentMovie = movie;

        txtTitle.setText(movie.getName());
        txtCategories.setText(movie.getCategoriesString());
        txtImdbRating.setText(String.valueOf(movie.getImdbRating()));
        txtPersonalRating.setText(String.valueOf(movie.getPersonalRating()));
        txtFilePath.setText(movie.getFilePath());
    }



    @FXML
    private void onSave() {

        String title = txtTitle.getText().trim();
        String categories = txtCategories.getText().trim();
        String filePath = txtFilePath.getText().trim();

        float imdbRating;
        float personalRating;

        try {
            imdbRating = Float.parseFloat(txtImdbRating.getText().trim());
            personalRating = Float.parseFloat(txtPersonalRating.getText().trim());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Ratings must be numbers").showAndWait();
            return;
        }

        if (createMode) {
            Movie movie = new Movie(title, categories, imdbRating, filePath);
            movie.setPersonalRating(personalRating);
            model.createMovie(movie);
        } else {
            currentMovie.setName(title);
            currentMovie.setCategories(categories);
            currentMovie.setImdbRating(imdbRating);
            currentMovie.setPersonalRating(personalRating);
            currentMovie.setFilePath(filePath);

            model.updateMovie(currentMovie);
        }

        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void onCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
