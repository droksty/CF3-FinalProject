package gr.aueb.cf.finalproject.service.exceptions;

public class IdNotFoundException extends Exception {
    public IdNotFoundException(String reservationId) {
        super("Fatal error. Reservation with id: " + reservationId + " does not exist.");
    }
}
