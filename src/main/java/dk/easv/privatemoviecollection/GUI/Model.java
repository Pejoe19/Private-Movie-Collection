package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.BLL.MovieException;
import dk.easv.privatemoviecollection.BLL.MovieManager;
import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;
import dk.easv.privatemoviecollection.DAL.DB.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    MovieManager movieManager = new MovieManager();
    ObservableList<Movie> observableList;

    public Model() throws MovieException {
    }

    public ObservableList<Movie> getMovies() {
        if (observableList == null) {
            observableList = FXCollections.observableList(movieManager.getMovies());
        }
        return observableList;
    }

    public Movie getMovieData(String movieTitle) {
        return movieManager.getMovieData(movieTitle);
    }

    public void updateMovieLastViewed(Movie movie) {
        movieManager.updateMovieLastViewed(movie);
    }

    public void deleteMovie(Movie movie) {
        movieManager.deleteMovie(movie);
    }

    public String getMovieTrailerString(Movie movie) {
        return movieManager.getMovieTrailerString(movie);
    }

    public void createMovie(Movie movie) {
        System.out.println(movie.toString());
        movieManager.addMovieToDatabase(movie);
        getMovies().add(movie);
    }

    public void updateMovie(Movie movie) {
        movieManager.updateMovie(movie); // DB update

        // Update the list in memory
        for (int i = 0; i < observableList.size(); i++) {
            if (observableList.get(i).getId() == movie.getId()) {
                observableList.set(i, movie);
                break;
            }
        }
    }

    public List<Genre> getGenres() {
        return movieManager.getGenres();
    }

    public void updateGenres(Movie movie, ArrayList<Genre> genres) {

        Movie updatedMovie = movieManager.updateGenres(movie, genres);

        if (updatedMovie != null) {
            // Build string for GUI table
            String genreString = genres.stream()
                    .map(Genre::getName)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");

            updatedMovie.setGenresString(genreString);

            // Update observable list
            for (int i = 0; i < observableList.size(); i++) {
                if (observableList.get(i).getId() == updatedMovie.getId()) {
                    observableList.set(i, updatedMovie);
                    break;
                }
            }
        }
    }

    public List<String> getGenreNames() {
        return movieManager.getGenreNames();
    }

    public ArrayList<Genre> getMovieGenres(Movie movie) {
        return movieManager.getMovieGenres(movie);
    }
}