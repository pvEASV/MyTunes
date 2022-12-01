package mytunes.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunes.be.Genre;
import mytunes.dal.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    ConnectionManager cm = new ConnectionManager();

    public List<Genre> getAllGenres() {
        ArrayList<Genre> allGenres = new ArrayList<>();
        try (Connection con = cm.getConnection()){
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM GENRES");
            while (rs.next()){
                int id = rs.getInt("id");
                String genreName = rs.getString("name");
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
        try (Connection con = cm.getConnection()){
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM GENRES WHERE id = " + id);
            rs.next();
            return new Genre(id, rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createGenre(Genre genre) {
        //TODO alter, so a user can't create a genre that already exists
        String sql = "INSERT INTO GENRES (genreName) " + "VALUES ('" + validateStringForSQL(genre.getName()) + "')";
        try (Connection con = cm.getConnection()){
            con.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGenre(Genre genre){
        try (Connection con = cm.getConnection()){
            con.createStatement().execute("DELETE FROM GENRES WHERE id = " + genre.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGenre(Genre genre){
        String sql = "UPDATE GENRES SET genreName = '" + validateStringForSQL(genre.getName()) + "'"
                + "WHERE id = " + genre.getId();
        try (Connection con = cm.getConnection()){
            con.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String validateStringForSQL(String string){
        if (string == null) return null;
        string = string.replace("'", "''");
        return string;
    }
}
