package mytunes.gui.controllers;

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
    private ListView<Song> songsInPlaylistListView;
    @FXML
    private Slider volumeControlSlider, songTimeSlider;
    @FXML
    private TextField filterTextField;
    @FXML
    private ImageView playPauseButton, moveSongUpButton, moveSongDownButton, moveSongToPlaylistButton;
    @FXML
    private TableView<Song> allSongsTableView;
    @FXML
    private TableColumn<Song, String> titleColumn, artistColumn, genreColumn, durationColumn;
    @FXML
    private TableView<Playlist> playlistsTableView;
    @FXML
    private TableColumn<Playlist, String> playlistNameColumn, totalLengthColumn;

    private boolean isPlaying = false;
    private final Model model = new Model();

    @FXML
    public void initialize() {
        showAllSongs();
        showAllPlaylists();
        filterTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                model.loadSongsToMemory();
            else
                model.removeSongsFromMemory();
        });
    }

    private void showAllSongs() {
        allSongsTableView.refresh();
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title")); // cals getDurationAsAString() method
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationAsAString")); // cals getDurationAsAString() method
        allSongsTableView.getItems().setAll(model.getAllSongs());
    }

    private void showAllPlaylists(){
        playlistsTableView.refresh();
        playlistNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalLengthColumn.setCellValueFactory(new PropertyValueFactory<>("totalLengthAsAString"));
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
    public void ImageViewMouseDown(MouseEvent mouseEvent) {
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
        Object[] objects = openNewWindow("Add Playlist", "views/new-playlist-view.fxml", "images/playlist.png");
        FXMLLoader fxmlLoader = (FXMLLoader) objects[0];
        Window window = (Window) objects[1];
        window.setOnHiding(event -> showAllPlaylists());
        NewPlaylistViewController newPlaylistViewController = fxmlLoader.getController();
        newPlaylistViewController.setModel(model);
    }
    public void playlistEditButtonAction(ActionEvent actionEvent) throws IOException {
        Playlist playlist = playlistsTableView.getSelectionModel().getSelectedItem();
        if (playlist == null)
            new Alert(Alert.AlertType.ERROR, "Please select a playlist to edit").showAndWait();
        else {
            model.setPlaylistToEdit(playlist);
            Object[] objects = openNewWindow("Edit Playlist", "views/edit-playlist-view.fxml", "images/playlist.png");
            FXMLLoader fxmlLoader = (FXMLLoader) objects[0];
            Window window = (Window) objects[1];
            window.setOnHiding(event -> showAllPlaylists());
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
        Object[] objects = openNewWindow("Add song", "views/new-song-view.fxml", "images/record.png");
        FXMLLoader fxmlLoader = (FXMLLoader) objects[0];
        Window window = (Window) objects[1];
        window.setOnHiding(event -> showAllSongs());
        NewSongViewController newSongViewController = fxmlLoader.getController();
        newSongViewController.setModel(model);
        newSongViewController.setComboBoxItems();
    }

    public void songEditButtonAction(ActionEvent actionEvent) throws IOException {
        Song selectedSong = allSongsTableView.getSelectionModel().getSelectedItem();
        if (selectedSong == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a song to edit").showAndWait();
        } else {
            model.setSongToEdit(selectedSong);
            Object[] objects  = openNewWindow("Edit song", "views/new-song-view.fxml", "images/record.png");
            FXMLLoader fxmlLoader = (FXMLLoader) objects[0];
            Window window = (Window) objects[1];
            window.setOnHiding(event -> showAllSongs());
            NewSongViewController newSongViewController = fxmlLoader.getController();
            newSongViewController.setModel(model);
            newSongViewController.setIsEditing();
        }
    }

    private Object[] openNewWindow(String title, String fxmlFile, String iconFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyTunes.class.getResource(fxmlFile));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(Objects.requireNonNull(MyTunes.class.getResourceAsStream(iconFile))));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        Window window = scene.getWindow();
        stage.show();
        return new Object[]{fxmlLoader, window};
    }

    public void moveSongToPlaylistMouseDown(MouseEvent mouseEvent) {
        ImageViewMouseDown(mouseEvent);
    }

    public void moveSongToPlaylistMouseUp(MouseEvent mouseEvent) {
        resetOpacity(mouseEvent);
        Song song = allSongsTableView.getSelectionModel().getSelectedItem();
        Playlist playlist = playlistsTableView.getSelectionModel().getSelectedItem();
        if (song == null || playlist == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a song and a playlist").showAndWait();
        } else {
            model.moveSongToPlaylist(song, playlist);
            showSongsInPlaylist();
        }
    }

    public void playlistTableViewOnMouseUp(MouseEvent mouseEvent) {
        showSongsInPlaylist();
    }

    private void showSongsInPlaylist(){
        songsInPlaylistListView.setItems(model.getSongsInPlaylist(playlistsTableView.getSelectionModel().getSelectedItem()));
    }

    public void moveSongUpMouseUp(MouseEvent mouseEvent){
        resetOpacity(mouseEvent);
        Playlist playlist = playlistsTableView.getSelectionModel().getSelectedItem();
        Song song = songsInPlaylistListView.getSelectionModel().getSelectedItem();
        int songIndex = songsInPlaylistListView.getSelectionModel().getSelectedIndex();
        if (song == null)
            new Alert(Alert.AlertType.ERROR, "Please select a song to move").showAndWait();
        else{
            model.moveSongUpInPlaylist(song, playlist, songIndex);
            songsInPlaylistListView.setItems(model.getSongsInPlaylist(playlist));
        }
    }

    public void moveSongUpMouseDown(MouseEvent mouseEvent){
        ImageViewMouseDown(mouseEvent);
    }

    public void moveSongDownMouseUp(MouseEvent mouseEvent){
        resetOpacity(mouseEvent);
        Playlist playlist = playlistsTableView.getSelectionModel().getSelectedItem();
        Song song = songsInPlaylistListView.getSelectionModel().getSelectedItem();
        int songIndex = songsInPlaylistListView.getEditingIndex();
        if (song == null)
            new Alert(Alert.AlertType.ERROR, "Please select a song to move").showAndWait();
        else{
            //model.moveSongDownInPlaylist(song, playlist, songIndex);
            songsInPlaylistListView.setItems(model.getSongsInPlaylist(playlist));
        }
    }

    public void moveSongDownMouseDown(MouseEvent mouseEvent){
        ImageViewMouseDown(mouseEvent);
    }
}
