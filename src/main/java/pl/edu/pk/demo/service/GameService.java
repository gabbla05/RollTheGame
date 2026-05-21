package pl.edu.pk.demo.service;

import org.springframework.stereotype.Service;
import pl.edu.pk.demo.Game;
import pl.edu.pk.demo.GameRepository;
import pl.edu.pk.demo.dto.GameRequest;
import pl.edu.pk.demo.dto.GameResponse;
import pl.edu.pk.demo.exception.ResourceNotFoundException;
import pl.edu.pk.demo.mapper.GameMapper;

import java.util.List;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public List<GameResponse> getAllGames() {
        return repository.findAll().stream()
                .map(GameMapper::toResponse)
                .toList();
    }

    public GameResponse getGameById(Long id) {
        return repository.findById(id)
                .map(GameMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Gra o id " + id + " nie istnieje"));
    }

    public GameResponse createGame(GameRequest request) {
        // Punkt 5: Walidacja biznesowa w serwisie
        if (request.getTitle().equalsIgnoreCase("Error")) {
            throw new RuntimeException("Tytuł 'Error' jest zakazany!");
        }

        Game game = GameMapper.toEntity(request);
        return GameMapper.toResponse(repository.save(game));
    }

    public void deleteGame(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nie można usunąć - gra o id " + id + " nie istnieje");
        }
        repository.deleteById(id);
    }
}