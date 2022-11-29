package mytunes.be;

import java.util.HashMap;

public class Playlist {
    private int id;
    private String name;
    private int totalLength;
    private HashMap<Integer, Song> songs;

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Playlist(int id, String name, HashMap<Integer, Song> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
