package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MovieGenreController {
    @FXML
    private VBox checkListContainerLeft;
    @FXML
    private VBox checkListContainerRight;
    @FXML
    private Label lblMovieName;

    private MainController parentController;
    private Model model;
    private Movie movie;
    private List<Genre> genreList;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();

    public void setParent(MainController mainController) {
        this.parentController = mainController;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void init(Movie movie) {
        this.movie = movie;
        lblMovieName.setText(movie.getName());
        generateCheckboxes(movie.getGenresString());
    }

    private void generateCheckboxes(String genres) {
        genreList = model.getGenres();
        boolean left = true;
        for(Genre genre : genreList){
            CheckBox checkBox = new CheckBox(genre.getName());
            checkBox.setId(String.valueOf(genre.getId()));
            if(genres.contains(genre.getName())){
                checkBox.setSelected(true);
            }

            if(left){
                checkListContainerLeft.getChildren().add(checkBox);
            } else {
                checkListContainerRight.getChildren().add(checkBox);
            }
            left = !left;
            checkBoxes.add(checkBox);
        }
    }

    @FXML
    private void onSave(ActionEvent actionEvent) {
        ArrayList<Genre> genres = new ArrayList<>();
        for(CheckBox checkBox : checkBoxes){
            if(checkBox.isSelected()){
                genres.add( new Genre(Integer.parseInt(checkBox.getId()),checkBox.getText()));
            }
        }
        model.updateGenres(movie, genres);
        closeWindow(actionEvent);
    }

    @FXML
    private void onCancel(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }

    private void closeWindow(ActionEvent event){
        Button btn = (Button) event.getSource();
        Stage window = (Stage) btn.getScene().getWindow();
        window.close();
    }
}
