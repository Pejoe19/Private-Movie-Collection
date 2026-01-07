package dk.easv.privatemoviecollection.GUI;

import dk.easv.privatemoviecollection.BLL.Logic;
import dk.easv.privatemoviecollection.Be.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MovieEditController {

    @FXML private Label lblHeader;
    @FXML private TextField txtTitle;
    @FXML private TextField txtImdbRating;
    @FXML private TextField txtPersonalRating;
    @FXML private TextField txtFilePath;
    @FXML private TextField txtCategories;

    public void showCreateMode() {
        lblHeader.setText("Add Movie");
    }

    public void showEditMode() {
        lblHeader.setText("Edit Movie");
    }
}
