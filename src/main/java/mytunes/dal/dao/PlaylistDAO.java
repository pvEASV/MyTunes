package mytunes.dal.dao;

import mytunes.be.Playlist;
import mytunes.be.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static mytunes.dal.DAOTools.*;

public class PlaylistDAO {
    public List<Playlist> getAllPlaylists() {
        ArrayList<Playlist> allPlaylists = new ArrayList<>();

        String sql = "SELECT * FROM ALL_PLAYLISTS";
        try (ResultSet rs = SQLQueryWithRS(sql)){
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
        try  (ResultSet rs = SQLQueryWithRS(sql)){
            rs.next();
            return new Playlist(id, rs.getString("playlistName"), rs.getInt("total_length"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPlaylist(Playlist playlist) {
        String sql = "INSERT INTO ALL_PLAYLISTS (playlistName, total_length) " +
                "VALUES ('" + validateStringForSQL(playlist.getName()) + "', "
                + playlist.getTotalLength() + ")";
        try {
            SQLQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePlaylist(Playlist playlist) {
        String sql = "DELETE FROM ALL_PLAYLISTS WHERE id =" + playlist.getId();
        try  {
            SQLQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePlaylist(Playlist playlist) {
        String sql = "UPDATE ALL_PLAYLISTS SET playlistName = '" + validateStringForSQL(playlist.getName()) + "', "
                + "total_length = '" + playlist.getTotalLength() + "' "
                + "WHERE id = " + playlist.getId();
        try {
            SQLQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Song> getAllSongsInPlaylist(int playlistID) {
        ArrayList<Song> songsInPlaylist = new ArrayList<>();

        String sql = "SELECT * FROM SONG_PLAYLIST_LINK where playlistId = " + playlistID;
        try (ResultSet rs = SQLQueryWithRS(sql)) {
            while (rs.next()) {
                int songId = rs.getInt("songId");
                SongDAO songDAO = new SongDAO();
                Song song = songDAO.getSong(songId);
                songsInPlaylist.add(song);
            }
            return songsInPlaylist;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void addSongToPlaylist(int songID, int playlistID) {
        String sql = "INSERT INTO SONG_PLAYLIST_LINK (songId, playlistId) VALUES (" + songID + ", " + playlistID + ")";
        try {
            SQLQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
