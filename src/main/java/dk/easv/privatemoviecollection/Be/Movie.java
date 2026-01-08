package dk.easv.privatemoviecollection.Be;

import javafx.scene.image.Image;

import java.util.Date;
import java.util.List;

public class Movie {

    private int Id;
    private String name;
    private List<Integer> categories;
    private String categoriesString;
    private float imdbRating;
    private float personalRating;
    private String filePath;
    private String trailerApiString;
    private Image image;
    private String overview;
    private Date lastViewed;

    public Movie(int id, String name, String categories, float imdbRating, float personalRating, String filePath, Date lastViewed) {
        Id = id;
        this.name = name;
        this.categoriesString = categories;
        this.categories = null;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.filePath = filePath;
        this.trailerApiString = null;
        this.image = null;
        this.overview = null;
        this.lastViewed = lastViewed;
    }

    public Movie(String name, List<Integer> categories, String overview, String filePath, String trailerApiString) {
        Id = -1;
        this.name = name;
        this.categories = categories;
        this.imdbRating = 0;
        this.personalRating = 0;
        this.filePath = filePath;
        this.image = null;
        this.trailerApiString = trailerApiString;
        this.overview = overview;
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

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public String getCategoriesString() {
        return categoriesString;
    }

    public void setCategoriesString(String categoriesString) {
        this.categoriesString = categoriesString;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
                ", categories=" + categories +
                ", categoriesString='" + categoriesString + '\'' +
                ", imdbRating=" + imdbRating +
                ", personalRating=" + personalRating +
                ", filePath='" + filePath + '\'' +
                ", trailerApiString='" + trailerApiString + '\'' +
                ", image=" + image +
                ", overview='" + overview + '\'' +
                ", lastViewed=" + lastViewed +
                '}';
    }
}
