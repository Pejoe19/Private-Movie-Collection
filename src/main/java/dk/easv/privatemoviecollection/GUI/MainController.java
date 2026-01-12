package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.BLL.MovieException;
import dk.easv.privatemoviecollection.Be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.io.IOException;
import java.io.File;


public class MainController {
    @FXML
    private TableColumn tblColGenre;
    @FXML
    private TableColumn tblColIMDbRating;
    @FXML
    private TableColumn tblColPRating;
    @FXML
    private TableColumn<Movie, Void> tblColButton;
    @FXML
    private TableColumn tblColImage;
    @FXML
    private TableView<Movie> tblView;
    @FXML
    private TableColumn<Movie, String> tblColName;
    @FXML
    private CheckBox CBTitle;
    @FXML
    private CheckBox CBCategory;
    @FXML
    private CheckBox CBMIMDbRating;
    @FXML
    private CheckBox CBMPersonalRating;
    @FXML
    private TextField TFSearchF;
    @FXML
    private Button btnCancel;

    Model model = new Model();
    Image defaultImage = new Image(getClass().getResourceAsStream("/dk/easv/privatemoviecollection/pictures/3d-cinema-popcorn-cup.jpg"));
    private FilteredList<Movie> filteredMovies;

    public MainController() throws MovieException {
    }

    @FXML
    public void initialize() {
        setupTable();

        filteredMovies = new FilteredList<>(
                model.getMovies(),
                movie -> true
        );
        SortedList<Movie> sortedMovies = new SortedList<>(filteredMovies);
        sortedMovies.comparatorProperty().bind(tblView.comparatorProperty());

        tblView.setItems(sortedMovies);

        setupFiltering();
    }

    private void setupFiltering() {
        TFSearchF.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        CBTitle.selectedProperty().addListener((obs, o, n) -> applyFilters());
        CBCategory.selectedProperty().addListener((obs, o, n) -> applyFilters());
        CBMIMDbRating.selectedProperty().addListener((obs, o, n) -> applyFilters());
        CBMPersonalRating.selectedProperty().addListener((obs, o, n) -> applyFilters());
    }

    private void applyFilters() {

        String searchText = TFSearchF.getText().toLowerCase().trim();

        filteredMovies.setPredicate(movie -> {

            // Hvis ingen filtre er valgt → vis alle
            if (!CBTitle.isSelected() &&
                    !CBCategory.isSelected() &&
                    !CBMIMDbRating.isSelected() &&
                    !CBMPersonalRating.isSelected()) {
                return true;
            }

            boolean matches = false;

            if (CBTitle.isSelected()) {
                matches |= movie.getName().toLowerCase().contains(searchText);
            }

            if (CBCategory.isSelected()) {
                matches |= movie.getGenresString().toLowerCase().contains(searchText);
            }

            if (CBMIMDbRating.isSelected()) {
                try {
                    float minRating = Float.parseFloat(searchText);
                    matches |= movie.getImdbRating() >= minRating;
                } catch (NumberFormatException ignored) {}
            }

            if (CBMPersonalRating.isSelected()) {
                try {
                    float minRating = Float.parseFloat(searchText);
                    matches |= movie.getPersonalRating() >= minRating;
                } catch (NumberFormatException ignored) {}
            }

            return matches;
        });
    }

