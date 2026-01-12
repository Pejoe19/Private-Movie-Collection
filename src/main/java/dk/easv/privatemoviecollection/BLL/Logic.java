package dk.easv.privatemoviecollection.BLL;

import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;
import dk.easv.privatemoviecollection.DAL.ApiMovieDAO;
import dk.easv.privatemoviecollection.DAL.GenreDAO;
import dk.easv.privatemoviecollection.DAL.MovieDAO;
import dk.easv.privatemoviecollection.DAL.MovieGenreDAO;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Logic {
    MovieManager movieManager = new MovieManager();
    MovieDAO movieData = new MovieDAO();
    GenreDAO genreData = new GenreDAO();
    MovieGenreDAO movieGenreData = new MovieGenreDAO();
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

    public List<Genre> getGenres() {
        return genreData.getGenres();
    }

    public Movie updateGenres(Movie movie, ArrayList<Genre> newGenres) {
        ArrayList<Genre> movieGenresBefore = movieGenreData.getMovieGenres(movie);
        String genreString = "";

        for (Genre newGenre : newGenres){
            if (!movieGenresBefore.contains(newGenre)){
                movieGenreData.addGenre(movie, newGenre.getId());
            }
            if(newGenres.getFirst().equals(newGenre)){
                genreString += newGenre.getName();
            } else {
                genreString += ", " + newGenre.getName();
            }
        }

        for (Genre oldGenre : movieGenresBefore){
            if (!newGenres.contains(oldGenre)) {
                movieGenreData.deleteGenre(movie, oldGenre.getId());
            }
        }

        movie.setGenresString(genreString);
        return movie;
    }
}