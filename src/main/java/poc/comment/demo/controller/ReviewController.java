package poc.comment.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import poc.comment.demo.model.ParsedLong;
import poc.comment.demo.model.Review;
import poc.comment.demo.service.PublicationService;
import poc.comment.demo.service.ReviewService;
import poc.comment.demo.service.ServiceUtils;

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

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private ReviewService reviewService;

    private ServiceUtils serviceUtils;

    @GetMapping
    public CollectionModel<EntityModel<Review>> getAllReviews() {

        List<Review> reviews = reviewService.getAllReviews();

        List<EntityModel<Review>> reviewResource = reviews.stream()
            .map(review -> EntityModel.of(review,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReviewById(review.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());
        
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReviews());

        CollectionModel<EntityModel<Review>> resources = CollectionModel.of(reviewResource, linkTo.withRel("review"));

        return resources;

    }
    
    @GetMapping("/{id}")
    public EntityModel<Review> getReviewById(@PathVariable Long id) {

        Optional<Review> pelicula = reviewService.getReviewById(id);

        if (pelicula.isPresent()) {
            return EntityModel.of(pelicula.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReviewById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReviews()).withRel("all-reviews")
            );
        }else{
            throw new ReviewNotFoundException("reseña de venta no encontrado con id: " + id);
        }

    }

    @PostMapping
    public EntityModel<Review> addReview(@RequestBody Review review) {
        
        if(review.getId() != null && review.getId() < 0){
            throw new ReviewBadRequestException("id no puede ser un valor negativo");
        }

        review.setId(null);

        if(review.getUserId() <= 0 ){
            throw new ReviewBadRequestException("id de usuario no puede ser un valor negativo o 0");
        }

        if(review.getUserId() > 9999){
            throw new ReviewBadRequestException("id de usuario no puede ser superar 4 digitos");
        }

        if(review.getStars() < 1 || review.getStars() > 5){
            throw new ReviewBadRequestException("el rango de stars es de 1 a 5");
        }

        if (review.getTitle().length() == 0) {
            throw new ReviewBadRequestException("title no puede estar vacio");
        }

        if (review.getDescription().length() == 0) {
            throw new ReviewBadRequestException("description no puede estar vacio");
        }

        if (!publicationService.existsPublicationById(review.getIdPublication())) {
            throw new PublicationNotFoundException("publicacion no encontrada");
        }

        Review createdReview = reviewService.createReview(review);
        return EntityModel.of(createdReview,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReviewById(createdReview.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReviews()).withRel("all-reviews")
        );

    }

    @PutMapping("/{id}")
    public EntityModel<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {

        if (review.getId() == null) {
            throw new ReviewBadRequestException("id no puede estar vacio");
        }
        
        if(review.getId() < 0){
            throw new ReviewBadRequestException("id no puede ser un valor negativo");
        }

        if(review.getUserId() <= 0){
            throw new ReviewBadRequestException("id de usuario no puede ser un valor negativo o 0");
        }

        if(review.getUserId() > 9999){
            throw new ReviewBadRequestException("id de usuario no puede ser superar 4 digitos");
        }

        if(review.getStars() < 1 || review.getStars() > 5){
            throw new ReviewBadRequestException("el rango de stars es de 1 a 5");
        }

        if (review.getTitle().length() == 0) {
            throw new ReviewBadRequestException("title no puede estar vacio");
        }

        if (review.getDescription().length() == 0) {
            throw new ReviewBadRequestException("description no puede estar vacio");
        }

        if (!publicationService.existsPublicationById(review.getIdPublication())) {
            throw new PublicationNotFoundException("publicacion no encontrada");
        }

        if (!reviewService.existsReviewById(review.getId())) {
            throw new ReviewNotFoundException("reseña no encontrada");
        }

        Review updatedReviewl = reviewService.updateReview(id, review);
        return EntityModel.of(updatedReviewl,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getReviewById(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllReviews()).withRel("all-reviews")
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable String id){

        ParsedLong parsedId = serviceUtils.validateLong(id, "id");

        if (!parsedId.isSuccess()) {
            throw new ReviewBadRequestException(parsedId.getErrorMessage());
        }

        if (!reviewService.existsReviewById(parsedId.getResultLong())) {
            throw new ReviewNotFoundException("reseña no encontrada");
        }

        reviewService.deleteReview(parsedId.getResultLong());

        return ResponseEntity.ok().body("Reseña " + parsedId.getResultLong() + " borrada.");
    }

}
