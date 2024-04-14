package poc.comment.demo.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.web.bind.annotation.RestController;

import poc.comment.demo.model.ErrorMessage;
import poc.comment.demo.model.Publication;
import poc.comment.demo.model.Review;
import poc.comment.demo.model.ReviewDetail;
import poc.comment.demo.service.PublicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/publish")
public class PublicationController {

    private List<Publication> publicationsList = new ArrayList<>();
    private ResponseEntity<ErrorMessage> error = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(),"publicacion no encontrada"));

    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public ResponseEntity<List<Publication>> getAllPublish() {
        return ResponseEntity.ok(publicationService.getAllPublications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublishById(@PathVariable Long id) {

        Optional<Publication> publication;

        publication = publicationService.getPublicationById(id);

        if (publication != null) {
            return ResponseEntity.ok(publication);
        }else{
            return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
        }

    }

    @GetMapping("/publish/{id}/edit/{title}/{description}")
    public ResponseEntity<?> updatePublish(@PathVariable String id,@PathVariable String title,@PathVariable String description) {
        int parsedId = validateInteger(id, "id");

        if(parsedId == -1){
            return error;
        }

        if (title.length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"title no puede estar vacio");
        }

        if (description.length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"description no puede estar vacio");
        }

        for (Publication publication : publicationsList) {
            if (publication.getId() == parsedId) {
                publication.setTitle(title);
                publication.setDescription(description);
                return ResponseEntity.ok(publication);
            }
        }

        return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
    }
    
    @GetMapping("/publish/add/{title}/{description}")
    public ResponseEntity<?> addPublish(@PathVariable String title,@PathVariable String description) {

        if (title.length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"title no puede estar vacio");
        }

        if (description.length() == 0) {
            return buildResponseError(HttpStatus.BAD_REQUEST,"description no puede estar vacio");
        }

        Random random = new Random();
        Publication localPublication = null;//new Publication(this.publicationsList.size()+1, random.nextInt(9000)+1000, title, description);

        this.publicationsList.add(localPublication);

        return ResponseEntity.ok(localPublication);
    }

    @GetMapping("/publish/{id}/review")
    public ResponseEntity<?> getReviewAverage(@PathVariable String id) {
        
        int parsedId = validateInteger(id, "id");

        if(parsedId == -1){
            return error;
        }

        for (Publication publication : publicationsList) {
            if (publication.getId() == parsedId) {

                if (publication.getReviewList().size() == 0) {
                    return buildResponseError(HttpStatus.NOT_FOUND,"la publicacion aun no tiene reseñas");
                }

                double sum = 0;
                for (Review review : publication.getReviewList()) {
                    sum += review.getStars();
                }
                double average = sum/publication.getReviewList().size();
                
                DecimalFormat format = new DecimalFormat("#.##");

                return ResponseEntity.ok(new ReviewDetail(parsedId, publication.getTitle(), Double.parseDouble(format.format(average))));
            }
        }

        return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
    }
    
    @GetMapping("/publish/{id}/addreview/{title}/{description}/{stars}")
    public ResponseEntity<?> addReviewByPublishId(@PathVariable String id,@PathVariable String title,@PathVariable String description,@PathVariable String stars) {
        int parsedId = validateInteger(id, "id");
        int parsedStars = validateInteger(stars, "stars");

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
                return ResponseEntity.ok(publication.addReviewList(new Review(random.nextInt(9000)+1000, title, description, parsedStars)));
            }
        }
        
        return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
    }

    private int validateInteger(String intAsStr,String paramName){
        try {
            int parsedInt = Integer.parseInt(intAsStr);

            if(parsedInt < 0){
                parsedInt = -1;
                error = buildResponseError(HttpStatus.BAD_REQUEST,paramName+" no puede ser negativo");
            }

            return parsedInt;
        } catch (Exception e) {
            error = buildResponseError(HttpStatus.BAD_REQUEST,paramName+" no valido");
            return -1;
        }
    }

    private ResponseEntity<ErrorMessage> buildResponseError(HttpStatus status,String message){
        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),message));
    }
}
