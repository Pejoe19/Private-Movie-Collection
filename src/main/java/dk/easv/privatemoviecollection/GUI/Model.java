package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.BLL.Logic;
import dk.easv.privatemoviecollection.Be.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class Model {
    Logic logic = new Logic();

    public Model() throws IOException {
    }

    public ObservableList getMovies() {
        ObservableList observableList = FXCollections.observableList(logic.getMovies());
        return observableList;
    }

    public void deleteMovie(Movie movie) {
        logic.deleteMovie(movie);
    }
}
