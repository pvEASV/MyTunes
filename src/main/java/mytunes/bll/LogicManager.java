package mytunes.bll;

import mytunes.be.Artist;
import mytunes.be.Genre;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.dal.dao.GenreDAO;
import mytunes.dal.dao.PlaylistDAO;
import mytunes.dal.dao.SongDAO;

import java.util.ArrayList;
import java.util.List;

public class LogicManager {
    private final SongDAO songDAO = new SongDAO();
    private final GenreDAO genreDAO = new GenreDAO();
    private final PlaylistDAO playlistDAO = new PlaylistDAO();
    private List<Song> allSongs;

    public void createSong(String title, String filepath) {
        songDAO.addSong(new Song(title, new Artist("some author"), new Genre("test genre"), filepath, 404));
    }

    public void deleteSong(Song song){
        songDAO.deleteSong(song);
    }

    public void updateSong(Song song) {
        songDAO.editSong(song);
    }

    public List<Song> getAllSongs() {
        return songDAO.getAllSongs();
    }

    public void createPlaylist(Playlist playlist){
        playlistDAO.addPlaylist(playlist);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistDAO.getAllPlaylists();
    }

    public void deletePlaylist(Playlist playlist) {
        playlistDAO.deletePlaylist(playlist);
    }

    public void updatePlaylist(Playlist playlist) {
        playlistDAO.updatePlaylist(playlist);
    }

    public void loadSongsToMemory(){
        allSongs = songDAO.getAllSongs();
    }

    public void removeSongsFromMemory(){
        allSongs.clear();
    }

    public List<Song> filterSongs(String query){
        List<Song> filteredSongs = new ArrayList<>();
        for (Song song : allSongs){
            if (song.getTitle().toLowerCase().trim().contains(query.toLowerCase())){
                filteredSongs.add(song);
            }
        }
        return filteredSongs;
    }

    public void createGenre(Genre genre) {
        genreDAO.createGenre(genre);
    }

    public void deleteGenre(Genre genre){
        genreDAO.deleteGenre(genre);
    }

    public void updateGenre(Genre genre){
        genreDAO.updateGenre(genre);
    }

    public List<Genre> getAllGenres() {
        return genreDAO.getAllGenres();
    }

    public void addSongToPlaylist(Song song, Playlist playlist){
        playlistDAO.addSongToPlaylist(song.getId(), playlist.getId());
    }
}
