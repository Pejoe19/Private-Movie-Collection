package dk.easv.privatemoviecollection.BLL;

public class MovieException extends Exception {
    // Different constructors for the MovieException
    public MovieException(String message) { super(message); }

    public MovieException(String message, Throwable cause) { super(message, cause); }

    public MovieException(Throwable cause) { super(cause); }
}
