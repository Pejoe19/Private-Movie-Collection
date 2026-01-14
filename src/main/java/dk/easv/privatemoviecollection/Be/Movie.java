package dk.easv.privatemoviecollection.Be;

import javafx.scene.image.Image;

import java.util.Date;
import java.util.List;

public class Movie {

    private int Id;
    private String name;
    private List<Integer> genres;
    private String genresString;
    private float imdbRating;
    private float personalRating;
    private String pictureFilePath;
    private String movieFilePath;
    private String trailerApiString;
    private Image image;
    private String overview;
    private Date lastViewed;

    public Movie(int id, String name, String genres, float imdbRating, float personalRating, String pictureFilePath, String movieFilePath, Date lastViewed) {
        Id = id;
        this.name = name;
        this.genresString = genres;
        this.genres = null;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.pictureFilePath = pictureFilePath;
        this.movieFilePath = movieFilePath;
        this.trailerApiString = null;
        this.image = null;
        this.overview = null;
        this.lastViewed = lastViewed;
    }

    public Movie(String name, List<Integer> genres, String overview, String pictureFilePath, String trailerApiString) {
        Id = -1;
        this.name = name;
        this.genres = genres;
        this.imdbRating = 0;
        this.personalRating = 0;
        this.pictureFilePath = pictureFilePath;
        this.movieFilePath = "";
        this.image = null;
        this.trailerApiString = trailerApiString;
        this.overview = overview;
        this.lastViewed = null;
    }

    public Movie(String name, List<Integer> genres, float Imdb, float personalRating, String pictureFilePath, String movieFilePath) {
        Id = -1;
        this.name = name;
        this.genres = genres;
        this.imdbRating = Imdb;
        this.personalRating = personalRating;
        this.pictureFilePath = pictureFilePath;
        this.movieFilePath = movieFilePath;
        this.image = null;
        this.trailerApiString = "";
        this.overview = "";
        this.lastViewed = null;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public String getGenresString() {
        return genresString;
    }

    public void setGenresString(String genresString) {
        this.genresString = genresString;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public float getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(float personalRating) {
        this.personalRating = personalRating;
    }

    public String getPictureFilePath() {
        return pictureFilePath;
    }

    public void setPictureFilePath(String pictureFilePath) {
        this.pictureFilePath = pictureFilePath;
    }

    public String getMovieFilePath() {
        return movieFilePath;
    }

    public void setMovieFilePath(String movieFilePath) {
        this.movieFilePath = movieFilePath;
    }

    public String getTrailerApiString() {
        return trailerApiString;
    }

    public void setTrailerApiString(String trailerApiString) {
        this.trailerApiString = trailerApiString;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(Date lastViewed) {
        this.lastViewed = lastViewed;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", genres=" + genres +
                ", genresString='" + genresString + '\'' +
                ", imdbRating=" + imdbRating +
                ", personalRating=" + personalRating +
                ", pictureFilePath='" + pictureFilePath + '\'' +
                ", movieFilePath='" + movieFilePath + '\'' +
                ", trailerApiString='" + trailerApiString + '\'' +
                ", image=" + image +
                ", overview='" + overview + '\'' +
                ", lastViewed=" + lastViewed +
                '}';
    }
}
