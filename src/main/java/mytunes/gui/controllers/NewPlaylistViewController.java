package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import mytunes.be.Playlist;
import mytunes.gui.models.Model;

public class NewPlaylistViewController {
    @FXML
    private TextField nameTextField;
    private Model model = null;

    public void saveButtonAction(ActionEvent actionEvent) {
        model.createPlaylist(new Playlist(nameTextField.getText().trim()));
        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
