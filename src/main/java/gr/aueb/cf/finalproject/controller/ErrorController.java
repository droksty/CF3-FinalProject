package gr.aueb.cf.finalproject.controller;

import gr.aueb.cf.finalproject.service.exceptions.ReservationAlreadyExistsException;
import gr.aueb.cf.finalproject.service.exceptions.ReservationNotFoundException;
import gr.aueb.cf.finalproject.service.exceptions.SomeCustomException;
import gr.aueb.cf.finalproject.service.exceptions.UnexpectedErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = ReservationAlreadyExistsException.class)
    public ResponseEntity<String> handler1(ReservationAlreadyExistsException e) {
//        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ReservationNotFoundException.class)
    public ResponseEntity<String> handler2(ReservationNotFoundException e) {
//        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handler3(UnexpectedErrorException e) {
//        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handler4(SomeCustomException e) {
//        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
