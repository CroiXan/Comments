package poc.comment.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import poc.comment.demo.model.Publication;
import poc.comment.demo.service.PublicationServiceImpl;
import poc.comment.demo.service.ReviewServiceImpl;

@WebMvcTest(PublicationController.class)
public class PublicationControllerTest {

    @Autowired 
    private MockMvc mockMvc;
    
    @MockBean 
    private ReviewServiceImpl reviewServiceImplMock;
    
    @MockBean
    private PublicationServiceImpl publicationServiceImplMock;

    @Test
    public void updatePublish() throws Exception{

        Publication publication = new Publication();
        publication.setId(1L);
        publication.setUserId(100);
        publication.setTitle("Nuevo Restaurante");
        publication.setDescription("Comida criolla");

        when(publicationServiceImplMock.existsPublicationById(any())).thenReturn(true);
        when(publicationServiceImplMock.updatePublication(any(), any())).thenReturn(publication);

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.put("/publish/1")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(publication)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title",Matchers.is("Nuevo Restaurante")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description",Matchers.is("Comida criolla")));
    }

}
