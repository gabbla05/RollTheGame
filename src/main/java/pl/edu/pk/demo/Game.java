package pl.edu.pk.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // Pole 1: ID (wymagane)
    private String title;     // Pole 2: Tytuł
    private String genre;     // Pole 3: Gatunek
    private int rating;       // Pole 4: Ocena (0-100)

    // Konstruktory, Gettery i Settery
    public Game() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}