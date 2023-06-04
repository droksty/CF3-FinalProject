package gr.aueb.cf.finalproject.rest;

import gr.aueb.cf.finalproject.dto.ReservationDTO;
import gr.aueb.cf.finalproject.model.Reservation;
import gr.aueb.cf.finalproject.service.IReservationService;
import gr.aueb.cf.finalproject.service.exceptions.ReservationAlreadyExistsException;
import gr.aueb.cf.finalproject.service.exceptions.ReservationNotFoundException;
import gr.aueb.cf.finalproject.service.exceptions.UnexpectedErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final IReservationService service;

    @Autowired
    public ReservationController(IReservationService reservationService) {
        this.service = reservationService;
    }


    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> insertReservation(@RequestBody ReservationDTO reservationDTO)
            throws ReservationAlreadyExistsException {

        String reference = reservationDTO.getReference().trim().toUpperCase();

        if (service.reservationExists(reference)) {
            throw new ReservationAlreadyExistsException(reference);
        }

        reservationDTO.setReference(reference);
        Reservation insertedReservation = service.insertReservation(reservationDTO);
        ReservationDTO insertedReservationDTO = entityToDTO(insertedReservation);

        return new ResponseEntity<>(insertedReservationDTO, HttpStatus.OK);
    }

    @PutMapping("/reservations")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationDTO)
            throws UnexpectedErrorException {

        String id = reservationDTO.getId().trim().toUpperCase();

        if (!service.reservationExistsById(id)) throw new UnexpectedErrorException(id);

        Reservation updatedReservation = service.updateReservation(reservationDTO);
        ReservationDTO updatedReservationDTO = entityToDTO(updatedReservation);

        return new ResponseEntity<>(updatedReservationDTO, HttpStatus.OK);
    }

    @DeleteMapping("/reservations")
    public ResponseEntity<String> deleteReservation(@RequestParam("id") String id)
            throws UnexpectedErrorException {

        if (!service.reservationExistsById(id)) throw new UnexpectedErrorException(id);

        service.deleteReservation(id);
        return new ResponseEntity<>("Reservation deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/reservations/{reference}")
    public ResponseEntity<ReservationDTO> findOneReservation(@PathVariable("reference") String reference)
            throws ReservationNotFoundException {

        Reservation reservation = service.findReservation(reference.trim().toUpperCase());
        ReservationDTO reservationDTO = entityToDTO(reservation);

        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> getReservationsByGuestName(@RequestParam("guestName") String guestName) {
        List<Reservation> reservations = service.findReservationsByGuestName(guestName);

        if (reservations.size() == 0) {
            // No reservations found matching your search criteria.
            System.out.println("Implement a user friendly msg like the comment above");
        }

        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationsDTO.add(entityToDTO(reservation));
        }

        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }


    //
    @GetMapping("/reservations/filter/datesBetween")
    public ResponseEntity<List<ReservationDTO>> getReservationsByDatesBetween(@RequestParam String dateFrom, String dateTo) {

        List<Reservation> reservations = service.getReservationByCheckinBetween(dateFrom, dateTo);

        /*if (reservations.size() == 0) {
            // No reservations found matching your search criteria.
            System.out.println("Implement a user friendly msg like the comment above");
        }*/

        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationsDTO.add(entityToDTO(reservation));
        }

        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }


    // Util
    private ReservationDTO entityToDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getReference(),
                reservation.getReservationDate(),
                reservation.getGuestName(),
                reservation.getCheckin(),
                reservation.getCheckOut(),
                reservation.getRoomType(),
                reservation.getTotalPrice());
    }
}
