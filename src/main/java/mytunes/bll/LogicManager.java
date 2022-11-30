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

    public Genre createGenre(String name) {
        return genreDAO.createGenre(name);
    }

    public List<Genre> getAllGenres() {
        return genreDAO.getAllGenres();
    }

    public List<Song> filterSongs(String query){
        List<Song> allSongs = songDAO.getAllSongs();
        List<Song> filteredSongs = new ArrayList<>();
        for (Song song : allSongs){
            if (song.getTitle().toLowerCase().trim().contains(query.toLowerCase())){
                filteredSongs.add(song);
            }
        }
        return filteredSongs;
    }
}
