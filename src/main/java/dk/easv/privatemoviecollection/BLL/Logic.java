package dk.easv.privatemoviecollection.BLL;

import dk.easv.privatemoviecollection.Be.Movie;
import dk.easv.privatemoviecollection.DAL.MovieDAO;

import java.io.IOException;
import java.util.List;

public class Logic {
    MovieDAO movieDAO = new MovieDAO();

    public Logic() throws IOException {
    }

    public List<Movie> getMovies() {
        return movieDAO.getMovies();
    }
}
