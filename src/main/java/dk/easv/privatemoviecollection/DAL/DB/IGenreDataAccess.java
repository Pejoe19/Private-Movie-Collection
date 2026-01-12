package dk.easv.privatemoviecollection.DAL.DB;

import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;

import java.util.ArrayList;
import java.util.List;

public interface IGenreDataAccess {
        List<Genre> getAllGenres();
        ArrayList<Genre> getMovieGenres(Movie movie);
        void createGenre(Movie movie, Genre genre);
        void deleteGenre(Movie movie, Genre genre);
}
