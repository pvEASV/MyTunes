package mytunes.dal;

import mytunes.be.Song;

public class SongDAO {

    public Song createSong(String title, String filepath) {
        System.out.println("success");
        return new Song(title, filepath);
    }

}
