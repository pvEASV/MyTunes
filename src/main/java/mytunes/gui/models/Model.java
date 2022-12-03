package mytunes.gui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Genre;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.LogicManager;

import java.util.List;

public class Model {
    private final ObservableList<Genre> genres;
    private final ObservableList<Playlist> playlists;
    private final ObservableList<Song> allSongs;
    private final ObservableList<Song> songsInPlaylist;
    private Song songToEdit;
    private Playlist playlistToEdit;
    private Genre genreToEdit;
    private LogicManager bll = new LogicManager();

    public Model(){
        genres = FXCollections.observableArrayList();
        playlists = FXCollections.observableArrayList();
        allSongs = FXCollections.observableArrayList();
        songsInPlaylist = FXCollections.observableArrayList();
    }

    public Song getSongToEdit() {
        return songToEdit;
    }

    public void setSongToEdit(Song songToEdit) {
        this.songToEdit = songToEdit;
    }

    public void createSong(Song song){
        bll.createSong(song);
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
        loadAllPlaylists();
    }

    public void deletePlaylist(Playlist playlist) {
        bll.deletePlaylist(playlist);
        loadAllPlaylists();
    }

    public void updatePlaylist(Playlist playlist){
        bll.updatePlaylist(new Playlist(playlistToEdit.getId(), playlist.getName(), playlist.getTotalLength()));
    }

    private void loadAllPlaylists() {
        playlists.clear();
        playlists.addAll(bll.getAllPlaylists());
    }

    public ObservableList<Playlist> getAllPlaylists(){
        loadAllPlaylists();
        return playlists;
    }

    public Playlist getPlaylistToEdit() {
        return playlistToEdit;
    }

    public void setPlaylistToEdit(Playlist playlist){
        this.playlistToEdit = playlist;
    }

    public void createGenre(String name) {
        bll.createGenre(new Genre(name));
        loadAllGenres();
    }

    public void updateGenre(Genre genre) {
        bll.updateGenre(new Genre(genreToEdit.getId(), genre.getName()));
    }

    public void deleteGenre(Genre genre){
        bll.deleteGenre(genre);
        loadAllGenres();
    }

    private void loadAllGenres(){
        genres.clear();
        genres.addAll(bll.getAllGenres());
    }

    public List<Genre> getAllGenres(){
        loadAllGenres();
        return genres;
    }

    public void setGenreToEdit(Genre genre){
        this.genreToEdit = genre;
    }
    public Genre getGenreToEdit(){
        return genreToEdit;
    }

    public List<Song> search(String query){
        return bll.filterSongs(query);
    }
    public void loadSongsToMemory(){
        bll.loadSongsToMemory();
    }

    public void removeSongsFromMemory(){
        bll.removeSongsFromMemory();
    }

    public void moveSongToPlaylist(Song song, Playlist playlist) {
        bll.addSongToPlaylist(song, playlist);
    }

    public ObservableList<Song> getSongsInPlaylist(Playlist playlist){
        songsInPlaylist.clear();
        songsInPlaylist.addAll(bll.getSongsInPlaylist(playlist));
        return songsInPlaylist;
    }

    public ObservableList<String> getSongsInPlaylistTitles(Playlist playlist){
        getSongsInPlaylist(playlist);
        ObservableList<String> titles = FXCollections.observableArrayList();
        songsInPlaylist.forEach(song -> titles.add(song.getTitle()));
        return titles;
    }
}
