package dk.easv.privatemoviecollection.Be;

public class Genre {
    //Instance variables
    private int id;
    private String name;

    /**
     * Genre constructor
     * @param id genre id int
     * @param name genre name string
     */
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Id get method
     * @return the genre id
     */
    public int getId() {
        return id;
    }

    /**
     * Name get method
     * @return the genre name
     */
    public String getName() {
        return name;
    }
}
