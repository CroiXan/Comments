package poc.comment.demo.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "publicacion")
public class Publication extends RepresentationModel<Publication> {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column(name="id_publicacion")
    Long id;

    @Column(name="user_id")
    int userId;

    @Column(name="title")
    String title;

    @Column(name="descripcion")
    String description;

    public Publication() {
    }

    public Publication(Long id, int userId, String title, String description) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
    }

    public Publication(int userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
