package dk.easv.privatemoviecollection.DAL;

import dk.easv.privatemoviecollection.Be.Genre;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    DBConnector dbConnector = new DBConnector();

    public GenreDAO() throws IOException {
    }

    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();

        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "Select Id, Name from Vores_Gruppe_Private_Movie_Collection.dbo.Genre";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");

                genres.add(new Genre(id, name));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return genres;
    }
}
