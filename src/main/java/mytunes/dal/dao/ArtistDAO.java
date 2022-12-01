package mytunes.dal.dao;

import mytunes.be.Artist;
import mytunes.dal.ConnectionManager;
import mytunes.dal.DAOTools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArtistDAO {
    ConnectionManager cm = new ConnectionManager();

    public Artist getArtist(int id) {
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT * FROM ARTISTS WHERE id = " + id;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int artistId = rs.getInt("id");
            String artistName = rs.getString("name");
            return new Artist(artistId, artistName);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void addArtist(Artist artist){
        String sql = "INSERT INTO ARTISTS (name) " +
                "VALUES ('" + DAOTools.validateStringForSQL(artist.getName()) + "')";
        try (Connection con = cm.getConnection()){
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE_name")) {
                System.out.println("Artist already exists");
                sql = "SELECT * FROM ARTISTS WHERE name = '" + DAOTools.validateStringForSQL(artist.getName()) + "'";
                try (Connection con = cm.getConnection()){
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    rs.next();
                    int id = rs.getInt("id");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else
                throw new RuntimeException(e);
            throw new RuntimeException(e);
        }
    }
}
