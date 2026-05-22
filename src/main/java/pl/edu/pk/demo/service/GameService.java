package pl.edu.pk.demo.service;

import org.springframework.stereotype.Service;
import pl.edu.pk.demo.model.Game;
import pl.edu.pk.demo.repository.GameRepository;
import pl.edu.pk.demo.dto.GameRequest;
import pl.edu.pk.demo.dto.GameResponse;
import pl.edu.pk.demo.exception.ResourceNotFoundException;
import pl.edu.pk.demo.mapper.GameMapper;
import java.util.Collections;

import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import pl.edu.pk.demo.event.GameCreatedEvent;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class GameService {

    private final GameRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public GameService(GameRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    // --- TO JEST TA METODA, KTÓREJ POTRZEBUJESZ DO IZOLACJI DANYCH ---
    public List<Game> getGamesForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByUserUsername(username);
    }

    public GameResponse getGameById(Long id) {
        return repository.findById(id)
                .map(GameMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Gra o id " + id + " nie istnieje"));
    }

    public GameResponse getRandomGame() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 1. Pobieramy listę wszystkich gier tego użytkownika
        List<Game> userGames = repository.findByUserUsername(username);

        // 2. Jeśli lista jest pusta, rzucamy wyjątek
        if (userGames.isEmpty()) {
            throw new ResourceNotFoundException("Brak gier w Twojej bibliotece!");
        }

        // 3. Losujemy jedną grę z listy
        Collections.shuffle(userGames);
        Game randomGame = userGames.get(0);

        return GameMapper.toResponse(randomGame);
    }

    public GameResponse createGame(GameRequest request) {
        // Punkt 5: Walidacja biznesowa w serwisie
        if (request.getTitle().equalsIgnoreCase("Error")) {
            throw new RuntimeException("Tytuł 'Error' jest zakazany!");
        }

        Game game = GameMapper.toEntity(request);
        // Uwaga: Jeśli logikę setUser masz w kontrolerze, to tutaj zapisujemy tak jak było:
        Game savedGame = repository.save(game);

        eventPublisher.publishEvent(new GameCreatedEvent(savedGame.getId(), savedGame.getTitle()));

        return GameMapper.toResponse(savedGame);
    }

    // Dodatkowa metoda do zapisu pełnego obiektu Game (dla kontrolera z setUser)
    public Game saveGame(Game game) {
        return repository.save(game);
    }

    public GameResponse updateGame(Long id, GameRequest request) {
        Game game = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gra o id " + id + " nie istnieje"));

        if (request.getTitle().equalsIgnoreCase("Error")) {
            throw new RuntimeException("Tytuł 'Error' jest zakazany!");
        }

        game.setTitle(request.getTitle());
        game.setGenre(request.getGenre());
        game.setRating(request.getRating());
        return GameMapper.toResponse(repository.save(game));
    }

    public void deleteGame(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nie można usunąć - gra o id " + id + " nie istnieje");
        }
        repository.deleteById(id);
    }
}