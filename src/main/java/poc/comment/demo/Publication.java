package poc.comment.demo;

import java.util.ArrayList;
import java.util.List;

public class Publication {
    int id;
    int usedId;
    String title;
    String description;
    List<Review> reviewList = new ArrayList<>();
    
    public Publication(int id, int usedId, String title, String description) {
        this.id = id;
        this.usedId = usedId;
        this.title = title;
        this.description = description;
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

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void addReviewList(Review review) {
        review.setId(this.reviewList.size()+1);
        this.reviewList.add(review);
    }
    
}
