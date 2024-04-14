package poc.comment.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "resenia")
public class Review {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column(name="id_resenia")
    Long id;

    @Column(name="id_publicacion")
    Long idPublication;

    @Column(name="user_id")
    int userId;

    @Column(name="title")
    String title;

    @Column(name="descripcion")
    String description;

    @Column(name="stars")
    int stars;
    

    public Review() {
    }

    public Review(Long id, Long idPublication, int userId, String title, String description, int stars) {
        this.id = id;
        this.idPublication = idPublication;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.stars = stars;
    }

    public Review(int userId, String title, String description, int stars) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.stars = stars;
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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Long getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(Long idPublication) {
        this.idPublication = idPublication;
    }
    
}
