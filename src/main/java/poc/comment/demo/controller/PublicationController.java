package poc.comment.demo.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import poc.comment.demo.model.ErrorMessage;
import poc.comment.demo.model.Publication;
import poc.comment.demo.model.Review;
import poc.comment.demo.model.ReviewDetail;
import poc.comment.demo.service.PublicationService;
import poc.comment.demo.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/publish")
public class PublicationController {

    private ResponseEntity<ErrorMessage> error = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(),"publicacion no encontrada"));

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Publication>> getAllPublish() {
        return ResponseEntity.ok(publicationService.getAllPublications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublishById(@PathVariable String id) {

        Long parsedId = validateInteger(id, "id");

        if(parsedId == -1){
            return error;
        }

        Optional<Publication> publication = publicationService.getPublicationById(parsedId);

        if (publication.isEmpty()) {
            return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
        }
        
        return ResponseEntity.ok(publication);

    }

    @PutMapping
    public ResponseEntity<?> updatePublish(@RequestBody Publication publication) {

        if(publication.getId() == -1L){
            return error;
        }

        if (publication.getTitle().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"title no puede estar vacio");
        }

        if (publication.getDescription().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"description no puede estar vacio");
        }

        if (publicationService.existsPublicationById(publication.getId())) {
            return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
        }

        return ResponseEntity.ok(publicationService.updatePublication(publication.getId(), publication));
        
    }
    
    @PostMapping
    public ResponseEntity<?> addPublish(@RequestBody Publication publication) {

        if (publication.getTitle().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"title no puede estar vacio");
        }

        if (publication.getDescription().length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"description no puede estar vacio");
        }

        return ResponseEntity.ok(publicationService.createPublication(publication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublish(@PathVariable String id){

        Long parsedId = validateInteger(id, "id");

        if(parsedId == -1){
            return error;
        }
        
        if (publicationService.existsPublicationById(parsedId)) {
            return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
        }

        for (Review review : reviewService.getAllReviews()) {
            if (review.getIdPublication() == parsedId) {
                reviewService.deleteReview(review.getId());
            }
        }

        publicationService.deletePublication(parsedId);

        return ResponseEntity.ok().body("Publicacion " + parsedId + " borrada.");
    }

    @GetMapping("/{id}/review")
    public ResponseEntity<?> getReviewAverage(@PathVariable String id) {
        
        Long parsedId = validateInteger(id, "id");
        int countReviews = 0;
        double starsSum = 0;

        if(parsedId == -1){
            return error;
        }

        Optional<Publication> publication = publicationService.getPublicationById(parsedId);

        if (publication.isEmpty()) {
            return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
        }

        for (Review review : reviewService.getAllReviews()) {
            if (review.getIdPublication() == parsedId) {
                countReviews++;
                starsSum += review.getStars();
            }
        }

        double average = starsSum/countReviews;
                
        DecimalFormat format = new DecimalFormat("#.##");

        return ResponseEntity.ok(new ReviewDetail(Long.valueOf(parsedId).intValue(), publication.get().getTitle(), Double.parseDouble(format.format(average))));

    }
    
    /*@GetMapping("/publish/{id}/addreview/{title}/{description}/{stars}")
    public ResponseEntity<?> addReviewByPublishId(@PathVariable String id,@PathVariable String title,@PathVariable String description,@PathVariable String stars) {
        Long parsedId = validateInteger(id, "id");
        Long parsedStars = validateInteger(stars, "stars");

        if(parsedId == -1 || parsedStars == -1){
            return error;
        }

        if(parsedStars < 1 || parsedStars > 5){
            return buildResponseError(HttpStatus.BAD_REQUEST,"el rango de stars es de 1 a 5");
        }

        if (title.length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"title no puede estar vacio");
        }

        if (description.length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"description no puede estar vacio");
        }

        for (Publication publication : publicationsList) {
            if (publication.getId() == parsedId) {
                Random random = new Random();
                return ResponseEntity.ok(publication.addReviewList(new Review(random.nextInt(9000)+1000, title, description, Long.valueOf(parsedStars).intValue() )));
            }
        }
        
        return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
    }*/

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
