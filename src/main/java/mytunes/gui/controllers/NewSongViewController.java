package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mytunes.gui.models.SongModel;

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
    }

    public void btnSaveAction(ActionEvent actionEvent) {
        String title = txtFieldTitle.getText();
        String filepath = txtFieldFile.getText();
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
