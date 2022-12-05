package mytunes.be;

public class Song {
    private int id, duration;
    private String title, path, durationAsAString;
    private Artist artist;
    private Genre genre;

    public Song(String title, Artist artist, Genre genre, String path,  int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.path = path;
        this.genre = genre;
    }

    public Song(int id, String title, Artist artist, Genre genre, String path, int duration) {
        this(title, artist, genre, path, duration);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getPath() {
        return path;
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

    public String toString(){
        return  "ID: " + id + ", Title: " + title + ", Artist: " + artist.getName() + ", Genre: " + genre.getName() +
                ", Duration: " + duration + ", Path: " + path;
    }

    private void convertSecondsIntoString(int duration){
        durationAsAString = "";
        int seconds = duration % 60;
        int minutes = (duration / 60) % 60;
        int hours = (duration / 60) / 60;

        if (hours > 0){
            if (hours < 10)
                durationAsAString += "0";
            durationAsAString += hours + ":";
        }

        if (minutes < 10)
            durationAsAString += "0";
        durationAsAString += minutes + ":";

        if (seconds < 10)
            durationAsAString += "0";
        durationAsAString += seconds;
    }

    public String getDurationAsAString(){  // used by PropertyValueFactory
        convertSecondsIntoString(this.duration);
        return durationAsAString;
    }
}
