package mytunes.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import mytunes.MyTunes;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.models.Model;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class MainWindowController {
    @FXML
    private Slider volumeControlSlider, songTimeSlider;
    @FXML
    private TextField filterTextField;
    @FXML
    private ListView<Song> songsInPlaylistListVIew;
    @FXML
    private ListView<Song> allSongsListView;
    @FXML
    private ListView<Playlist> playListListVIew;
    @FXML
    private ImageView playPauseButton;
    @FXML
    private Button playlistDeleteButton, songsInPlaylistDeleteButton, songsDeleteButton;

    private boolean isPlaying = false;
    private Model model = new Model();

    @FXML
    public void initialize() {
        showAllSongs();
    }

    private void showAllSongs() {
        allSongsListView.setItems(model.getAllSongs());
    }

    /**
     * This method is called when the user clicks the ImageView representing play/pause button.
     * @param mouseEvent The mouse event that triggered this method.
     */
    public void playPauseMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);
        System.out.println("Play/Pause button mouse down");
        if (isPlaying) {
            playPauseButton.setImage(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/play.png"))));
            isPlaying = false;
        } else {
            playPauseButton.setImage(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/pause.png"))));
            isPlaying = true;
        }
    }
    public void forwardMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);
    }

    public void rewindMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);
    }

    /**
     * Changes opacity of the music controls buttons to 0.5 when mouse is pressed down on them
     * @param mouseEvent The mouse event that triggered this method
     */
    public void controlsButtonMouseDown(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setOpacity(0.5);
    }

    /**
     * Resets the opacity of the button to 1.0
     * Used when the mouse is released
     * @param mouseEvent The mouse event that triggered this method
     */
    private void resetOpacity(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setOpacity(1);
    }

    /**
     * Called when the user clicks one of delete buttons
     * @param actionEvent The action event that triggered this method
     */
    public void showAlert(ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Node source = (Node) actionEvent.getSource();
        String type = "song";
        if (source.getId().equals("playlistDeleteButton"))
            type = "playlist";
        alert.setTitle("Delete " + type);
        alert.setContentText("Do you really wish to delete this " + type + " ?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isEmpty()){
            // TODO alert is exited, no button has been pressed.
        } else if(result.get() == ButtonType.OK){
            if (type.equals("song")){
                Song song = allSongsListView.getSelectionModel().getSelectedItem();
                model.deleteSong(song);
            }
            else{
                Playlist playlist;
                //TODO model.deletePlaylist(playlist);
            }

        } else if(result.get() == ButtonType.CANCEL){
            //TODO cancel button is pressed
            System.out.println("Cancel button clicked");
        }
    }

    /**
     * Called when the user clicks the "new" button under playlist section
     * It opens new window for creating new playlist
     * @param actionEvent The action event that triggered this method
     * @throws IOException thrown when the fxml file is not found
     */
    public void playlistNewButtonAction(ActionEvent actionEvent) throws IOException {
        openNewWindow("Add Playlist", "views/new-playlist-view.fxml", "images/playlist.png");
    }
    public void playlistEditButtonAction(ActionEvent actionEvent) throws IOException {
        openNewWindow("Edit playlist", "views/edit-playlist-view.fxml", "images/playlist.png");
    }

    public void filterOnKeyTyped(KeyEvent keyEvent) {

    }
    /**
     * Called when the user clicks the "new" button under all songs section
     * It opens new window for adding new song
     * @param actionEvent The action event that triggered this method
     * @throws IOException thrown when the fxml file is not found
     */
    public void songNewButtonAction(ActionEvent actionEvent) throws IOException {
        openNewWindow("Add song", "views/new-song-view.fxml", "images/record.png");
    }

    public void songEditButtonAction(ActionEvent actionEvent) throws IOException {
        Song selectedSong = allSongsListView.getSelectionModel().getSelectedItem();
        if (selectedSong == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a song to edit").showAndWait();
        } else {
            model.setSongToEdit(selectedSong);
            FXMLLoader fxmlLoader = openNewWindow("Edit song", "views/new-song-view.fxml", "images/record.png");
            NewSongViewController newSongViewController = fxmlLoader.getController();
            newSongViewController.setModel(model);
            newSongViewController.setIsEditing();


        }
    }

    private FXMLLoader openNewWindow(String title, String fxmlFile, String iconFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyTunes.class.getResource(fxmlFile));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream(iconFile))));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        scene.getWindow().setOnHiding(event -> {
           showAllSongs();
        });
        stage.show();
        return fxmlLoader;
    }
}
