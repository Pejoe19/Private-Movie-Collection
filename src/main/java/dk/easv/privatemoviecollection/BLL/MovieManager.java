package dk.easv.privatemoviecollection.BLL;

import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;
import dk.easv.privatemoviecollection.DAL.API.ApiMovieDAO;
import dk.easv.privatemoviecollection.DAL.DB.GenreDAO;
import dk.easv.privatemoviecollection.DAL.DB.MovieDAO;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieManager {
    MovieDAO movieData = new MovieDAO();
    GenreDAO genreData = new GenreDAO();
    ApiMovieDAO apiData = new ApiMovieDAO();

    public MovieManager() throws MovieException {
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

    public List<Genre> getGenres() {
        return genreData.getAllGenres();
    }

    public List<String> getGenreNames() {
        List<Genre> genres = genreData.getAllGenres();
        List<String> genreNames = new ArrayList<>();
        for (Genre genre : genres){
            genreNames.add(genre.getName());
        }
        return genreNames;
    }

    public Movie updateGenres(Movie movie, ArrayList<Genre> newGenres) {
        ArrayList<Genre> movieGenresBefore = genreData.getMovieGenres(movie);
        String genreString = "";

        for (Genre newGenre : newGenres){
            if (!movieGenresBefore.contains(newGenre)){
                genreData.createGenre(movie, newGenre);
            }
            if(newGenres.getFirst().equals(newGenre)){
                genreString += newGenre.getName();
            } else {
                genreString += ", " + newGenre.getName();
            }
        }

        for (Genre oldGenre : movieGenresBefore){
            if (!newGenres.contains(oldGenre)) {
                genreData.deleteGenre(movie, oldGenre);
            }
        }

        movie.setGenresString(genreString);
        return movie;
    }
}