    private String extractTitleFromFile(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(0, dotIndex);
        } else {
            return fileName;
        }
    }

    private boolean isValidMovieFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".mp4") || name.endsWith(".mpeg4");
    }

    private void showInvalidFileAlert(File file) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid File");
        alert.setHeaderText("Invalid Movie File");
        alert.setContentText("The selected file '" + file.getName() + "' is not a valid movie file. Use .mp4 or .mpeg4 files only..");
        alert.showAndWait();
    }

    private void setupTable() {
        tblColImage.setCellValueFactory(new PropertyValueFactory<>("filePath"));
        tblColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblColGenre.setCellValueFactory(new PropertyValueFactory<>("genresString"));
        tblColIMDbRating.setCellValueFactory(new PropertyValueFactory<>("imdbRating"));
        tblColPRating.setCellValueFactory(new PropertyValueFactory<>("personalRating"));

        tblColImage.setCellFactory(col -> new TableCell<Movie, String>() {

            private final ImageView imageView = new ImageView();
            {
                imageView.setFitHeight(110);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);

                if (empty || imagePath == null) {
                    imageView.setImage(defaultImage);
                    setGraphic(null);
                    return;
                }

                Movie movie = getTableRow().getItem();
                if (movie == null) {
                    setGraphic(null);
                    return;
                }

                if (movie.getImage() == null) {
                    String imageUrl = "https://image.tmdb.org/t/p/w200" + imagePath;
                    movie.setImage(new Image(imageUrl, true)); // background loading
                }

                imageView.setImage(movie.getImage());
                setGraphic(imageView);
            }
        });
        tblColButton.setCellFactory(col -> new TableCell<Movie, Void>() {

            private final Button btnMore = new Button();

            {
                FontIcon icon = new FontIcon("fas-arrow-right");
                btnMore.setGraphic(icon);

                btnMore.setOnAction(event -> {
                    Movie movie = getTableRow().getItem();
                    if (movie != null) {
                        onShowDetails(event, movie);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnMore);
                }
            }
        });
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    private void onShowDetails(ActionEvent actionEvent, Movie movie) {
        FXMLLoader loader = new FXMLLoader();
        Scene scene = setScene(loader, "/dk/easv/privatemoviecollection/movieView.fxml");

        // Set this controller as a parent controller for the new controller
        MovieController movieController = loader.getController();
        movieController.setModel(model);
        movieController.init(movie);

        showStage(actionEvent, "MovieDetails", scene);
    }

    private void showDeleteConfirmation(Movie movie) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Movie");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(
                "The movie \"" + movie.getName() + "\" will be deleted from the collection."
        );

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            deleteMovie(movie);
        }
    }

    @FXML
    private void onAddRemoveM(ActionEvent event) {
        Movie selectedMovie = (Movie) tblView.getSelectionModel().getSelectedItem();

        if (selectedMovie == null) {
            openMovieEditorCreate(event);
        } else {
            showDeleteConfirmation(selectedMovie);
        }
    }

    private void openMovieEditorCreate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/privatemoviecollection/movieEditView.fxml"));
            Scene scene = new Scene(loader.load());

            MovieEditController controller = loader.getController();
            controller.setModel(model);
            controller.init();
            controller.showCreateMode();
            showStage(event, "Add Movie", scene);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onChangeGenre(ActionEvent actionEvent) {
        Movie selectedMovie = (Movie) tblView.getSelectionModel().getSelectedItem();

        if(selectedMovie != null){
            FXMLLoader loader = new FXMLLoader();
            Scene scene = setScene(loader, "/dk/easv/privatemoviecollection/movieGenreView.fxml");

            // Set this controller as a parent controller for the new controller
            MovieGenreController Controller = loader.getController();
            Controller.setModel(model);
            Controller.init(selectedMovie);

            showStage(actionEvent, "Edit Movie", scene);
        }
    }

    private Scene setScene(FXMLLoader loader, String filePath) {
        // Loads the new fxml file
        loader.setLocation(getClass().getResource(filePath));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());

        } catch (IOException e) {
            displayError(e);
        }
        return scene;
    }

    private void deleteMovie(Movie movie) {
        model.deleteMovie(movie);
        model.getMovies().remove(movie);
        tblView.setItems(model.getMovies());
    }

    private void showStage(ActionEvent actionEvent, String stageTitle, Scene scene) {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(stageTitle);

        // Locks the old window while the new window is open
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setResizable(false);

        stage.show();
    }
}