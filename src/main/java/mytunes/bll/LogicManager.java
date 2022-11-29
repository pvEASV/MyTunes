package mytunes.bll;

import mytunes.be.Genre;
import mytunes.be.Song;
import mytunes.dal.SongDAO;

public class LogicManager {
    private SongDAO songDAO = new SongDAO();

    public Song createSong(String title, String filepath) {
        return songDAO.createSong(title, filepath);
    }

    public Genre createGenre(String name) {
        return songDAO.createGenre(name);
    }
}
