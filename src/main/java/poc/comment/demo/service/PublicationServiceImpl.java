package poc.comment.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import poc.comment.demo.model.Publication;
import poc.comment.demo.repository.PublicationRepository;

@Service
public class PublicationServiceImpl implements PublicationService{

    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    @Override
    public Optional<Publication> getPublicationById(Long id) {
        return publicationRepository.findById(id);
    }

    @Override
    public Publication createPublication(Publication publication) {
        return publicationRepository.save(publication);
    }

    @Override
    public Publication updatePublication(Long id, Publication publication) {
        if(publicationRepository.existsById(id)){
            publication.setId(id);
            return publicationRepository.save(publication);
        }else{
            return null;
        }
    }

    @Override
    public void deletePublication(Long id) {
        publicationRepository.deleteById(id);
    }

}
