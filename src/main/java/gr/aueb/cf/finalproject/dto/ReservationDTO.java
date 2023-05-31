package gr.aueb.cf.finalproject.dto;

import gr.aueb.cf.finalproject.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private String id;
    private String reference;
    private String reservationDate;
    private String guestName;
    private String checkIn;
    private String checkOut;
    private RoomType roomType;
    private Integer totalPrice;
}
