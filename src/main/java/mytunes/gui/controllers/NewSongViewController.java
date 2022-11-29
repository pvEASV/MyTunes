package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mytunes.MyTunes;
import mytunes.gui.models.Model;

import java.io.File;
import java.io.IOException;

public class NewSongViewController {
    public TextField txtFieldTitle;
    public TextField txtFieldArtist;
    public ComboBox<String> comboBoxGenre;
    public TextField txtFieldTime;
    public TextField txtFieldFile;

    private Model model = new Model();

    public void btnGenreMoreAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyTunes.class.getResource("views/new-genre-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add genre");
        stage.setResizable(false);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.centerOnScreen();
        stage.show();

        //comboBoxGenre.getItems().add();
        //add the genre to database
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
        if (!title.isEmpty() && !filepath.isEmpty() && !title.equals("Field must not be empty!") && !filepath.equals("Field must not be empty!") ){
            model.createSong(title, filepath);
            Node node = (Node) actionEvent.getSource();
            node.getScene().getWindow().hide();
            //TODO update all songs list view
        }
        else{
            if (title.isEmpty())
                txtFieldTitle.setText("Field must not be empty!");
            if (filepath.isEmpty())
                txtFieldFile.setText("Field must not be empty!");
        }
    }

    public void btnCancelAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }
}
