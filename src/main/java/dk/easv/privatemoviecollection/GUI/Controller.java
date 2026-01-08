package dk.easv.privatemoviecollection.GUI;

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

import java.io.IOException;


public class Controller {
    @FXML
    private TableColumn tblColName;
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
    private TableView tblView;

    Model model = new Model();
    Image defaultImage = new Image(getClass().getResourceAsStream("/dk/easv/privatemoviecollection/pictures/3d-cinema-popcorn-cup.jpg"));

    public Controller() throws IOException {
    }

    @FXML
    public void initialize() {
        setupTable();
        tblView.setItems(model.getMovies());
    }

    private void setupTable() {
        tblColImage.setCellValueFactory(new PropertyValueFactory<>("filePath"));
        tblColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblColGenre.setCellValueFactory(new PropertyValueFactory<>("categories"));
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

    private void onShowDetails(ActionEvent actionEvent, Movie movie) {

        // Loads the new fxml file
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/dk/easv/privatemoviecollection/movieView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set this controller as a parent controller for the new controller
        MovieController movieController = loader.getController();
        movieController.setParent(this);
        movieController.setModel(model);
        movieController.init(movie);

        Stage stage = new Stage();
        stage.setScene(scene);

        // Locks the old window while the new window is open
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setResizable(false);

        stage.show();
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
            openMovieEditorCreate();
        } else {
            openMovieEditorEdit(selectedMovie);
        }
    }

    private void openMovieEditorCreate() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/dk/easv/privatemoviecollection/movieEditView.fxml")
            );
            Scene scene = new Scene(loader.load());
            MovieEditController controller = loader.getController();
            controller.showCreateMode();
            Stage stage = new Stage();
            stage.setTitle("Add Movie");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMovieEditorEdit(Movie movie) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/dk/easv/privatemoviecollection/movieEditView.fxml")
            );
            Scene scene = new Scene(loader.load());
            MovieEditController controller = loader.getController();
            controller.showEditMode();
            Stage stage = new Stage();
            stage.setTitle("Edit Movie");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void deleteMovie(Movie movie) {
        model.deleteMovie(movie);
        tblView.setItems(model.getMovies());
    }

}