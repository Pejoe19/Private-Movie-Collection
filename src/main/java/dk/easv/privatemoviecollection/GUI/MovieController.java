package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.Be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MovieController {

    @FXML
    private WebView webView;
    @FXML
    private Text txtOverview;
    @FXML
    private ImageView imgViewMovie;
    @FXML
    private Text txtImdbRating;
    @FXML
    private Text txtTitle;
    @FXML
    private Text txtPersonalRating;
    @FXML
    private Text txtCategories;

    Controller parentController;
    Model model;
    Movie movie;


    public void init(Movie movie) {
        this.movie = movie;

        txtTitle.setText(movie.getName());
        txtCategories.setText(movie.getCategoriesString());
        txtImdbRating.setText(String.valueOf(movie.getImdbRating()));
        txtPersonalRating.setText(String.valueOf(movie.getPersonalRating()));

        Movie tmdbMovie = model.getMovieData(movie.getName());
        if (tmdbMovie != null) {
            txtOverview.setText(tmdbMovie.getOverview());
            movie.setOverview(tmdbMovie.getOverview());
        } else {
            txtOverview.setText(movie.getOverview() != null ? movie.getOverview() : "No overview available");
        }
        if (movie.getImage() != null) {
            imgViewMovie.setImage(movie.getImage());
        }
    }

    public void setParent(Controller Controller) {
        parentController = Controller;
    }

    public void onBtnPlayMovie(ActionEvent actionEvent) {
        webView.setVisible(true);

        String key = model.getMovieTrailerString(movie);
        String youtubeUrl = "https://www.youtube.com/embed/" + key;
        webView.getEngine().load(youtubeUrl);

        model.updateMovieLastViewed(movie);
    }

    /**
     * Exit the open window
     * @param actionEvent
     */
    public void onExit(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        Stage window = (Stage) btn.getScene().getWindow();
        window.close();
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    private void onEditMovie(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easv/privatemoviecollection/movieEditView.fxml"));
            Scene scene = new Scene(loader.load());

            MovieEditController controller = loader.getController();
            controller.showEditMode(movie);

            Stage stage = new Stage();
            stage.setTitle("Edit Movie");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}