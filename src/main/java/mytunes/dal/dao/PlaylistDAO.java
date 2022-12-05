package mytunes.dal.dao;

import mytunes.be.Playlist;
import mytunes.be.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static mytunes.dal.DAOTools.*;
import static mytunes.dal.DAOTools.SQLQueryWithRS;

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
        int id = playlist.getId();
        String sql = "DELETE FROM ALL_PLAYLISTS WHERE id =" + id;
        String sql_delete = "DELETE FROM SONG_PLAYLIST_LINK WHERE playlistID = " + id;
        try  {
            SQLQuery(sql_delete);
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
        String sql = "SELECT * FROM SONG_PLAYLIST_LINK WHERE playlistId = " + playlistID + " ORDER BY songIndex";
        try (ResultSet rs = SQLQueryWithRS(sql)) {
            while (rs.next()) {
                int songId = rs.getInt("songId");
                int songIndex = rs.getInt("songIndex") + 1;
                SongDAO songDAO = new SongDAO();
                Song song = songDAO.getSong(songId);
                song.setIndexInPlaylist(songIndex);
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
            calculateTotalLength(playlistID);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void moveSongInPlaylist(int songID, int playlistID, boolean moveUp, int songIndex){
        String sql;
        try {
            if (songIndex == 0)
                return;
            int newSongIndex;
            if (moveUp)
                newSongIndex = songIndex-1;
            else
                newSongIndex = songIndex+1;

            ResultSet rs = SQLQueryWithRS("SELECT * FROM SONG_PLAYLIST_LINK WHERE songIndex = " + newSongIndex + "AND playlistId = " + playlistID);
            int songAtIndexBeforeID = 0;
            while (rs.next()) {
                songAtIndexBeforeID = rs.getInt("songId");
            }

            if (songAtIndexBeforeID != songID){
                sql = "UPDATE SONG_PLAYLIST_LINK SET songIndex = " + newSongIndex + " WHERE playlistId = "
                        + playlistID + " AND songId = " + songID + "AND songIndex = " + songIndex;
                SQLQuery(sql);

                sql = "UPDATE SONG_PLAYLIST_LINK SET songIndex = " + songIndex + " WHERE playlistId = "
                        + playlistID + " AND songId = " + songAtIndexBeforeID + "AND songIndex = " + newSongIndex;
                SQLQuery(sql);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void updateIndexInPlaylist(int songID, int playlistID){
        int songIndex = getAllSongsInPlaylist(playlistID).size()-1;
        String sql = "UPDATE SONG_PLAYLIST_LINK SET songIndex = " + songIndex + " WHERE playlistId = "
                + playlistID + " AND songId = " + songID + "AND songIndex IS NULL";
        try{
            SQLQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteSongInPlaylist(int songID, int playlistID, int songIndex){
        List<Song> allSongsInPlaylist = getAllSongsInPlaylist(playlistID);

        try{
            String sql = "DELETE FROM SONG_PLAYLIST_LINK WHERE playlistID = " + playlistID +
                    "AND songID = " + songID + "AND songIndex = " + songIndex;
            SQLQuery(sql);

            for (int i = songIndex+1; i < allSongsInPlaylist.size(); i++){
                int newSongIndex = i-1;
                songID = allSongsInPlaylist.get(i).getId();
                sql = "UPDATE SONG_PLAYLIST_LINK SET songIndex = " + newSongIndex + " WHERE playlistId = "
                        + playlistID + " AND songId = " + songID + "AND songIndex = " + i;
                SQLQuery(sql);
            }
            calculateTotalLength(playlistID);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void calculateTotalLength(int playlistID){
        List<Song> songsInPlaylist = getAllSongsInPlaylist(playlistID);
        try {
            int totalLength = 0;
            for (Song song : songsInPlaylist){
                totalLength += song.getDuration();
            }
            String sql = "UPDATE ALL_PLAYLISTS SET total_length = " + totalLength + " WHERE id = " + playlistID;
            SQLQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
