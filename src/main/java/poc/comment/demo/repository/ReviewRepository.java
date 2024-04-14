package poc.comment.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.comment.demo.model.Review;

public interface ReviewRepository extends JpaRepository<Review,Long>{

}
