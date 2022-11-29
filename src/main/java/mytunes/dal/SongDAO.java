package mytunes.dal;

import mytunes.be.Author;
import mytunes.be.Genre;
import mytunes.be.Song;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SongDAO {
    ConnectionManager cm = new ConnectionManager();

    public Song createSong(String title, String filepath) {
        Song song = new Song(title,filepath);
        //TODO add song to database
        return song;
    }

    public Genre createGenre(String name) {
        Genre genre = new Genre(name);
        //TODO add genre to database
        return genre;
    }

   /* public Song getSong(int id) {
        String sql = "SELECT * FROM ALL_SONGS WHERE id = " + id;
        try (Connection con = cm.getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Integer result_id = rs.getInt("id");
            System.out.println(result_id);

            return new Song(rs.getInt("id"), rs.getString("name"), new Author(404, "to be worked on"),
                    rs.getString("path"), new Genre(404, "to be worked on"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
}
