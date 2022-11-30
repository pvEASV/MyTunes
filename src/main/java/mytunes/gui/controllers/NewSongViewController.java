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

    /**
     * Called when a user clicks "More" button while creating a new song
     * Enables a user to create and add another genre to the combobox
     *
     * @param actionEvent The action event that triggered this method
     * @throws IOException Thrown when the fxml file is not found
     */
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

    /**
     * Called when a user clicks the "Choose" button while creating a new song
     * Opens a window which enables user to choose a file path from their documents
     *
     * @param actionEvent The action event that triggered this method
     */
    public void btnFileChooseAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a song file");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null)
            txtFieldFile.setText(file.getAbsolutePath());
    }

    /**
     * Called when a user clicks the "Save" button while creating a new song
     * Saves the song in the database and updates the list view in main window
     *
     * @param actionEvent The action event that triggered this method
     */
    public void btnSaveAction(ActionEvent actionEvent) {
        String title = txtFieldTitle.getText().trim();
        String filepath = txtFieldFile.getText().trim();
        String author = txtFieldArtist.getText().trim();
        String genre = comboBoxGenre.getValue();
        int duration = validateDurationInput(txtFieldTime.getText().trim());
        if (!title.isEmpty() && !filepath.isEmpty() && !title.equals("Field must not be empty!") && !filepath.equals("Field must not be empty!")) {
            model.createSong(title, filepath);
            Node node = (Node) actionEvent.getSource();
            node.getScene().getWindow().hide();
            //TODO update all songs list view
        } else {
            if (title.isEmpty())
                txtFieldTitle.setText("Field must not be empty!");
            if (filepath.isEmpty())
                txtFieldFile.setText("Field must not be empty!");
        }
    }

    /**
     * Cancels creating a new song
     *
     * @param actionEvent The action event that triggered this method
     */
    public void btnCancelAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        node.getScene().getWindow().hide();
    }

    /**
     * Checks, whether the user-input duration of the song is valid and converts it into seconds
     *@param input The duration of the song
     */
    private int validateDurationInput(String input) {
        int duration = -1;
        int colonPosition;

        if (input.contains(":")) {
            switch (input.length()) {
                case 6:
                case 8:
                    colonPosition = input.indexOf(":");
                    if (colonPosition == 1 || colonPosition == 2) {
                        duration += Integer.parseInt(input.substring(0, colonPosition)) * 3600;
                        input = input.substring(colonPosition + 1);
                    }
                case 4:
                case 5:
                    colonPosition = input.indexOf(":");
                    if (colonPosition == 1 || colonPosition == 2) {
                        duration += Integer.parseInt(input.substring(0, colonPosition)) * 60 + Integer.parseInt(input.substring(colonPosition + 1)) + 1;
                    }
                    break;
            }
        }
        return duration;
    }
}
