package dk.easv.privatemoviecollection.BLL;

import dk.easv.privatemoviecollection.Be.Movie;
import dk.easv.privatemoviecollection.DAL.ApiMovieDAO;
import dk.easv.privatemoviecollection.DAL.MovieDAO;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class Logic {
    MovieManager movieManager = new MovieManager();
    MovieDAO movieData = new MovieDAO();
    ApiMovieDAO apiData = new ApiMovieDAO();

    public Logic() throws IOException {
    }

    public void deleteMovie(Movie movie) {
        movieData.deleteMovie(movie);
    }

    public List<Movie> getMovies() {
        return movieData.getMovies();
    }

    public Movie getMovieData(String movieTitle) {
        String encoded = URLEncoder.encode(movieTitle, StandardCharsets.UTF_8);
        return apiData.getMovieData(encoded);
    }

    public void updateMovieLastViewed(Movie movie) {
        LocalDate today = LocalDate.now();
        movieData.updateMovieLastViewed(movie, today);
    }

    public String getMovieTrailerString(Movie movie) {
        String encoded = URLEncoder.encode(movie.getName(), StandardCharsets.UTF_8);
        Movie m = apiData.getMovieData(encoded);
        return m.getTrailerApiString();
    }
}