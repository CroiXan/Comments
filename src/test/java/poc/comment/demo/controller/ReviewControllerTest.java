package poc.comment.demo.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import poc.comment.demo.model.Review;
import poc.comment.demo.service.PublicationServiceImpl;
import poc.comment.demo.service.ReviewServiceImpl;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired 
    private MockMvc mockMvc;
    
    @MockBean 
    private ReviewServiceImpl reviewServiceImplMock;
    
    @MockBean
    private PublicationServiceImpl publicationServiceImplMock;

    @Test
    public void getReviewByIdTest() throws Exception{

        Review review = new Review();
        review.setId(1L);
        review.setIdPublication(1L);
        review.setUserId(50);
        review.setTitle("Buena atencion");
        review.setDescription("buena comida, buena atencion");
        review.setStars(5);

        Review review2 = new Review();
        review2.setId(2L);
        review2.setIdPublication(1L);
        review2.setUserId(33);
        review2.setTitle("buenos precios");
        review2.setDescription("la presentacion de la comida no es muy buena");
        review2.setStars(3);

        List<Review> reviews = new ArrayList<Review>();

        reviews.add(review);
        reviews.add(review2);

        when(reviewServiceImplMock.getReviewById(2L)).thenReturn(getReviewById(2L, reviews));

        mockMvc.perform(MockMvcRequestBuilders.get("/review/2"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title",Matchers.is("buenos precios")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.stars",Matchers.is(3)));

    }

    private Optional<Review> getReviewById(Long id, List<Review> reviewList){

        for (Review review : reviewList) {
            if (review.getId() == id) {
                return Optional.of(review);
            }
        }

        return null;
    }

}
