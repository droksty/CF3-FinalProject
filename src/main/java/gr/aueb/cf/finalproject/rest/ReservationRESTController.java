package gr.aueb.cf.finalproject.rest;

import gr.aueb.cf.finalproject.dto.MessageDTO;
import gr.aueb.cf.finalproject.dto.ReservationDTO;
import gr.aueb.cf.finalproject.model.Reservation;
import gr.aueb.cf.finalproject.model.RoomType;
import gr.aueb.cf.finalproject.service.IReservationService;
import gr.aueb.cf.finalproject.service.exceptions.ReservationAlreadyExistsException;
import gr.aueb.cf.finalproject.service.exceptions.ReservationNotFoundException;
import gr.aueb.cf.finalproject.service.exceptions.IdNotFoundException;
import gr.aueb.cf.finalproject.service.exceptions.ValidationErrorException;
import gr.aueb.cf.finalproject.validator.ReservationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ReservationRESTController {
    private final IReservationService service;
    private final ReservationValidator validator;

    @Autowired
    public ReservationRESTController(IReservationService reservationService, ReservationValidator reservationValidator) {
        this.service = reservationService;
        this.validator = reservationValidator;
    }


    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> insertReservation(@RequestBody ReservationDTO dto, BindingResult bindingResult)
            throws ReservationAlreadyExistsException, ValidationErrorException {

        validator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) throw new ValidationErrorException();

        if (service.reservationExists(dto.getReference())) throw new ReservationAlreadyExistsException(dto.getReference());

        Reservation insertedReservation = service.insertReservation(dto);
        ReservationDTO insertedReservationDTO = entityToDTO(insertedReservation);

        return new ResponseEntity<>(insertedReservationDTO, HttpStatus.OK);
    }


    @PutMapping("/reservations")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO dto, BindingResult bindingResult)
            throws IdNotFoundException, ValidationErrorException, ReservationAlreadyExistsException {

        validator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) throw new ValidationErrorException();

        if (!service.reservationExistsById(dto.getId())) throw new IdNotFoundException(dto.getId());

        Reservation persistedRes = service.findReservation(dto.getReference());
        if (!(persistedRes == null)) {
            if (!Objects.equals(persistedRes.getId(), dto.getId())) throw new ReservationAlreadyExistsException(dto.getReference());
        }

        Reservation updatedReservation = service.updateReservation(dto);
        ReservationDTO updatedReservationDTO = entityToDTO(updatedReservation);

        return new ResponseEntity<>(updatedReservationDTO, HttpStatus.OK);
    }


    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<MessageDTO> deleteReservation(@PathVariable("id") String id) throws IdNotFoundException {

        if (!service.reservationExistsById(id)) throw new IdNotFoundException(id);

        service.deleteReservation(id);
        return new ResponseEntity<>(new MessageDTO("Reservation deleted successfully"), HttpStatus.OK);
    }


    @GetMapping("/reservations/{reference}")
    public ResponseEntity<ReservationDTO> findOneReservation(@PathVariable("reference") String reference)
            throws ReservationNotFoundException {

        Reservation reservation = service.findReservation(reference);
        if (reservation == null) throw new ReservationNotFoundException(reference);

        ReservationDTO reservationDTO = entityToDTO(reservation);

        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }


    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> getReservationsByGuestName(@RequestParam("guestName") String guestName) {

        List<Reservation> reservations = service.findReservationsByGuestName(guestName);
        //if (reservations.size() == 0) System.out.println("No reservations matching your search criteria were found");

        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationsDTO.add(entityToDTO(reservation));
        }

        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }


    // Advanced Queries - "Filters"
    @GetMapping("/reservations/filter/datesBetween")
    public ResponseEntity<List<ReservationDTO>> getReservationsByDatesBetween(@RequestParam String dateFrom, String dateTo) {

        List<Reservation> reservations = service.getReservationByCheckinBetween(dateFrom, dateTo);
        //if (reservations.size() == 0) System.out.println("No reservations matching your search criteria were found");

        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationsDTO.add(entityToDTO(reservation));
        }

        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping("/reservations/filter/checkIn")
    public ResponseEntity<List<ReservationDTO>> getReservationsByCheckin(@RequestParam String checkIn) {

        List<Reservation> reservations = service.getReservationsByCheckIn(checkIn);
        //if (reservations.size() == 0) System.out.println("No reservations matching your search criteria were found");

        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationsDTO.add(entityToDTO(reservation));
        }

        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping("/reservations/filter/checkOut")
    public ResponseEntity<List<ReservationDTO>> getReservationsByCheckOut(@RequestParam String checkOut) {

        List<Reservation> reservations = service.getReservationsByCheckOut(checkOut);
        //if (reservations.size() == 0) System.out.println("No reservations matching your search criteria were found");

        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationsDTO.add(entityToDTO(reservation));
        }

        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping("/reservations/filter/roomType")
    public ResponseEntity<List<ReservationDTO>> getReservationsByRoomType(@RequestParam RoomType roomType) {

        List<Reservation> reservations = service.getReservationsByRoomType(roomType);
        //if (reservations.size() == 0) System.out.println("No reservations matching your search criteria were found");

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
                reservation.getTotalPrice() );
    }
}
