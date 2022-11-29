package mytunes.bll;

import mytunes.be.Genre;
import mytunes.be.Song;
import mytunes.dal.GenreDAO;
import mytunes.dal.SongDAO;

import java.util.List;

public class LogicManager {
    private SongDAO songDAO = new SongDAO();
    private GenreDAO genreDAO = new GenreDAO();

    public void createSong(String title, String filepath) {
        songDAO.createSong(title, filepath);
    }

    public Genre createGenre(String name) {
        return genreDAO.createGenre(name);
    }

    public List<Genre> getAllGenres() {
        return genreDAO.getAllGenres();
    }
}
