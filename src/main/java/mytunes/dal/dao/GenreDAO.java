package mytunes.dal.dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import mytunes.be.Genre;
import mytunes.dal.ConnectionManager;
import mytunes.dal.DAOTools;

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
        try (Connection con = cm.getConnection()){
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM GENRES WHERE id = " + id);
            rs.next();
            return new Genre(id, rs.getString("genreName"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createGenre(Genre genre) {
        String sql = "INSERT INTO GENRES (genreName) " + "VALUES ('" + DAOTools.validateStringForSQL(genre.getName()) + "')";
        try (Connection con = cm.getConnection()){
            con.createStatement().execute(sql);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE_name")){
                sql = "SELECT * FROM GENRES WHERE genreName = '" + DAOTools.validateStringForSQL(genre.getName()) + "'";
                try (Connection con = cm.getConnection()){
                    ResultSet rs = con.createStatement().executeQuery(sql);
                    rs.next();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        }
    }
}
