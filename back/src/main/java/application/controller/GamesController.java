package application.controller;

import application.domain.Game;
import application.domain.GameComment;
import application.parsing.CustomRsqlVisitor;
import application.pojo.GameResponse;
import application.service.GameService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    private ResponseEntity<List<Game>> showAllGames(@RequestParam(value = "search") String search) {
        List<Game> games;
        if (search.isEmpty()) {
            games = gameService.findAll();
        } else {
            Node rootNode = new RSQLParser().parse(search);
            Specification<Game> spec = rootNode.accept(new CustomRsqlVisitor<Game>());
            games = gameService.findAll(spec);
        }
        if (games != null) {
            return ResponseEntity.ok().body(games);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{game_id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<GameResponse> showGameById(@PathVariable(value = "game_id") int gameId) {
        Game game = gameService.findById(gameId);
        if (game != null) {
            List<GameComment> comments = gameService.findAllGameComments(gameId);
            GameResponse response = new GameResponse(game, comments);
            return ResponseEntity.ok().body(response);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NOT_FOUND);
    }
}
