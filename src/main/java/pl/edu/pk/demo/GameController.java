package pl.edu.pk.demo;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.demo.dto.GameRequest;
import pl.edu.pk.demo.dto.GameResponse;
import pl.edu.pk.demo.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService; // Zmiana z repository na service

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameResponse> getAll() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public GameResponse getOne(@PathVariable("id") Long id) { // Zmiana tutaj
        return gameService.getGameById(id);
    }

    @GetMapping("/find/random")
    public GameResponse getRandom() {
        return gameService.getRandomGame();
    }

    @PostMapping
    public GameResponse create(@Valid @RequestBody GameRequest request) {
        return gameService.createGame(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) { // Zmiana tutaj
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public GameResponse update(@PathVariable("id") Long id, @Valid @RequestBody GameRequest request) { // Zmiana tutaj
        return gameService.updateGame(id, request);
    }
}