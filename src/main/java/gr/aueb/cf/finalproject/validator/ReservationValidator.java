package gr.aueb.cf.finalproject.validator;

import gr.aueb.cf.finalproject.dto.ReservationDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ReservationValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ReservationDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReservationDTO dto = (ReservationDTO) target;

        // Validate reference
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reference", "emptyOrWhitespace");
        if (dto.getReference().length() < 10) {
            errors.rejectValue("reference", "invalidLength");
        }

        // Validate reservationDate
        if (!isDateValid(dto.getReservationDate())) {
            errors.reject("InvalidDateFormat");
        }

        // Validate guestName
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "guestName", "emptyOrWhitespace");

        // Validate checkIn
        if (!isDateValid(dto.getCheckIn())) {
            errors.reject("InvalidDateFormat");
        }

        // Validate checkOut
        if (!isDateValid(dto.getCheckOut())) {
            errors.reject("InvalidDateFormat");
        }

        // Validate roomType
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomType", "emptyOrWhitespace");
    }


    private boolean isDateValid(String inputDate) {
        boolean isValid = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (inputDate != null) {
            try {
                Date parsedDate = sdf.parse(inputDate);
                if (sdf.format(parsedDate).equals(inputDate)) {
                    isValid = true;
                }
            } catch (ParseException e) {
//                e.printStackTrace();
            }
        }

        return isValid;
    }
}
