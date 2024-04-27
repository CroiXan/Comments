package poc.comment.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import poc.comment.demo.model.Publication;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PublicationRepositoryTest {

    @Autowired
    private PublicationRepository publicationRepository;

    @Test
    public void updatePublicationTest(){

        Publication publication = new Publication();
        publication.setUserId(200);
        publication.setTitle("Terminal Pesquero");
        publication.setDescription("Pescados y mariscos");

        Publication savedPublication = publicationRepository.save(publication);

        publication.setId(savedPublication.getId());
        publication.setDescription("Pescados, mariscos y comida criolla");

        Publication updatedPublication = publicationRepository.save(publication);

        assertNotNull(updatedPublication.getId());

        assertEquals(savedPublication.getId(), updatedPublication.getId());
        assertEquals(savedPublication.getUserId(), updatedPublication.getUserId());
        assertEquals(savedPublication.getTitle(), updatedPublication.getTitle());
        assertEquals("Pescados, mariscos y comida criolla", updatedPublication.getDescription());
    }

}
