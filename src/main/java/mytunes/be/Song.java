package mytunes.be;

public class Song {
    private int id;
    private String title;
    private Artist artist;
    private int duration;
    private String path;
    private Genre genre;

    public Song(String title, Artist artist, String path, Genre genre, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.path = path;
        this.genre = genre;
    }

    public Song(int id, String title, Artist artist, int duration, String path, Genre genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.path = path;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String toString(){
        return  "ID: " + id + ", Title: " + title + ", Author: " + artist.getName() + ", Genre: " + genre.getName() +
                ", Duration: " + duration + ", Path: " + path;
    }
}
