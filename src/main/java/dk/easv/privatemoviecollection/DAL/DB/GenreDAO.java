package dk.easv.privatemoviecollection.DAL.DB;

import dk.easv.privatemoviecollection.BLL.MovieException;
import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements IGenreDataAccess {
    DBConnector dbConnector = new DBConnector();

    public GenreDAO() throws MovieException {
    }

    public List<Genre> getAllGenres() {
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


    public void deleteGenre(Movie movie, Genre genre) {
        String sqlDelete = "DELETE FROM dbo.CatMovie WHERE MovieId = ? AND GenreId = ?";

        try (Connection conn = DBConnector.getStaticConnection();
             PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {

            stmtDelete.setInt(1, movie.getId());
            stmtDelete.setInt(2, genre.getId());
            stmtDelete.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createGenre(Movie movie, Integer genreId) {
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
