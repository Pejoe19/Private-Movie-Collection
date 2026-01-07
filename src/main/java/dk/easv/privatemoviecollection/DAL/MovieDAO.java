package dk.easv.privatemoviecollection.DAL;

import dk.easv.privatemoviecollection.Be.Movie;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

            String sql = "Select Movies.Id, Movies.Name, STRING_AGG(C.Name, ', ') AS Categories, Movies.ImdbRating AS \"IMDB Rating\", Movies.PersonalRating AS \"Personal Rating\", Movies.FileLink, Movies.LastView from Vores_Gruppe_Private_Movie_Collection.dbo.Movies\n" +
                    "JOIN dbo.CatMovie CM on Movies.Id = CM.MovieId\n" +
                    "JOIN dbo.Category C on C.Id = CM.CategoryId\n" +
                    "group by Movies.Id, Movies.Name, Movies.ImdbRating, Movies.PersonalRating, Movies.FileLink, Movies.LastView";
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
