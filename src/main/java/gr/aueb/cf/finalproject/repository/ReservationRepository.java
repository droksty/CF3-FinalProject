package gr.aueb.cf.finalproject.repository;

import gr.aueb.cf.finalproject.model.Reservation;
import gr.aueb.cf.finalproject.model.RoomType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, String> {

    // Query validations
    boolean existsByReference(String reference);
    boolean existsById(String id);

    // Basic Queries
    Reservation findReservationByReference(String reference);
    List<Reservation> findReservationsByGuestNameLike(String guestName);


    // Advanced Queries
    @Query("{'checkin' : { '$gte' : ?0, '$lte' : ?1} }")
    List<Reservation> findReservationsByCheckinBetween(String dateFrom, String dateTo);

//    List<Reservation> findReservationsByCheckin(String checkIn);
//    List<Reservation> findReservationsByCheckOut(String checkOut);
//    List<Reservation> findReservationsByRoomType(RoomType roomType);
//    List<Reservation> findReservationsByReservationDate(String reservationDate);
}
