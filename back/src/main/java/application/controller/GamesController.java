package application.controller;

import application.domain.Game;
import application.domain.GameComment;
import application.domain.GameShop;
import application.parsing.CustomRsqlVisitor;
import application.pojo.GameResponse;
import application.pojo.GameWithStockResponse;
import application.service.GameService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/game_aggregator/game")
@CrossOrigin(origins = "*")
public class GamesController {
    @Autowired
    private GameService gameService;

    /**
     * @param search поисковая строка, которая парсится на ноды и ищет игры в соотвествтии с фильтром
     *               если фильтра нет, возвращает все игры
     * @return список игр
     */
    @GetMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<List<Game>> showAllGames(@RequestParam(value = "search", required = false) String search) {
        List<Game> games;
        if (search == null) {
            games = gameService.findAll();
        } else {
            Node rootNode = new RSQLParser().parse(search);
            Specification<Game> spec = rootNode.accept(new CustomRsqlVisitor<Game>());
            games = gameService.findAll(spec);
        }
        return ResponseEntity.ok().body(games);
    }

    /**
     * @param gameId
     * @return информацию о конкретной игре, комментарии к ней
     */
    @GetMapping(value = "/{game_id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<GameResponse> showGameById(@PathVariable(value = "game_id") Integer gameId) {
        Game game = gameService.findById(gameId);
        if (game != null) {
            List<GameComment> comments = gameService.findAllGameComments(gameId);
            Set<GameShop> shopsHavingInStock = game.getShopsHavingInStock();
            List<GameWithStockResponse> prices = new ArrayList<>();
            for (GameShop gameShop: shopsHavingInStock) {
                prices.add(new GameWithStockResponse(gameShop.getShop(), gameShop.getPrice()));
            }
            GameResponse response = new GameResponse(game, comments, prices);
            return ResponseEntity.ok().body(response);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NOT_FOUND);
    }
}
