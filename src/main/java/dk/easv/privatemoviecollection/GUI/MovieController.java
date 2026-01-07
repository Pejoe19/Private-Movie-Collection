package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.Be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;

import java.io.File;

public class MovieController {

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
    Movie movie;


    public void init(Movie movie) {
        this.movie = movie;
        txtTitle.setText(movie.getName());
        txtCategories.setText(movie.getCategories());
        txtImdbRating.setText(String.valueOf(movie.getImdbRating()));
        txtPersonalRating.setText(String.valueOf(movie.getPersonalRating()));

        File file = new File("src/main/resources/dk/easv/privatemoviecollection/videos/Optagelse2026-01-07_010703.mp4");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }

    public void setParent(Controller Controller) {
        parentController = Controller;
    }

    public void onBtnPlayMovie(ActionEvent actionEvent) {
        mediaPlayer.play();
        mediaView.setVisible(true);
    }
}
