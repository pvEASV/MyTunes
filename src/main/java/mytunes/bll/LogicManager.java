package mytunes.bll;

import mytunes.be.Author;
import mytunes.be.Genre;
import mytunes.be.Song;
import mytunes.dal.dao.GenreDAO;
import mytunes.dal.dao.SongDAO;

import java.util.List;

public class LogicManager {
    private SongDAO songDAO = new SongDAO();
    private GenreDAO genreDAO = new GenreDAO();

    public void createSong(String title, String filepath) {
        //TODO songDAO.addSong(new Song(title, new Author("some author"),new Genre("test genre"), filepath, 404));
    }

    public Genre createGenre(String name) {
        return genreDAO.createGenre(name);
    }

    public List<Genre> getAllGenres() {
        return genreDAO.getAllGenres();
    }


}
