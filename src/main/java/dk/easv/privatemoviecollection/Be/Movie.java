package dk.easv.privatemoviecollection.Be;

import java.util.Date;

public class Movie {

    private int Id;
    private String name;
    private String categories;
    private float imdbRating;
    private float personalRating;
    private String filePath;
    private Date lastViewed;

    public Movie(int id, String name, String categories, float imdbRating, float personalRating, String filePath, Date lastViewed) {
        Id = id;
        this.name = name;
        this.categories = categories;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.filePath = filePath;
        this.lastViewed = lastViewed;
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
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
                ", categories='" + categories + '\'' +
                ", imdbRating=" + imdbRating +
                ", personalRating=" + personalRating +
                ", filePath='" + filePath + '\'' +
                ", lastViewed=" + lastViewed +
                '}';
    }
}
