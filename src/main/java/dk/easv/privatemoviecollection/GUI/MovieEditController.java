package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.BLL.MovieException;
import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MovieEditController {

    @FXML private VBox checkListsLeft;
    @FXML private VBox checkListsRight;
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
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private Movie editingMovie;

    public MovieEditController() {
    }

    public void init(String genres) {
        generateCheckboxes(genres);
    }

    private void generateCheckboxes(String genres) {
        if (genres == null) genres = "";

        List<Genre> genreList = model.getGenres();
        boolean left = true;

        for (Genre genre : genreList) {
            CheckBox checkBox = new CheckBox(genre.getName());
            checkBox.setId(String.valueOf(genre.getId()));

            if (genres.contains(genre.getName())) {
                checkBox.setSelected(true);
            }

            if (left) {
                checkListsLeft.getChildren().add(checkBox);
            } else {
                checkListsRight.getChildren().add(checkBox);
            }

            left = !left;
            checkBoxes.add(checkBox);
        }
    }

    public void showCreateMode() {

        lblHeader.setText("Add Movie");
        createMode = true;
        btnSave.setText("Save Movie");
    }

    public void showEditMode(Movie movie) {
        lblHeader.setText("Edit Movie");
        createMode = false;
        editingMovie = movie;
        btnSave.setText("Save Changes");

        this.currentMovie = movie;

        // Fill text fields
        txtTitle.setText(movie.getName());
        txtImdbRating.setText(String.valueOf(movie.getImdbRating()));
        txtPersonalRating.setText(String.valueOf(movie.getPersonalRating()));
        txtFilePath.setText(movie.getFilePath());

        // Generate genre checkboxes
        generateCheckboxes(movie.getGenresString());
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setMovie(Movie movie) {
        this.currentMovie = movie;

        txtTitle.setText(movie.getName());

        //cbGenre.setText(movie.getGenresString());
        txtImdbRating.setText(String.valueOf(movie.getImdbRating()));
        txtPersonalRating.setText(String.valueOf(movie.getPersonalRating()));
        txtFilePath.setText(movie.getFilePath());
    }

    @FXML
    private void onSave() {
        String title = txtTitle.getText().trim();
        List<Integer> genreIds = new ArrayList<>();
        String genreString = "";
        for(CheckBox checkBox : checkBoxes){
            if(checkBox.isSelected()){
                genreIds.add(Integer.parseInt(checkBox.getId()));
                if(genreString == ""){
                    genreString = checkBox.getText();
                } else {
                    genreString += ", " + checkBox.getText();
                }
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
            movie.setGenresString(genreString);
            System.out.println(movie.toString());
            model.createMovie(movie);
        } else {
            currentMovie.setName(title);
            currentMovie.setGenres(genreIds);
            currentMovie.setImdbRating(imdbRating);
            currentMovie.setPersonalRating(personalRating);
            currentMovie.setFilePath(filePath);

            model.updateMovie(currentMovie);

            model.updateGenres(currentMovie, buildGenreList());
        }
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    private ArrayList<Genre> buildGenreList() {
        ArrayList<Genre> genres = new ArrayList<>();
        for (CheckBox cb : checkBoxes) {
            if (cb.isSelected()) {
                genres.add(new Genre(Integer.parseInt(cb.getId()), cb.getText()));
            }
        }
        return genres;
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
