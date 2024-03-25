package poc.comment.demo;

import java.text.DecimalFormat;
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
    
        publicationsList.add(new Publication(2, 234, "La Cabrera Chile Alonso", "CUISINES : Argentinean, Steakhouse"));
        publicationsList.get(1).addReviewList(new Review(465, "DON'T GO!", "The Ritz recommended this restaurant. Our waitress didn't listen to a thing we said. The drinks were wrong, and the meal came all at the same time. It didn't even fit on the table. My husband was hungry for a steak", 1));
        publicationsList.get(1).addReviewList(new Review(327, "Charred steak, screwed up scores", "Few disappointments rival the impact of hyper-expectations colliding with a starkly different reality during restaurant visits. A dining establishment touted as one of the premier steakhouses globally (included in the prestigious list of the top 101 steakhouses worldwide, prominently displayed at the entrance) serves a charred (!!) steak that, in addition, missed the mark on the desired level of doneness", 2));
        publicationsList.get(1).addReviewList(new Review(467, "Great Service!", "The service was very good. The steaks were just okay. A bit tough and too fatty. A bit disappointing considering the high ratings. We expected a better quality meat.", 4));
        publicationsList.get(1).addReviewList(new Review(234, "Fantastic stunning beautiful steaks", "Fantastic stunning food ginormous steaks…. waiter Ricardo was brilliant and very helpful. Will definitely come back!", 5));
        publicationsList.get(1).addReviewList(new Review(156, "Fantastic food and fantastic staff", "Great food, great ambience, great waiter. Special thanks to Patricio. Really nice and most funniest guy. We really enjoyed the evening with him.", 5));

        publicationsList.add(new Publication(3, 576, "Alto Aji Seco", "CUISINES : Peruvian, Latin, Seafood"));
        publicationsList.get(2).addReviewList(new Review(943, "Excellent, but if it cost a bit less I'd give it 5 stars", "Excellent Peruvian restaurant in Las Condes. Lots of parking available. There aren't any other restaurants around that area so it's definitely a destination place. Nicely remodeled and decorated, friendly and courteous staff, great food served in generous portions. It's one of the better Peruvian restaurants we have tried in Chile so far. Better value than Tanta or La Mar.", 4));
        publicationsList.get(2).addReviewList(new Review(763, "Really good peruvian food in Santiago", "I really enjoyed this restaurant. it is in the end of las condes almost going to el Arrayan o plaza san enrique, the place is nice, has a piano, a terrace and a elevator. we tried the barbequed octopus, and the ceviche trio for starters, they where outstanding. The pisco sour was good also, but the dish that i enjoyed the most was the Lomo mary tierra (Lomo land and ocean).", 4));
        publicationsList.get(2).addReviewList(new Review(362, "Good food and good service.", "This is Peruin cuisine, the food is good so as the atmersfere and service. It is not cheap for the locals, but you pay for what you enjoy.v", 4));
        publicationsList.get(2).addReviewList(new Review(235, "Good food, poor service, high prices", "This restaurant serves refined peruvian food, but at very high prices, I domn´t think it is a good value-price place to go. Although this restaurant doesn´t have a closing hour (the same as almost every other restaurant in Chile), as soon as we finished with our dessert, they started to do things to get us out of tghe rerstaurant as soon as possible:very loud music, dim the lights and stopped the central heating so that we would be cold.", 2));
        publicationsList.get(2).addReviewList(new Review(635, "Delicious", "Great service, great food, great ambiance, and wonderful ceviches and pisco sours. It is a great restaurant to go, with family or just couples...", 5));

        publicationsList.add(new Publication(4, 875, "Tanta", "CUISINES : Peruvian, Latin, Seafood"));
        publicationsList.get(3).addReviewList(new Review(764, "Average food and service", "Big fan of Gaston Acurio and his restaurants but this one doesn’t seem to be at par. It’s more of a production line version of his restaurants. The service was just okay. Two items in the menu we asked for weren’t available.", 3));
        publicationsList.get(3).addReviewList(new Review(936, "Poor service. Orders take too long. Better places nearby. Rude waiters. I do not recommend this place at all!", "Poor service. Orders takes too long. Waiters are rude. Better places nearby, just look around! I don’t recommend this place at all!", 1));
        publicationsList.get(3).addReviewList(new Review(724, "Great Peruvian food, but a bit pricey", "A Peruvian friend recommended me this place so I had to try it. It's located inside a food court in a mall, but it's big enough that it isn't really a problem. It's really great; the food was excellent and the service also good.", 4));
        publicationsList.get(3).addReviewList(new Review(254, "delicious food and lovely appetizer", "amazing ceviche. We had very nice friendly service and a helpful waiter even though our spanish was minimal", 5));
        publicationsList.get(3).addReviewList(new Review(736, "Fantastic Octupus & Pisco", "Had a great lunch today with my business associates - had two Octopus with a great light spiced sauce and and superb chicken dish with equivalent sauce.", 4));

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
