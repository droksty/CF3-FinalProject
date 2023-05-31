package gr.aueb.cf.finalproject.service.exceptions;

public class ReservationAlreadyExistsException extends Exception {
    public ReservationAlreadyExistsException(String reference) {
        super("Error. A reservation with reference " + reference + " already exists.");
    }
}
