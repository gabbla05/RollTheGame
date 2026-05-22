package pl.edu.pk.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pk.demo.model.Game;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByUserUsername(String username);

    @Query(value = "SELECT * FROM game ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Game> findRandomGame();
}