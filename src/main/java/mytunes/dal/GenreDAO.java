package mytunes.dal;

import mytunes.be.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreDAO {

    public Genre createGenre(String name) {
        Genre genre = new Genre(name);
        //TODO add genre to database
        return genre;
    }

    public List<Genre> getAllGenres() {
        //TODO return a list of all genres, so the combobox can update
        List<Genre> genres = new ArrayList<>();
        return genres;
    }
}
