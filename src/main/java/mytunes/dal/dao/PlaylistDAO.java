package mytunes.dal.dao;

import mytunes.be.Artist;
import mytunes.be.Genre;
import mytunes.be.Playlist;
import mytunes.be.Song;
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
                Playlist playlist = new Playlist(id, playlistName);
                allPlaylists.add(playlist);
            }
            return allPlaylists;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void getPlaylist(Playlist playlist){
        //TODO implement
    }

    public void addPlaylist(Playlist playlist){
        //TODO implement
    }

    public void deletePlaylist(Playlist playlist) {
        //TODO implement
    }

    public void updatePlaylist(Playlist playlist) {
        //TODO implement
    }
}
