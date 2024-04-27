package poc.comment.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import poc.comment.demo.model.Review;
import poc.comment.demo.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;
    
    @Mock
    private ReviewRepository reviewRepositoryMock;

    @Test
    public void updateReviewTest(){

        Review review = new Review();
        review.setId(1L);
        review.setIdPublication(1L);
        review.setTitle("prueba");
        review.setDescription("descripcion actual");
        review.setStars(1);

        when(reviewRepositoryMock.save(any())).thenReturn(review);
        when(reviewRepositoryMock.existsById(any())).thenReturn(true);

        review.setDescription("nueva descripcion");
        review.setStars(3);

        Review updatReview = reviewService.updateReview(review.getId(), review);

        assertNotNull(updatReview);
        assertEquals("nueva descripcion", updatReview.getDescription());
        assertEquals(3, updatReview.getStars());

    }

}
