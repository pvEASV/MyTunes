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
    private boolean isEditing = false;

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    public void initialize(){
        isEditing = false;
    }

    public void btnSaveAction(ActionEvent actionEvent) {
        String name = nameTextField.getText().trim();
        if (!name.isEmpty())
            nameTextField.promptTextProperty().setValue("");
        if (!name.isEmpty() && !nameTextField.promptTextProperty().getValue().equals("Field must not be empty!")
                && !nameTextField.promptTextProperty().getValue().equals("Enter the name of the playlist")){
            nameTextField.promptTextProperty().setValue("");
            if (isEditing)
                model.updatePlaylist(new Playlist(name));
            else
                model.createPlaylist(new Playlist(name));
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

    public void setIsEditing(){
        isEditing = true;
        Playlist playlistToEdit = model.getPlaylistToEdit();
        nameTextField.setText(playlistToEdit.getName());
    }
}
