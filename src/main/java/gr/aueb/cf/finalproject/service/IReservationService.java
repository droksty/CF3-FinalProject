package gr.aueb.cf.finalproject.service;

import gr.aueb.cf.finalproject.dto.ReservationDTO;
import gr.aueb.cf.finalproject.model.Reservation;

import java.util.List;

public interface IReservationService {

    // Basic Public API
    /**
     * Inserts {@link Reservation} to the database.
     * @param reservationDTO    The {@link ReservationDTO} to insert
     * @return  The inserted {@link Reservation}
     */
    Reservation insertReservation(ReservationDTO reservationDTO);

    /**
     * Updates a {@link Reservation} in the database.
     * @param reservationDTO    The {@link ReservationDTO} to update
     * @return  The updated {@link Reservation}
     */
    Reservation updateReservation(ReservationDTO reservationDTO);

    /**
     * Deletes a {@link Reservation} from the database
     * @param id    The {@link Reservation#id} to delete with
     */
    void deleteReservation(String id);

    /**
     * Finds a {@link Reservation} from the database.
     * @param reference The {@link Reservation#reference} to search with.
     * @return  The {@link Reservation} matching the {@link Reservation#reference} parameter or null if no {@link Reservation} was found.
     */
    Reservation findReservation(String reference);

    /**
     * Finds every {@link Reservation} matching the {@link Reservation#guestName} string.
     * Alternatively if an empty string is provided, returns every {@link Reservation} in the database.
     * @param guestName The {@link Reservation#guestName} to search for. Can be partial or empty.
     * @return  A list of every {@link Reservation} matching the passed {@link Reservation#guestName} string.
     * Alternatively, a list of every {@link Reservation} in the database, if an empty string is provided.
     */
    List<Reservation> findReservationsByGuestName(String guestName);


    // "Advanced" Queries Public API
    /**
     * Finds every {@link Reservation} between two {@link Reservation#checkin} dates.
     * @param dateFrom   The {@link Reservation#checkin} date as String "yyyy-MM-dd" to start the search from.
     * @param dateTo     The {@link Reservation#checkin} date as String "yyyy-MM-dd" to end the search at.
     * @return  Every {@link Reservation} between two {@link Reservation#checkin} dates.
     */
    List<Reservation> getReservationByCheckinBetween(String dateFrom, String dateTo);

    /*List<Reservation> getReservationsByCheckIn(String checkIn);
    List<Reservation> getReservationsByCheckOut(String checkOut);
    List<Reservation> getReservationsByRoomType(String roomType);*/


    // Util
    boolean reservationExists(String reference);
    boolean reservationExistsById(String id);
}
