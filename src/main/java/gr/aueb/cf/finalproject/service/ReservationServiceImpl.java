package gr.aueb.cf.finalproject.service;

import gr.aueb.cf.finalproject.dto.ReservationDTO;
import gr.aueb.cf.finalproject.model.Reservation;
import gr.aueb.cf.finalproject.model.RoomType;
import gr.aueb.cf.finalproject.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationServiceImpl implements IReservationService {
    private final ReservationRepository repository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }


    // Basic CRUD Actions: Insert/ Update / Delete / FindOne / FindMany
    @Transactional
    @Override
    public Reservation insertReservation(ReservationDTO reservationDTO) {
        return repository.save(dtoToEntity(reservationDTO));
    }

    @Transactional
    @Override
    public Reservation updateReservation(ReservationDTO reservationDTO) {
        return repository.save(dtoToEntityWithId(reservationDTO));
    }

    @Transactional
    @Override
    public void deleteReservation(String id) {
        repository.deleteById(id);
    }

    @Override
    public Reservation findReservation(String reference) {
        return repository.findReservationByReference(reference.trim().toUpperCase());
    }

    @Override
    public List<Reservation> findReservationsByGuestName(String guestName) {
        return repository.findReservationsByGuestNameLike(guestName.toLowerCase());
    }


    // Advanced Queries
    @Override
    public List<Reservation> getReservationByCheckinBetween(String dateFrom, String dateTo) {
        return repository.findReservationsByCheckinBetween(dateFrom, dateTo);
    }

    @Override
    public List<Reservation> getReservationsByCheckIn(String checkIn) {
        return repository.findReservationsByCheckin(checkIn);
    }

    @Override
    public List<Reservation> getReservationsByCheckOut(String checkOut) {
        return repository.findReservationsByCheckOut(checkOut);
    }

    @Override
    public List<Reservation> getReservationsByRoomType(RoomType roomType) {
        return repository.findReservationsByRoomType(roomType);
    }


    // Util
    @Override
    public boolean reservationExists(String reference) {
        return repository.existsByReference(reference.trim().toUpperCase());
    }

    @Override
    public boolean reservationExistsById(String id) {
        return repository.existsById(id);
    }


    // Mappers
    private Reservation dtoToEntity(ReservationDTO dto) {
        return new Reservation(
                dto.getReference().trim().toUpperCase(),
                dto.getReservationDate(),
                dto.getGuestName().toLowerCase(),
                dto.getCheckIn(),
                dto.getCheckOut(),
                dto.getRoomType(),
                dto.getTotalPrice()
        );
    }

    private Reservation dtoToEntityWithId(ReservationDTO dto) {
        return new Reservation (
                dto.getId(),
                dto.getReference().trim().toUpperCase(),
                dto.getReservationDate(),
                dto.getGuestName().toLowerCase(),
                dto.getCheckIn(),
                dto.getCheckOut(),
                dto.getRoomType(),
                dto.getTotalPrice()
        );
    }

}
