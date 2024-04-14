package poc.comment.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "publicacion")
public class Publication {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column(name="id_publicacion")
    Long id;

    @Column(name="usedId")
    int usedId;

    @Column(name="title")
    String title;

    @Column(name="descripcion")
    String description;

    List<Review> reviewList = new ArrayList<>();

    public Publication(Long id, int usedId, String title, String description) {
        this.id = id;
        this.usedId = usedId;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUsedId() {
        return usedId;
    }

    public void setUsedId(int usedId) {
        this.usedId = usedId;
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

    public List<Review> getReviewList() {
        return reviewList;
    }

    public Review addReviewList(Review review) {
        this.reviewList.add(review);
        return review;
    }
    
}
