package mytunes.be;

import mytunes.dal.dao.PlaylistDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Playlist {
    private int id, totalLength;
    private String name;
    private HashMap<Integer, Song> songs;
    private String totalLengthAsAString;
    private List<Song> songsInPlaylist;
    PlaylistDAO playlistDAO = new PlaylistDAO();

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Playlist(String name){
        this.name = name;
        songsInPlaylist = new ArrayList<>();
        songsInPlaylist.addAll(playlistDAO.getAllSongsInPlaylist(id));
        calculateLength();
    }

    public Playlist(String name, HashMap<Integer, Song> songs) {
        this.name = name;
        this.songs = songs;
    }

    public Playlist(int id, String playlistName, int totalLength) {
        this.id = id;
        this.name = playlistName;
        this.totalLength = totalLength;
        songsInPlaylist = new ArrayList<>();
        songsInPlaylist.addAll(playlistDAO.getAllSongsInPlaylist(id));
        calculateLength();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public HashMap<Integer, Song> getSongs() {
        return songs;
    }

    public void setSongs(HashMap<Integer, Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return  "id = " + id +
                ", name = '" + name + '\'' +
                ", totalLength = " + totalLength;
    }

    private void convertSecondsIntoString(int duration){
        totalLengthAsAString = "";
        int seconds = duration % 60;
        int minutes = (duration / 60) % 60;
        int hours = (duration / 60) / 60;

        if (hours > 0){
            if (hours < 10)
                totalLengthAsAString += "0";
            totalLengthAsAString += hours + ":";
        }

        if (minutes < 10)
            totalLengthAsAString += "0";
        totalLengthAsAString += minutes + ":";

        if (seconds < 10)
            totalLengthAsAString += "0";
        totalLengthAsAString += seconds;
    }

    public String getTotalLengthAsAString(){  // used by PropertyValueFactory
        convertSecondsIntoString(this.totalLength);
        return totalLengthAsAString;
    }

    public List<Song> getSongsInPlaylist(){
        return songsInPlaylist;
    }

    private void calculateLength(){
        for (Song song : songsInPlaylist){
            totalLength += song.getDuration();
        }
    }
}
