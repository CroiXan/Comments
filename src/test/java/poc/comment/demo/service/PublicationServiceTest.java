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

import poc.comment.demo.model.Publication;
import poc.comment.demo.repository.PublicationRepository;

@ExtendWith(MockitoExtension.class)
public class PublicationServiceTest {

    @InjectMocks
    private PublicationServiceImpl publicationService;
    
    @Mock
    private PublicationRepository publicationRepositoryMock;

    @Test
    public void createPublicationTest(){

        Publication publication = new Publication();
        publication.setId(1L);
        publication.setUserId(100);
        publication.setTitle("Nuevo Restaurante");
        publication.setDescription("Comida criolla");

        when(publicationRepositoryMock.save(any())).thenReturn(publication);

        Publication createdPublication = publicationService.createPublication(publication);

        assertNotNull(createdPublication);
        assertEquals(1L, createdPublication.getId());
        assertEquals(100, createdPublication.getUserId());
        assertEquals("Nuevo Restaurante", createdPublication.getTitle());
        assertEquals("Comida criolla", createdPublication.getDescription());

    }

}
