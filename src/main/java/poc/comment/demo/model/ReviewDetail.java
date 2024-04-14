package poc.comment.demo.model;

public class ReviewDetail {
    private int publishId;
    private String publishTitle;
    private double average;

    public ReviewDetail(int publishId, String publishTitle, double average) {
        this.publishId = publishId;
        this.publishTitle = publishTitle;
        this.average = average;
    }

    public int getPublishId() {
        return publishId;
    }

    public void setPublishId(int publishId) {
        this.publishId = publishId;
    }

    public String getPublishTitle() {
        return publishTitle;
    }

    public void setPublishTitle(String publishTitle) {
        this.publishTitle = publishTitle;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
    
}
