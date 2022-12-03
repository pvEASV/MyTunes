package mytunes.dal.dao;

import mytunes.be.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static mytunes.dal.DAOTools.*;

public class GenreDAO {
    public List<Genre> getAllGenres() {
        ArrayList<Genre> allGenres = new ArrayList<>();
        String sql = "SELECT * FROM GENRES";
        try (ResultSet rs = SQLQueryWithRS(sql)){
            while (rs.next()){
                int id = rs.getInt("id");
                String genreName = rs.getString("genreName");
                Genre genre = new Genre(id, genreName);
                allGenres.add(genre);
            }
            return allGenres;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Genre getGenre(int id){
        try (ResultSet rs = SQLQueryWithRS("SELECT * FROM GENRES WHERE id = " + id)){
            rs.next();
            return new Genre(id, rs.getString("genreName"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createGenre(Genre genre) {
        String sql = "INSERT INTO GENRES (genreName) " + "VALUES ('" + validateStringForSQL(genre.getName()) + "')";
        try {
            for (Genre g : getAllGenres()){
                if (g.getName().toLowerCase().trim().equals(genre.getName().toLowerCase().trim())){
                    return;
                }
            }
            SQLQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGenre(Genre genre){
        try {
            SQLQuery("DELETE FROM GENRES WHERE id = " + genre.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGenre(Genre genre){
        String sql = "UPDATE GENRES SET genreName = '" + validateStringForSQL(genre.getName()) + "'"
                + "WHERE id = " + genre.getId();
        try {
           SQLQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
