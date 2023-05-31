package gr.aueb.cf.finalproject.service.exceptions;

public class UnexpectedErrorException extends Exception {
    public UnexpectedErrorException(String reservationId) {
        super("Unexpected error with Reservation : " + reservationId);
    }
}
