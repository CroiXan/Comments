package poc.comment.demo.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RestController;

import poc.comment.demo.model.ParsedLong;
import poc.comment.demo.model.Publication;
import poc.comment.demo.model.Review;
import poc.comment.demo.model.ReviewDetail;
import poc.comment.demo.service.PublicationService;
import poc.comment.demo.service.ReviewService;
import poc.comment.demo.service.ServiceUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private ReviewService reviewService;

    private ServiceUtils serviceUtils;

    @GetMapping
    public CollectionModel<EntityModel<Publication>> getAllPublish() {

        List<Publication> publications = publicationService.getAllPublications();

        List<EntityModel<Publication>> publicationResource = publications.stream()
            .map(product -> EntityModel.of(product,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublishById(product.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());
        
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPublish());

        CollectionModel<EntityModel<Publication>> resources = CollectionModel.of(publicationResource, linkTo.withRel("publish"));

        return resources;

    }

    @GetMapping("/{id}")
    public EntityModel<Publication> getPublishById(@PathVariable Long id) {

        Optional<Publication> publication = publicationService.getPublicationById(id);

        if (publication.isPresent()) {
            return EntityModel.of(publication.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublishById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPublish()).withRel("all-publish")
            );
        }else{
            throw new PublicationNotFoundException("Publicacion no encontrada con Id: " + id);
        }

    }

    @PutMapping("/{id}")
    public EntityModel<Publication> updatePublish(@PathVariable Long id,@RequestBody Publication publication) {

        if(publication.getId() == null){
            throw new PublicationBadRequestException("id no puede estar vacio");
        }

        if(publication.getId() < 0L){
            throw new PublicationBadRequestException("id no puede ser un valor negativo");
        }

        if(publication.getUserId() < 0L){
            throw new PublicationBadRequestException("id de usuario no puede ser un valor negativo");
        }

        if(publication.getUserId() > 9999){
            throw new PublicationBadRequestException("id de usuario no puede ser superar 4 digitos");
        }

        if (publication.getTitle().length() == 0) {
            throw new PublicationBadRequestException("title no puede estar vacio");
        }

        if (publication.getDescription().length() == 0) {
            throw new PublicationBadRequestException("description no puede estar vacio");
        }

        if (!publicationService.existsPublicationById(publication.getId())) {
            throw new PublicationNotFoundException("publicacion no encontrada");
        }

        Publication updatedPublication = publicationService.updatePublication(id,publication);
        return EntityModel.of(updatedPublication,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublishById(updatedPublication.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPublish()).withRel("all-publish")
        );

    }
    
    @PostMapping
    public EntityModel<Publication> addPublish(@RequestBody Publication publication) {

        publication.setId(null);

        if(publication.getUserId() < 0L){
            throw new PublicationBadRequestException("id de usuario no puede ser un valor negativo");
        }

        if (publication.getTitle().length() == 0) {
            throw new PublicationBadRequestException("title no puede estar vacio");
        }

        if (publication.getDescription().length() == 0) {
            throw new PublicationBadRequestException("description no puede estar vacio");
        }

        if(publication.getUserId() > 9999){
            throw new PublicationNotFoundException("id de usuario no puede ser superar 4 digitos");
        }

        Publication createdPublication = publicationService.createPublication(publication);
        return EntityModel.of(createdPublication,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublishById(createdPublication.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPublish()).withRel("all-publish")
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublish(@PathVariable String id){

        ParsedLong parsedId = serviceUtils.validateLong(id, "id");

        if(!parsedId.isSuccess()){
            throw new PublicationBadRequestException(parsedId.getErrorMessage());
        }
        
        if (!publicationService.existsPublicationById(parsedId.getResultLong())) {
            throw new PublicationNotFoundException("publicacion no encontrada");
        }

        for (Review review : reviewService.getAllReviews()) {
            if (review.getIdPublication() == parsedId.getResultLong()) {
                reviewService.deleteReview(review.getId());
            }
        }

        publicationService.deletePublication(parsedId.getResultLong());

        return ResponseEntity.ok().body("Publicacion " + parsedId + " borrada.");
    }

    @GetMapping("/{id}/reviewResult")
    public EntityModel<ReviewDetail> getReviewAverage(@PathVariable String id) {
        
        ParsedLong parsedId = serviceUtils.validateLong(id, "id");
        int countReviews = 0;
        double starsSum = 0;

        if(!parsedId.isSuccess()){
            throw new PublicationBadRequestException(parsedId.getErrorMessage());
        }

        if (!publicationService.existsPublicationById(parsedId.getResultLong())) {
            throw new PublicationNotFoundException("publicacion no encontrada");
        }

        Optional<Publication> publication = publicationService.getPublicationById(parsedId.getResultLong());

        if (publication.isEmpty()) {
            throw new PublicationNotFoundException("publicacion no encontrada");
        }

        for (Review review : reviewService.getAllReviews()) {
            if (review.getIdPublication() == parsedId.getResultLong()) {
                countReviews++;
                starsSum += review.getStars();
            }
        }

        double average = starsSum/countReviews;
                
        DecimalFormat format = new DecimalFormat("#.##");

        ReviewDetail reviewResult = new ReviewDetail(Long.valueOf(parsedId.getResultLong()).intValue(), publication.get().getTitle(), Double.parseDouble(format.format(average)));

        return EntityModel.of(reviewResult,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublishById(parsedId.getResultLong())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPublish()).withRel("all-publish")
        );

    }

}
