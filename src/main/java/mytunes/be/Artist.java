package mytunes.be;

import java.util.List;

public class Artist {
    private int id;
    private String name;
    private List<Genre> genres;

    public Artist(String name) {
        this.name = name;
    }

    public Artist(int artistId, String artistName) {}

    public void setId(int id) {
        this.id = id;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    @Override
    public String toString() {
        return name;
    }
}
