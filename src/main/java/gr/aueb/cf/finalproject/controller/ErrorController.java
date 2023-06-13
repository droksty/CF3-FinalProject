package gr.aueb.cf.finalproject.controller;

import gr.aueb.cf.finalproject.dto.MessageDTO;
import gr.aueb.cf.finalproject.service.exceptions.IdNotFoundException;
import gr.aueb.cf.finalproject.service.exceptions.ReservationAlreadyExistsException;
import gr.aueb.cf.finalproject.service.exceptions.ReservationNotFoundException;
import gr.aueb.cf.finalproject.service.exceptions.ValidationErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = ReservationAlreadyExistsException.class)
    public ResponseEntity<MessageDTO> handler1(ReservationAlreadyExistsException e) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ReservationNotFoundException.class)
    public ResponseEntity<MessageDTO> handler2(ReservationNotFoundException e) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDTO> handler3(IdNotFoundException e) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDTO> handler4(ValidationErrorException e) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
