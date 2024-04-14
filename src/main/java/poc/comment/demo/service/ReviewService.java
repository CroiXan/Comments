package poc.comment.demo.service;

import java.util.List;
import java.util.Optional;

import poc.comment.demo.model.Review;

public interface ReviewService {
    List<Review> getAllReviews();
    Optional<Review> getReviewById(Long id);
    Review createReview(Review review);
    Review updateReview(Long id, Review review);
    void deleteReview(Long id);
}
