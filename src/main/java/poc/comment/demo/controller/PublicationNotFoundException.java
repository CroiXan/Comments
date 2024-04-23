package poc.comment.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublicationNotFoundException extends RuntimeException {

    public PublicationNotFoundException(String message){
        super(message);
    }

}
