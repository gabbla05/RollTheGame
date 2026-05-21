package pl.edu.pk.demo.dto;

public class GameResponse {
    private Long id;
    private String title;
    private String genre;
    private int rating;
    private String imageUrl;

    // Gettery i Settery
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}