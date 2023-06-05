package gr.aueb.cf.finalproject.service.exceptions;

public class ValidationErrorException extends Exception {
    public ValidationErrorException() {
        super("Error in data Validation");
    }
}
