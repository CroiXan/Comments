package poc.comment.demo.service;

import java.util.List;
import java.util.Optional;

import poc.comment.demo.model.Publication;

public interface PublicationService {
    List<Publication> getAllPublications();
    Optional<Publication> getPublicationById(Long id);
    Publication createPublication(Publication publication);
    Publication updatePublication(Long id, Publication publication);
    void deletePublication(Long id);
    boolean existsPublicationById(Long id);
}
