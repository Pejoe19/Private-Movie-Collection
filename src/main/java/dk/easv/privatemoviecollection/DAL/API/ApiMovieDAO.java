package dk.easv.privatemoviecollection.DAL.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.easv.privatemoviecollection.BLL.MovieException;
import dk.easv.privatemoviecollection.Be.Genre;
import dk.easv.privatemoviecollection.Be.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiMovieDAO {

    APIConnector apiConnector = new APIConnector();
    List<Genre> genres = new ArrayList<>();

    public ApiMovieDAO() throws MovieException {
    }

    public Movie getMovieData(String movieTitle) {
        String movieDataJson = apiConnector.getJsonFromApi("https://api.themoviedb.org/3/search/movie?query="+ movieTitle +"&language=da-DK");

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(movieDataJson, Map.class);
            List<Map<String, Object>> movies = (List<Map<String, Object>>) map.get("results");
            if (!movies.isEmpty()) {
                Map<String, Object> firstMovie = movies.getFirst();

                if (firstMovie.get("poster_path") != null) {
                    String movieName = (String) firstMovie.get("original_title");
                    String movieOverview = (String) firstMovie.get("overview");
                    String trailerApiKey = getTrailerKey(String.valueOf(firstMovie.get("id")));
                    ArrayList<Integer> movieGenreIdList = (ArrayList<Integer>) firstMovie.get("genre_ids");
                    String movieImagePath = (String) firstMovie.get("poster_path");

                    return new Movie(movieName, movieGenreIdList, movieOverview, movieImagePath, trailerApiKey);
                }
            }
        } catch(JsonProcessingException e){
            throw new RuntimeException(e);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    private String getTrailerKey(String id) {
         String videoDataJson = apiConnector.getJsonFromApi("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=");

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(videoDataJson, Map.class);
            List<Map<String, Object>> videoData = (List<Map<String, Object>>) map.get("results");
            Map<String, Object> firstVideo = videoData.getFirst();

            return firstVideo.get("key").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getGenres() {
        String genreDataJson = APIConnector.getJsonFromApi("https://api.themoviedb.org/3/genre/movie/list?language=en");

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String,Object> map = mapper.readValue(genreDataJson, Map.class);
            List<Map<String,Object>> movies = (List<Map<String, Object>>) map.get("genres");

            for (Map<String,Object> genre : movies){
                genres.add(new Genre((Integer) genre.get("id"), (String) genre.get("name")));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
