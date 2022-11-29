package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import mytunes.gui.models.SongModel;

import java.io.File;

public class NewSongViewController {
    public TextField txtFieldTitle;
    public TextField txtFieldArtist;
    public ComboBox<?> comboBoxGenre;
    public TextField txtFieldTime;
    public TextField txtFieldFile;

    private SongModel songModel = new SongModel();

    public void btnGenreMoreAction(ActionEvent actionEvent) {
    }

    public void btnFileChooseAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a song file");

        File file = fileChooser.showOpenDialog(stage);
        if (file != null)
            txtFieldFile.setText(file.getAbsolutePath());
    }

    public void btnSaveAction(ActionEvent actionEvent) {
        String title = txtFieldTitle.getText().trim();
        String filepath = txtFieldFile.getText().trim();
        if (!title.isEmpty() && !filepath.isEmpty())
            songModel.createSong(title, filepath);
        else{
            if (title.isEmpty() || title.equals("Field must not be empty!"))
                txtFieldTitle.setText("Field must not be empty!");
            if (filepath.isEmpty() || filepath.equals("Field must not be empty!"))
                txtFieldFile.setText("Field must not be empty!");
        }
    }

    public void btnCancelAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }
}
