package pl.edu.pk.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GameRequest {
    @NotBlank(message = "Tytuł gry nie może być pusty") // Walidacja pola 1
    @Size(min = 2, message = "Tytuł musi mieć co najmniej 2 znaki")
    private String title;

    @NotBlank(message = "Gatunek nie może być pusty")
    private String genre;

    @Min(value = 0, message = "Ocena nie może być mniejsza niż 0") // Walidacja pola 2
    @Max(value = 100, message = "Ocena nie może być większa niż 100")
    private int rating;

    // Gettery i Settery
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}