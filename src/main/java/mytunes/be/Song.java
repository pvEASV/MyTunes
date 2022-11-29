package mytunes.be;

public class Song {
    private int id;
    private String title;
    private Author author;
    private int duration;
    private String path;
    private Genre genre;

    public Song(int id, String title, Author author, String path, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.path = path;
        this.genre = genre;
    }
}
