package mytunes.dal.dao;

import mytunes.be.Playlist;
import mytunes.dal.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    ConnectionManager cm = new ConnectionManager();

    public List<Playlist> getAllPlaylists() {
        ArrayList<Playlist> allPlaylists = new ArrayList<>();
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT * FROM ALL_PLAYLISTS";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String playlistName = rs.getString("playlistName");
                int totalLength = rs.getInt("total_length");
                Playlist playlist = new Playlist(id, playlistName, totalLength);
                allPlaylists.add(playlist);
            }
            return allPlaylists;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Playlist getPlaylist(int id) {
        String sql = "SELECT * FROM ALL_PLAYLISTS WHERE id = " + id;
        try (Connection con = cm.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            return new Playlist(id, rs.getString("playlistName"), rs.getInt("total_length"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPlaylist(Playlist playlist){
        String sql = "INSERT INTO ALL_PLAYLISTS (playlistName, total_length) " +
                "VALUES ('" + validateStringForSQL(playlist.getName()) + "', "
                + playlist.getTotalLength() + ")";
        try (Connection con = cm.getConnection()){
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePlaylist(Playlist playlist) {
        String sql = "DELETE FROM ALL_PLAYLISTS WHERE id =" + playlist.getId();
        try (Connection con = cm.getConnection()){
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePlaylist(Playlist playlist) {
        String sql = "UPDATE ALL_PLAYLISTS SET playlistName = '" + validateStringForSQL(playlist.getName()) + "', "
                + "total_length = '" + playlist.getTotalLength() + "' "
                + "WHERE id = " + playlist.getId();
        try (Connection con = cm.getConnection()){
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String validateStringForSQL(String string){
        if (string == null) return null;
        string = string.replace("'", "''");
        return string;
    }
}
