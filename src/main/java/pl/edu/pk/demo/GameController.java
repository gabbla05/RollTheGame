package pl.edu.pk.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.demo.dto.GameRequest;
import pl.edu.pk.demo.dto.GameResponse;
import pl.edu.pk.demo.exception.ResourceNotFoundException;
import pl.edu.pk.demo.model.Game;
import pl.edu.pk.demo.model.User;
import pl.edu.pk.demo.repository.UserRepository;
import pl.edu.pk.demo.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;
    private final UserRepository userRepository; // Musi być wstrzyknięte

    public GameController(GameService gameService, UserRepository userRepository) {
        this.gameService = gameService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Game> getGames() {
        return gameService.getGamesForCurrentUser();
    }

    @PostMapping
    public Game addGame(@RequestBody GameRequest gameRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Użytkownik nie znaleziony"));

        Game game = new Game();
        game.setTitle(gameRequest.getTitle());
        game.setGenre(gameRequest.getGenre());
        game.setRating(gameRequest.getRating());
        game.setUser(user);
        return gameService.saveGame(game); // Użyj serwisu do zapisu!
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}