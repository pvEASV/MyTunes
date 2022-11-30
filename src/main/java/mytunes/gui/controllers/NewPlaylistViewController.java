package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.gui.models.Model;

public class NewPlaylistViewController {
    @FXML
    private TextField nameTextField;
    private Model model = new Model();

    public void saveButtonAction(ActionEvent actionEvent) {
        String playlistName = nameTextField.getText().trim();
        //TODO model.createPlaylist(playlist);
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }
}
