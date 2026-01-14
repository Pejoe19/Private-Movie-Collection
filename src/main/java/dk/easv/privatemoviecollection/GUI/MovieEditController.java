package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.BLL.MovieManager;
import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MovieEditController {

    @FXML private HBox hBoxMovieFound;
    @FXML private Text txtMovieApiTitle;
    @FXML private ImageView ivMovie;
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
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private Movie editingMovie;
    private Movie movieFound;

    public MovieEditController() {
    }

    public void initialize(){
        if(createMode){
            txtTitle.textProperty().addListener((observable, oldValue, newValue) -> searchApiMovie(newValue));
        }

        hBoxMovieFound.setOnMouseClicked(movie -> loadMovieData());
    }

    public void init(String genres) {generateCheckboxes(new ArrayList<>());}

    private void generateCheckboxes(ArrayList<Genre> movieGenres) {
        List<Genre> allGenres = model.getGenres();
        boolean left = true;

        for (Genre genre : allGenres) {
            CheckBox cb = new CheckBox(genre.getName());
            cb.setId(String.valueOf(genre.getId()));

            if (movieGenres.stream().anyMatch(g -> g.getId() == genre.getId())) {
                cb.setSelected(true);
            }

            if (left) checkListsLeft.getChildren().add(cb);
            else checkListsRight.getChildren().add(cb);

            left = !left;
            checkBoxes.add(cb);
        }
    }

    private void searchApiMovie(String titleString) {
        String titleText = titleString.toLowerCase().trim();
        Movie movieFound = model.getMovieData(titleText);
        if(movieFound != null){
            if(movieFound.getFilePath() != null){
                this.movieFound = movieFound;
                hBoxMovieFound.setVisible(true);
                ivMovie.setImage(new Image(movieFound.getFilePath(),true));
                txtMovieApiTitle.setText(movieFound.getName());
            }
        } else {
            hBoxMovieFound.setVisible(false);
            txtMovieApiTitle.setText("");
            this.movieFound = null;
        }
    }

    private void loadMovieData() {
        System.out.println(movieFound.getGenres());
        for(int genreId : movieFound.getGenres()){
            for(CheckBox checkBox : checkBoxes){
                if(genreId == Integer.parseInt(checkBox.getId())){
                    checkBox.setSelected(true);
                }
            }
        }
        txtFilePath.setText(movieFound.getFilePath());
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

        ArrayList<Genre> movieGenres = model.getMovieGenres(movie);
        generateCheckboxes(movieGenres);
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setMovie(Movie movie) {
        this.currentMovie = movie;

        txtTitle.setText(movie.getName());
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
            Movie movie;
            if (movieFound != null){
                movie = movieFound;
            } else {
                movie = new Movie(title, genreIds, imdbRating, personalRating, filePath);
            }
            movie.setGenres(genreIds);
            movie.setGenresString(genreString);
            movie.setImdbRating(Float.parseFloat(txtImdbRating.getText()));
            movie.setPersonalRating(Float.parseFloat(txtPersonalRating.getText()));
            model.createMovie(movie);
        } else {
            currentMovie.setName(title);
            currentMovie.setImdbRating(imdbRating);
            currentMovie.setPersonalRating(personalRating);
            currentMovie.setFilePath(filePath);

            // Update movie info in DB
            model.updateMovie(currentMovie);

            // Compare genres
            ArrayList<Genre> newGenres = buildGenreList();
            ArrayList<Genre> oldGenres = model.getMovieGenres(currentMovie);

            if (!newGenres.equals(oldGenres)) {
                model.updateGenres(currentMovie, newGenres);
            }
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
