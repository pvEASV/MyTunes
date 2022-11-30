package mytunes.dal.dao;

import mytunes.be.Author;
import mytunes.be.Genre;
import mytunes.be.Song;
import mytunes.dal.ConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDAO {
    ConnectionManager cm = new ConnectionManager();

    public List<Song> getAllSongs() {
        ArrayList<Song> allSongs =new ArrayList<>();
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT * FROM ALL_SONGS";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author= rs.getString("author");
                String genre = rs.getString("genre");
                int duration = rs.getInt("duration");
                String path = rs.getString("filepath");
                Song song = new Song(id, title, new Author(author), duration, path, new Genre(genre));
                allSongs.add(song);
            }
            return allSongs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
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
        String sql = "INSERT INTO ALL_SONGS (title, author, genre, filepath, duration) " +
                        "VALUES ('" + validateStringForSQL(song.getTitle()) + "', '"
                        + validateStringForSQL(song.getAuthor().getName()) + "', '"
                        + validateStringForSQL(song.getGenre().getName()) + "', '"
                        + validateStringForSQL(song.getPath()) + "', "
                        + song.getDuration() + ")";
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

    public void editSong(Song song){
        String sql = "UPDATE ALL_SONGS SET title = '" + validateStringForSQL(song.getTitle()) + "', "
                + "author = '" + validateStringForSQL(song.getAuthor().getName()) + "', "
                + "genre = '" + validateStringForSQL(song.getGenre().getName()) + "', "
                + "filepath = '" + validateStringForSQL(song.getPath()) + "', "
                + "duration = " + song.getDuration() + " "
                + "WHERE id = " + song.getId();
        try (Connection con = cm.getConnection()){
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Used to replace ' with '' in a string to make it SQL compatible
     * @param string the string to check
     * @return the string with ' replaced by ''
     */
    public String validateStringForSQL(String string){
        string = string.replace("'", "''");
        return string;
    }
}
