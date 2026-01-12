package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.BLL.MovieException;
import dk.easv.privatemoviecollection.BLL.MovieManager;
import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    MovieManager movieManager = new MovieManager();
    private Connection connection;
    ObservableList<Movie> observableList;

    public Model() throws MovieException {
//        try {
//            String url = "jdbc:mysql://localhost:3306/privatemovie_collection";
//            String user = "root";
//            String password = "root";
//            connection = DriverManager.getConnection(url, user, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
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
        getMovies().add(movie);
        try {
            addMovieToDatabase(movie);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMovieToDatabase(Movie movie) throws SQLException {
        String sql = "INSERT INTO movies (title, imdbRating, personalRating, filePath) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, movie.getName());
            stmt.setFloat(2, movie.getImdbRating());
            stmt.setFloat(3, movie.getPersonalRating());
            stmt.setString(4, movie.getFilePath());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    movie.setId(keys.getInt(1));
                }
            }
        }
    }

    public void updateMovie(Movie movie) {
        try {
            String sql = "UPDATE movies SET title = ?, imdbRating = ?, personalRating = ?, filePath = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, movie.getName());
                stmt.setFloat(2, movie.getImdbRating());
                stmt.setFloat(3, movie.getPersonalRating());
                stmt.setString(4, movie.getFilePath());
                stmt.setInt(5, movie.getId());
                stmt.executeUpdate();
            }

            ObservableList<Movie> movies = getMovies();
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getId() == movie.getId()) {
                    movies.set(i, movie);
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Genre> getGenres() {
        return movieManager.getGenres();
    }

    public void updateGenres(Movie movie, ArrayList<Genre> genres) {
        Movie updatedMovie = movieManager.updateGenres(movie, genres);
        if (updatedMovie != null) {
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
}