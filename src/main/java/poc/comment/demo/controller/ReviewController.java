package poc.comment.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import poc.comment.demo.model.ErrorMessage;
import poc.comment.demo.model.Review;
import poc.comment.demo.service.PublicationService;
import poc.comment.demo.service.ReviewService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/review")
public class ReviewController {

    private ResponseEntity<ErrorMessage> error = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(),"publicacion no encontrada"));

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable String id) {

        Long parsedId = validateInteger(id, "id");

        if(parsedId == -1){
            return error;
        }

        Optional<Review> review = reviewService.getReviewById(parsedId);

        if (review.isEmpty()) {
            return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
        }
        
        return ResponseEntity.ok(review);

    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody Review review) {
        
        if(review.getId() < 0){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id no puede ser un valor negativo");
        }

        if(review.getUserId() <= 0 ){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id no puede ser un valor negativo o 0");
        }

        if(review.getUserId() > 9999){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id de usuario no puede ser superar 4 digitos");
        }

        if(review.getStars() < 1 || review.getStars() > 5){
            return buildResponseError(HttpStatus.BAD_REQUEST,"el rango de stars es de 1 a 5");
        }

        if (review.getTitle().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"title no puede estar vacio");
        }

        if (review.getDescription().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"description no puede estar vacio");
        }

        if (publicationService.existsPublicationById(review.getIdPublication())) {
            return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
        }

        return ResponseEntity.ok(reviewService.createReview(review));
    }

    @PutMapping
    public ResponseEntity<?> updateReview(@RequestBody Review review) {
        
        if(review.getId() < 0){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id no puede ser un valor negativo");
        }

        if(review.getUserId() <= 0){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id no puede ser un valor negativo o 0");
        }

        if(review.getUserId() > 9999){
            return buildResponseError(HttpStatus.BAD_REQUEST,"id de usuario no puede ser superar 4 digitos");
        }

        if(review.getStars() < 1 || review.getStars() > 5){
            return buildResponseError(HttpStatus.BAD_REQUEST,"el rango de stars es de 1 a 5");
        }

        if (review.getTitle().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"title no puede estar vacio");
        }

        if (review.getDescription().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"description no puede estar vacio");
        }

        if (publicationService.existsPublicationById(review.getIdPublication())) {
            return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
        }

        if (reviewService.existsReviewById(review.getId())) {
            return buildResponseError(HttpStatus.NOT_FOUND,"reseña no encontrada");
        }

        return ResponseEntity.ok(reviewService.updateReview(review.getId(),review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable String id){

        Long parsedId = validateInteger(id, "id");

        if(parsedId == -1){
            return error;
        }

        reviewService.deleteReview(parsedId);

        return ResponseEntity.ok().body("Reseña " + parsedId + " borrada.");
    }

    private Long validateInteger(String intAsStr,String paramName){
        try {
            Long parsedInt = Long.parseLong(intAsStr);

            if(parsedInt < 0){
                parsedInt = -1L;
                error = buildResponseError(HttpStatus.BAD_REQUEST,paramName+" no puede ser negativo");
            }

            return parsedInt;
        } catch (Exception e) {
            error = buildResponseError(HttpStatus.BAD_REQUEST,paramName+" no valido");
            return -1L;
        }
    }

    private ResponseEntity<ErrorMessage> buildResponseError(HttpStatus status,String message){
        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),message));
    }
}
