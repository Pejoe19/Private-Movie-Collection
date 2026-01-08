package dk.easv.privatemoviecollection.BLL;

import dk.easv.privatemoviecollection.Be.Movie;
import dk.easv.privatemoviecollection.DAL.ApiMovieDAO;
import dk.easv.privatemoviecollection.DAL.MovieDAO;

import java.io.IOException;
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
        return apiData.getMovieData(movieTitle);
    }

    public void updateMovieLastViewed(Movie movie) {
        LocalDate today = LocalDate.now();
        movieData.updateMovieLastViewed(movie, today);
    }

    public String getMovieTrailerString(Movie movie) {
        Movie m = apiData.getMovieData(movie.getName().replace(" ","-"));
        return m.getTrailerApiString();
    }
}