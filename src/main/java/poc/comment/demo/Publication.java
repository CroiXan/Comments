package poc.comment.demo;

import java.time.LocalDateTime;
import java.util.List;

public class Publication {
    int id;
    int usedId;
    String title;
    String description;
    LocalDateTime publishDate;
    List<Review> reviewList;
    
    public Publication(int usedId, String title, String description, LocalDateTime publishDate) {
        this.usedId = usedId;
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void addReviewList(Review review) {
        this.reviewList.add(review);
    }
    
}
