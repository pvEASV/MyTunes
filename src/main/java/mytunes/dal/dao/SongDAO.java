package mytunes.dal.dao;

import mytunes.be.Author;
import mytunes.be.Genre;
import mytunes.be.Song;
import mytunes.dal.ConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SongDAO {
    ConnectionManager cm = new ConnectionManager();

    public Song getSong(int id) {
        String sql = "SELECT * FROM ALL_SONGS WHERE id = " + id;
        try (Connection con = cm.getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            Integer result_id = rs.getInt("id");
            System.out.println(result_id);

            return new Song(id, rs.getString("title"), new Author(rs.getString("author")),
                    rs.getInt("duration"), rs.getString("filepath"), new Genre(rs.getString("genre")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addSong(Song song){
        String sql = "INSERT INTO ALL_SONGS (title, author, genre, filepath, duration) VALUES ('" + song.getTitle() + "', '" + song.getAuthor().getName() + "', '" + song.getGenre().getName() + "', '" + song.getPath() + "', " + song.getDuration() + ")";
        try (Connection con = cm.getConnection()){
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            if (e.getMessage().contains("C_unique_title"))
            {
                System.out.println("Song already exists");
                //throw new IOException("Song already exists");
            } else
                throw new RuntimeException(e);
        }
    }
}
