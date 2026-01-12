package dk.easv.privatemoviecollection.DAL;

import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;

import java.sql.*;
import java.util.ArrayList;

public class MovieGenreDAO {

    public ArrayList<Genre> getMovieGenres(Movie movie) {
        ArrayList<Genre> movieGenres = new ArrayList<>();

        try(Connection conn = DBConnector.getStaticConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "select GenreId, G.Name from CatMovie\n" +
                            "join dbo.Genre G on G.Id = CatMovie.GenreId\n" +
                            "where movieId = ?"
                    , Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1,movie.getId());
            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                int genreId = rs.getInt("GenreId");
                String genreName = rs.getString("Name");
                Genre genre = new Genre(genreId, genreName);
                movieGenres.add(genre);
            }
            return movieGenres;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteGenre(Movie movie, Integer genreId) {
        String sqlDelete = "DELETE FROM dbo.CatMovie WHERE MovieId = ? AND GenreId = ?";

        try (Connection conn = DBConnector.getStaticConnection();
             PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {

            stmtDelete.setInt(1, movie.getId());
            stmtDelete.setInt(2, genreId);
            stmtDelete.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addGenre(Movie movie, Integer genreId) {
        String sqlDelete = "INSERT INTO dbo.CatMovie (GenreId, MovieId) VALUES (?,?)";

        try (Connection conn = DBConnector.getStaticConnection();
             PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {

            stmtDelete.setInt(1, genreId);
            stmtDelete.setInt(2, movie.getId());
            stmtDelete.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
