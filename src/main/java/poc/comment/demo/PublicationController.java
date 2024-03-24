package poc.comment.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class PublicationController {

    private List<Publication> publicationsList = new ArrayList<>();
    private ResponseEntity<ErrorMessage> error = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(HttpStatus.NOT_FOUND.value(),"publicacion no encontrada"));

    public PublicationController() {

        publicationsList.add(new Publication(1,654, "La Finestra", "CUISINES : Italian, Pizza, Mediterranean, Neapolitan, Campania, Tuscan, Romana, Lazio, Sicilian, Central-Italian, Southern-Italian"));
        publicationsList.get(0).addReviewList(new Review(111, "Awesome italian restaurant in Santiago !", "If you’re looking for Italian food in Santiago, you have to go to La Finestra. Located in Ñuñoa, with a lovely garden hidden behind the entrance, you’ll enjoy a nice moment with your friends or family, discovering typical food and drinks from Italy.", 5));
        publicationsList.get(0).addReviewList(new Review(356, "Loved this place! Very cool vibe, setting. Authentic Italian pizza.", "I love a place where the owner is passionate about their food and their craft. If you go here, search out the owner and chat with him. Great guy from Italy, very passionate about the customer experience, the food and the venue. ", 5));
        publicationsList.get(0).addReviewList(new Review(763, "La peggior pizza!", "For the first time in xx years I did not finish the pizza ... my favorite food... they don’t have a brick oven, but that’s not the reason. The though is bitter and mostly without flavor! A very disappointing experience.", 1));
        publicationsList.get(0).addReviewList(new Review(953, "Delicious but I want more", "This place is worth going to Nuñoa for. The only thing I would say is that I would love to have bigger portion sizes. Had the gorgonzola gnocchi. Delicious.", 4));
        publicationsList.get(0).addReviewList(new Review(523, "The pizza is hardly eatable!", "I have visited this place several times with variable success. The pasta is generally good but last time it was overcooked and without any taste. What was really bad was the pizza. I ordered one with mozzarella di buffala.", 1));
        publicationsList.get(0).addReviewList(new Review(423, "Excellent food, poor service", "We were pleasantly surprised by the reviews on TripAdvisor to find out that the hole-in-the-wall entrance that we thought was a crummy place was in reality a beautiful restaurant inside. The pizza and pasta we ordered was delicious and the menu had a large and good selection. The problem was the service. ", 3));
    
    }

    @GetMapping("/publish")
    public ResponseEntity<List<Publication>> getAllPublish() {
        return ResponseEntity.ok(publicationsList);
    }

    @GetMapping("/publish/{id}")
    public ResponseEntity<?> getPublishById(@PathVariable String id) {

        int parsedId = validateInteger(id, "id");

        if(parsedId == -1){
            return error;
        }

        for (Publication publication : publicationsList) {
            if (publication.getId() == parsedId) {
                return ResponseEntity.ok(publication);
            }
        }

        return buildResponseError(HttpStatus.NOT_FOUND,"publicacion no encontrada");
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
        Publication localPublication = new Publication(this.publicationsList.size()+1, random.nextInt(9000)+1000, title, description);

        this.publicationsList.add(localPublication);

        return ResponseEntity.ok(localPublication);
    }

    @GetMapping("/publish/{id}/review")
    public ResponseEntity<?> getMethodName(@PathVariable String id) {
        
        int parsedId = validateInteger(id, "id");

        if(parsedId == -1){
            return error;
        }

        for (Publication publication : publicationsList) {
            if (publication.getId() == parsedId) {

                if (publication.getReviewList().size() == 0) {
                    return buildResponseError(HttpStatus.NOT_FOUND,"la publicacion aun no tiene reseñas");
                }

                int sum = 0;
                for (Review review : publication.getReviewList()) {
                    sum += review.getStars();
                }
                double average = sum/publication.getReviewList().size();
                
                return ResponseEntity.ok(new ReviewDetail(parsedId, publication.getTitle(), Math.round(average)));
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
