package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import mytunes.be.Genre;
import mytunes.be.Playlist;
import mytunes.gui.models.Model;

public class NewGenreViewController {
    @FXML
    private TextField nameTextField;
    private Model model = null;

    public void setModel(Model model) {
        this.model = model;
    }

    public void saveButtonAction(ActionEvent actionEvent) {
        String name = nameTextField.getText().trim();
        if (!name.isEmpty())
            nameTextField.promptTextProperty().setValue("");
        if (!name.isEmpty() && !nameTextField.promptTextProperty().getValue().equals("Field must not be empty!")
                && !nameTextField.promptTextProperty().getValue().equals("Enter the name of the playlist")){
            nameTextField.promptTextProperty().setValue("");
            model.createGenre(name);
            Node node = (Node) actionEvent.getSource();
            node.getScene().getWindow().hide();
        }
        else
            nameTextField.promptTextProperty().setValue("Field must not be empty!");
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }
}
