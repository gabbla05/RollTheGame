package pl.edu.pk.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pk.demo.Game;
import pl.edu.pk.demo.GameRepository;
import pl.edu.pk.demo.dto.GameRequest;
import pl.edu.pk.demo.dto.GameResponse;
import pl.edu.pk.demo.exception.ResourceNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository repository;

    @InjectMocks
    private GameService gameService;

    @Test
    void createGame_HappyPath_ShouldReturnGame() {
        // Given
        GameRequest request = new GameRequest();
        request.setTitle("Wiedźmin 3");
        request.setGenre("RPG");
        request.setRating(95);

        Game savedGame = new Game();
        savedGame.setId(1L);
        savedGame.setTitle("Wiedźmin 3");
        savedGame.setGenre("RPG");
        savedGame.setRating(95);

        when(repository.save(any(Game.class))).thenReturn(savedGame);

        // When
        GameResponse response = gameService.createGame(request);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Wiedźmin 3", response.getTitle());
        verify(repository, times(1)).save(any(Game.class));
    }

    @Test
    void createGame_ShouldThrowException_WhenTitleIsError() {
        // Given
        GameRequest request = new GameRequest();
        request.setTitle("Error");
        request.setGenre("Test");
        request.setRating(50);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                gameService.createGame(request)
        );
        assertEquals("Tytuł 'Error' jest zakazany!", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void getGameById_ShouldReturnGame_WhenExists() {
        // Given
        Long gameId = 1L;
        Game game = new Game();
        game.setId(gameId);
        game.setTitle("Cyberpunk 2077");

        when(repository.findById(gameId)).thenReturn(Optional.of(game));

        // When
        GameResponse response = gameService.getGameById(gameId);

        // Then
        assertNotNull(response);
        assertEquals("Cyberpunk 2077", response.getTitle());
    }

    @Test
    void getGameById_ShouldThrowException_WhenNotFound() {
        // Given
        Long gameId = 999L;
        when(repository.findById(gameId)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                gameService.getGameById(gameId)
        );
        assertEquals("Gra o id 999 nie istnieje", exception.getMessage());
    }

    @Test
    void deleteGame_ShouldThrowException_WhenGameDoesNotExist() {
        // Given
        Long gameId = 1L;
        // Zmieniono .setReturnValue(false) na .thenReturn(false)
        when(repository.existsById(gameId)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> gameService.deleteGame(gameId));
    }
}