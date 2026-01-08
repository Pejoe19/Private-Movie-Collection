package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.Be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;

public class MovieController {

    @FXML
    private WebView webView;
    @FXML
    private Text txtOverview;
    @FXML
    private ImageView imgViewMovie;
    @FXML
    private MediaView mediaView;
    @FXML
    private Text txtImdbRating;
    @FXML
    private Text txtTitle;
    @FXML
    private Text txtPersonalRating;
    @FXML
    private Text txtCategories;

    MediaPlayer mediaPlayer;
    Controller parentController;
    Model model;
    Movie movie;


    public void init(Movie movie) {
        this.movie = movie;
        txtTitle.setText(movie.getName());
        txtOverview.setText(movie.getOverview());
        txtCategories.setText(movie.getCategoriesString());
        txtImdbRating.setText(String.valueOf(movie.getImdbRating()));
        txtPersonalRating.setText(String.valueOf(movie.getPersonalRating()));

        Image image;
        if (movie.getFilePath().contains("easv")) {
            image = new Image(getClass().getResourceAsStream(movie.getFilePath()));
        } else {
            String imageUrl = "https://image.tmdb.org/t/p/w200" + movie.getFilePath();
            image = new Image(imageUrl, true);
        }
        imgViewMovie.setImage(image);

        File file = new File("src/main/resources/dk/easv/privatemoviecollection/videos/Optagelse2026-01-07_010703.mp4");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
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
}