package mytunes.bll;

import mytunes.be.Author;
import mytunes.be.Genre;
import mytunes.be.Song;
import mytunes.dal.dao.SongDAO;

public class LogicManager {
    private final SongDAO songDAO = new SongDAO();

    public void createSong(String title, String filepath) {
        songDAO.addSong(new Song(title, new Author("some author"), filepath, new Genre("test genre"), 404));
    }
}
