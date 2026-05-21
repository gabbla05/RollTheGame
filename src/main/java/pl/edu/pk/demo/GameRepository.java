package pl.edu.pk.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    // Dodajemy losowanie gry dla PostgreSQL
    @Query(value = "SELECT * FROM game ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Game> findRandomGame();
}