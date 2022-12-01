package mytunes.gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import mytunes.MyTunes;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.gui.models.Model;

import java.io.File;
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
    private ImageView playPauseButton, moveSongUpButton, moveSongDownButton, moveSongToPlaylistButton;
    @FXML
    private TableView<Song> allSongsTableView;
    @FXML
    private TableColumn<Song, String> titleColumn, artistColumn, genreColumn;
    @FXML
    private TableColumn<Song, Integer> durationColumn;
    //TODO change the duration to mm:ss
    @FXML
    private TableView<Playlist> playlistsTableView;
    @FXML
    private TableColumn<Playlist, String> playlistNameColumn;
    @FXML
    private TableColumn<Playlist, Integer> totalLengthColumn;
    //TODO change the duration to mm:ss

    private boolean isPlaying = false;
    private final Model model = new Model();

    private Media media;
    private MediaPlayer mediaPlayer;
    private File file = new File("src/main/java/mytunes/Bring_me_the_Horizon_-_Drown.mp3");
    private final String MEDIA_URL = file.toURI().toString();

    @FXML
    public void initialize() {
        showAllSongs();
        showAllPlaylists();

        media = new Media(MEDIA_URL);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.05);

        // Loading all songs when filter text field is put in focus
        filterTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                model.loadSongsToMemory();
            else
                model.removeSongsFromMemory();
        });
    }

    private void showAllSongs() {
        allSongsTableView.refresh();
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        allSongsTableView.getItems().setAll(model.getAllSongs());
    }

    private void showAllPlaylists(){
        playlistsTableView.refresh();
        playlistNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalLengthColumn.setCellValueFactory(new PropertyValueFactory<>("totalLength"));
        playlistsTableView.getItems().setAll(model.getAllPlaylists());
    }

    /**
     * This method is called when the user clicks the ImageView representing play/pause button.
     * @param mouseEvent The mouse event that triggered this method.
     */
    public void playPauseMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);


        if (isPlaying) {
            playPauseButton.setImage(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/play.png"))));
            isPlaying = false;
            mediaPlayer.pause();
        } else {
            playPauseButton.setImage(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream("images/pause.png"))));
            isPlaying = true;


            mediaPlayer.play();
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
    public void deleteButtonAction(ActionEvent actionEvent){
        Node source = (Node) actionEvent.getSource();
        String type = source.getId().equals("playlistDeleteButton") ? "playlist" : "song";

        // check if the user has selected a playlist or a song
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        if (type.equals("song")){
            if (allSongsTableView.getSelectionModel().getSelectedItem() == null){
                errorAlert.setHeaderText("No song selected");
                errorAlert.setContentText("Please select a song to delete");
                errorAlert.showAndWait();
                return;
            }
        } else {
            if (playlistsTableView.getSelectionModel().getSelectedItem() == null){
                errorAlert.setHeaderText("No playlist selected");
                errorAlert.setContentText("Please select a playlist to delete");
                errorAlert.showAndWait();
                return;
            }
        }

        // ask the user if they are sure they want to delete the selected playlist/song
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete " + type);
        alert.setContentText("Do you really wish to delete this " + type + " ?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            if (type.equals("song")) {
                Song song = allSongsTableView.getSelectionModel().getSelectedItem();
                model.deleteSong(song);
                showAllSongs();
            } else {
                Playlist playlist = playlistsTableView.getSelectionModel().getSelectedItem();
                model.deletePlaylist(playlist);
                showAllPlaylists();
            }
        }
    }

    /**
     * Called when the user clicks the "new" button under playlist section
     * It opens new window for creating new playlist
     * @param actionEvent The action event that triggered this method
     * @throws IOException thrown when the fxml file is not found
     */
    public void playlistNewButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = openNewWindow("Add Playlist", "views/new-playlist-view.fxml", "images/playlist.png");
        NewPlaylistViewController newPlaylistViewController = fxmlLoader.getController();
        newPlaylistViewController.setModel(model);
    }
    public void playlistEditButtonAction(ActionEvent actionEvent) throws IOException {
        Playlist playlist = playlistsTableView.getSelectionModel().getSelectedItem();
        if (playlist == null)
            new Alert(Alert.AlertType.ERROR, "Please select a playlist to edit").showAndWait();
        else {
            model.setPlaylistToEdit(playlist);
            FXMLLoader fxmlLoader = openNewWindow("Edit playlist", "views/new-playlist-view.fxml", "images/playlist.png");
            NewPlaylistViewController newPlaylistViewController = fxmlLoader.getController();
            newPlaylistViewController.setModel(model);
            newPlaylistViewController.setIsEditing();
        }
    }

    public void filterOnKeyTyped(KeyEvent keyEvent) {
        allSongsTableView.getItems().setAll(model.search(filterTextField.getText()));
        allSongsTableView.refresh();
    }
    /**
     * Called when the user clicks the "new" button under all songs section
     * It opens new window for adding new song
     * @param actionEvent The action event that triggered this method
     * @throws IOException thrown when the fxml file is not found
     */
    public void songNewButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = openNewWindow("Add song", "views/new-song-view.fxml", "images/record.png");
        NewSongViewController newSongViewController = fxmlLoader.getController();
        newSongViewController.setModel(model);
    }

    public void songEditButtonAction(ActionEvent actionEvent) throws IOException {
        Song selectedSong = allSongsTableView.getSelectionModel().getSelectedItem();
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
           showAllPlaylists();
        });
        stage.show();
        return fxmlLoader;
    }
}
