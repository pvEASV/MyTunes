package mytunes.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import mytunes.be.Genre;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.LogicManager;

public class Model {
    private final ObservableList<Genre> genres;
    private final ObservableList<Playlist> playlists;
    private final ObservableList<Song> allSongs;
    private Song songToEdit;
    private LogicManager bll = new LogicManager();

    public Model(){
        genres = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
        allSongs = FXCollections.observableArrayList();
    }
    public Song getSongToEdit() {
        return songToEdit;
    }

    public void setSongToEdit(Song songToEdit) {
        this.songToEdit = songToEdit;
    }

    public void createSong(String title, String filepath){
        bll.createSong(title, filepath);
        loadAllSongs();
    }

    public void deleteSong(Song song){
        bll.deleteSong(song);
        loadAllSongs();
    }

    public void loadAllSongs(){
        allSongs.clear();
        allSongs.addAll(bll.getAllSongs());
    }

    public ObservableList<Song> getAllSongs() {
        loadAllSongs();
        return allSongs;
    }

    public void updateSong(Song song){
        bll.updateSong(new Song(songToEdit.getId(), song.getTitle(), song.getArtist(), song.getGenre(), song.getPath(), song.getDuration()));
    }

    public void createPlaylist(Playlist playlist){
        bll.createPlaylist(playlist);
    }

    public void deletePlaylist(Playlist playlist) {
        bll.deletePlaylist(playlist);
        loadAllPlaylists();
    }

    public void updatePlaylist(Playlist playlist){
        bll.updatePlaylist(playlist);
    }

    private void loadAllPlaylists() {
        playlists.clear();
        playlists.addAll(bll.getAllPlaylists());
    }

    public void createGenre(String name) {
        //TODO update combobox
        Genre genre = bll.createGenre(name);
        genres.add(genre);
    }

    private void updateGenres() {
        genres.clear();
        genres.addAll(bll.getAllGenres());
    }
}
