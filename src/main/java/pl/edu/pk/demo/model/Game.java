package pl.edu.pk.demo.model; // Upewnij się, że masz poprawny pakiet

import jakarta.persistence.*;
import pl.edu.pk.demo.model.User;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private int rating;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Game() {}

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
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}