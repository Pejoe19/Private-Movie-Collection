package dk.easv.privatemoviecollection.DAL.DB;

import dk.easv.privatemoviecollection.Be.Movie;

import java.util.List;

public interface IMovieDataAccess {
    List<Movie> getMovies();
    Movie createMovie(Movie movie);
    void updateMovie(Movie movie);
    void deleteMovie(Movie movie);
}
