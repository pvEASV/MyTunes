package mytunes.be;

public class Song {
    private int id;
    private String title;
    private Author author;
    private int duration;
    private String path;
    private Genre genre;

    public Song(int id, String title, Author author, Genre genre, String path, int duration) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.path = path;
        this.genre = genre;
    }

    public Song(String title, Author author, Genre genre, String path,  int duration) {
        this.title = title;
        this.author = author;
        this.duration = duration;
        this.path = path;
        this.genre = genre;
    }

    public Song(String title, String path){
        this.title = title;
        this.path = path;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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
        return  "ID: " + id + ", Title: " + title + ", Author: " + author.getName() + ", Genre: " + genre.getName() +
                ", Duration: " + duration + ", Path: " + path;
    }
}
