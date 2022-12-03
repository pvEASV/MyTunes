package mytunes.dal.dao;

import mytunes.be.Artist;
import mytunes.be.Genre;
import mytunes.be.Song;
import mytunes.dal.ConnectionManager;
import mytunes.dal.DAOTools;

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
                String artist= rs.getString("artist");
                String genre = rs.getString("genre");
                int duration = rs.getInt("duration");
                String path = rs.getString("filepath");
                Song song = new Song(id, title, new Artist(artist), new Genre(genre), path, duration);
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
            return new Song(id, rs.getString("title"), new Artist(rs.getString("artist")),
                    new Genre(rs.getString("genre")), rs.getString("filepath"), rs.getInt("duration"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addSong(Song song){
        String sql = "INSERT INTO ALL_SONGS (title, artist, genre, filepath, duration) " +
                        "VALUES ('" + DAOTools.validateStringForSQL(song.getTitle()) + "', '"
                        + DAOTools.validateStringForSQL(song.getArtist().getName()) + "', '"
                        + DAOTools.validateStringForSQL(song.getGenre().getName()) + "', '"
                        + DAOTools.validateStringForSQL(song.getPath()) + "', "
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
        String sql = "UPDATE ALL_SONGS SET title = '" + DAOTools.validateStringForSQL(song.getTitle()) + "', "
                + "artist = '" + DAOTools.validateStringForSQL(song.getArtist().getName()) + "', "
                + "genre = '" + DAOTools.validateStringForSQL(song.getGenre().getName()) + "', "
                + "filepath = '" + DAOTools.validateStringForSQL(song.getPath()) + "', "
                + "duration = " + song.getDuration() + " "
                + "WHERE id = " + song.getId();
        try (Connection con = cm.getConnection()){
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSong(Song song){
        String sql = "DELETE FROM ALL_SONGS WHERE id =" + song.getId();
        try (Connection con = cm.getConnection()){
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
