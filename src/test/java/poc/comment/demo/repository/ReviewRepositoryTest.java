package poc.comment.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import poc.comment.demo.model.Review;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void getReviewById(){

        Review review = new Review();
        review.setId(1L);
        review.setIdPublication(1L);
        review.setTitle("restaurante regular");
        review.setDescription("descripcion actual");
        review.setStars(2);

        reviewRepository.save(review);

        Optional<Review> searchReview = reviewRepository.findById(1L);

        assertNotNull(searchReview.get());

        assertEquals(review.getId(), searchReview.get().getId());
        assertEquals(review.getTitle(), searchReview.get().getTitle());
        assertEquals(review.getDescription(), searchReview.get().getDescription());
        assertEquals(review.getStars(), searchReview.get().getStars());

    }

}
