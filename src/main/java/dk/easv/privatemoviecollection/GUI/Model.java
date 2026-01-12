package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.BLL.MovieException;
import dk.easv.privatemoviecollection.BLL.MovieManager;
import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {
    MovieManager logic = new MovieManager();
    ObservableList<Movie> observableList;

    public Model() throws MovieException {
    }

    public ObservableList<Movie> getMovies() {
        observableList = FXCollections.observableList(logic.getMovies());
        return observableList;
    }

    public Movie getMovieData(String movieTitle) {
        return logic.getMovieData(movieTitle);
    }

    public void updateMovieLastViewed(Movie movie) {
        logic.updateMovieLastViewed(movie);
    }

    public void deleteMovie(Movie movie) {
        logic.deleteMovie(movie);
    }

    public String getMovieTrailerString(Movie movie) {
        return logic.getMovieTrailerString(movie);
    }

    public List<Genre> getGenres() {
        return logic.getGenres();
    }

    public void updateGenres(Movie movie, ArrayList<Genre> genres) {
        Movie updatedMovie = logic.updateGenres(movie, genres);
        if (updatedMovie != null) {
            for (int i = 0; i < observableList.size(); i++) {
                if (observableList.get(i).getId() == updatedMovie.getId()) {
                    observableList.set(i, updatedMovie);
                    break;
                }
            }
        }
    }
}