package application.controller;

import application.domain.Game;
import application.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class GamesController {
    @Autowired
    private GameService gameService;

    @GetMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<List<Game>> showAllGames() {
        List<Game> games = gameService.findAll();
        if (games != null) {
            return ResponseEntity.ok().body(games);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{game_id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<Game> showGameById(@PathVariable(value = "game_id") int gameId) {
        Game game = gameService.findById(gameId);
        if (game != null) {
            return ResponseEntity.ok().body(game);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NOT_FOUND);
    }
}
