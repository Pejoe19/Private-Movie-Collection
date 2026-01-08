package dk.easv.privatemoviecollection.DAL;

import dk.easv.privatemoviecollection.Be.Movie;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieDAO {
    private final DBConnector dbConnector = new DBConnector();

    public MovieDAO() throws IOException {
    }

    public List<Movie> getMovies() {
       List<Movie> movies = new ArrayList<>();

        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "Select Movie.Id, Movie.Name, STRING_AGG(C.Name, ', ') AS Categories, Movie.ImdbRating AS \"IMDB Rating\", Movie.PersonalRating AS \"Personal Rating\", Movie.FileLink, Movie.LastView from Vores_Gruppe_Private_Movie_Collection.dbo.Movie\n" +
                    "LEFT JOIN dbo.CatMovie CM on Movie.Id = CM.MovieId\n" +
                    "LEFT JOIN dbo.Category C on C.Id = CM.CategoryId\n" +
                    "group by Movie.Id, Movie.Name, Movie.ImdbRating, Movie.PersonalRating, Movie.FileLink, Movie.LastView";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String category = rs.getString("Categories");
                float imdbRating = rs.getFloat("Imdb Rating");
                float personalRating = rs.getFloat("Personal Rating");
                String filePath = rs.getString("FileLink");
                Date lastViewed = rs.getDate("LastView");

                movies.add(new Movie(id, name, category, imdbRating, personalRating, filePath, lastViewed));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return movies;
    }

    public Movie updateMovieLastViewed(Movie movie, LocalDate today) {
        try (Connection conn = dbConnector.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE dbo.Movie " +
                            "SET LastView = ? " +
                            "WHERE Id = ?"
            );

            ps.setDate(1, java.sql.Date.valueOf(today));
            ps.setInt(2, movie.getId());
            ps.executeUpdate();

            return movie;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMovie(Movie movie) {
        String deleteRelations = "DELETE FROM CatMovie WHERE MovieId = ?";
        String deleteMovie = "DELETE FROM Movies WHERE Id = ?";

        try (Connection conn = dbConnector.getConnection()) {

            try (PreparedStatement stmt1 = conn.prepareStatement(deleteRelations)) {
                stmt1.setInt(1, movie.getId());
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(deleteMovie)) {
                stmt2.setInt(1, movie.getId());
                stmt2.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
