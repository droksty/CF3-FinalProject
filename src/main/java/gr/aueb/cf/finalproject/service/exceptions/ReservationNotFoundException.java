package gr.aueb.cf.finalproject.service.exceptions;

public class ReservationNotFoundException extends Exception {
    public ReservationNotFoundException(String reference) {
        super("Error. Reservation with reference: " + reference + " does not exist.");
    }
}
