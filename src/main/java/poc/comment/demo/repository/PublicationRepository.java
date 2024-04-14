package poc.comment.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.comment.demo.model.Publication;

public interface PublicationRepository extends JpaRepository<Publication,Long>{

}
