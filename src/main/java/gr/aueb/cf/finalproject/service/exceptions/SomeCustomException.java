package gr.aueb.cf.finalproject.service.exceptions;

public class SomeCustomException extends Exception {
    public SomeCustomException() {
        super("No reservation found matching your search criteria.");
    }
}
