package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.BLL.MovieException;
import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MovieEditController {

    @FXML private ChoiceBox cbGenre;
    @FXML private Label lblHeader;
    @FXML private TextField txtTitle;
    @FXML private TextField txtImdbRating;
    @FXML private TextField txtPersonalRating;
    @FXML private TextField txtFilePath;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private Model model;
    private boolean createMode = true;
    private Movie currentMovie;
    private Movie editingMovie;

    public MovieEditController() {
    }

   public void init(){
       List<String> genres = model.getGenreNames();
       cbGenre.setItems(FXCollections.observableList(genres));
   }

    public void showCreateMode() {

        lblHeader.setText("Add Movie");
        createMode = true;
        btnSave.setText("Save Movie");
    }

    public void showEditMode(Movie movie) {
        lblHeader.setText("Edit Movie");
        editingMovie = movie;
        createMode = false;
        btnSave.setText("Save Changes");
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setMovie(Movie movie) {
        this.currentMovie = movie;

        txtTitle.setText(movie.getName());
        cbGenre.setValue(movie.getGenresString());
        txtImdbRating.setText(String.valueOf(movie.getImdbRating()));
        txtPersonalRating.setText(String.valueOf(movie.getPersonalRating()));
        txtFilePath.setText(movie.getFilePath());
    }



    @FXML
    private void onSave() {

        String title = txtTitle.getText().trim();
        List<Integer> genreIds = new ArrayList<>();
        String genres = cbGenre.getValue().toString();
        List<Genre> allGenres = model.getGenres();
        for(Genre genre : allGenres) {
            if(genre.getName().equals(genres)) {
                genreIds.add(genre.getId());
            }
        }

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
            Movie movie = new Movie(title, genreIds, imdbRating, personalRating, filePath);
            model.createMovie(movie);
        } else {
            currentMovie.setName(title);
            currentMovie.setGenres(genreIds);
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
