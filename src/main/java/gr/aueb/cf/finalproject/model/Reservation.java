package gr.aueb.cf.finalproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    private String id;
    private String reference;
    private String reservationDate;
    private String guestName;
    private String checkin;
    private String checkOut;
    private RoomType roomType;
    private Integer totalPrice;


    public Reservation(String reference, String reservationDate, String guestName, String checkin, String checkOut, RoomType roomType, Integer totalPrice) {
        this.reference = reference;
        this.reservationDate = reservationDate;
        this.guestName = guestName;
        this.checkin = checkin;
        this.checkOut = checkOut;
        this.roomType = roomType;
        this.totalPrice = totalPrice;
    }
}
