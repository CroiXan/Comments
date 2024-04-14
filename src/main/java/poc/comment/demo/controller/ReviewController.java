package poc.comment.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import poc.comment.demo.model.ErrorMessage;

public class ReviewController {

    private ResponseEntity<ErrorMessage> error = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(),"publicacion no encontrada"));

    private ResponseEntity<ErrorMessage> buildResponseError(HttpStatus status,String message){
        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),message));
    }
}
