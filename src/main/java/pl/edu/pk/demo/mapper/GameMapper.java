package pl.edu.pk.demo.mapper;

import pl.edu.pk.demo.Game;
import pl.edu.pk.demo.dto.GameRequest;
import pl.edu.pk.demo.dto.GameResponse;

public class GameMapper {
    public static Game toEntity(GameRequest request) {
        Game game = new Game();
        game.setTitle(request.getTitle());
        game.setGenre(request.getGenre());
        game.setRating(request.getRating());
        return game;
    }

    public static GameResponse toResponse(Game game) {
        GameResponse res = new GameResponse();
        res.setId(game.getId());
        res.setTitle(game.getTitle());
        res.setGenre(game.getGenre());
        res.setRating(game.getRating());
        return res;
    }
}