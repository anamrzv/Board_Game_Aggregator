package application.controller;

import application.domain.Game;
import application.domain.GameComment;
import application.domain.GameShop;
import application.domain.GameTheme;
import application.parsing.CustomRsqlVisitor;
import application.pojo.CommentRequest;
import application.pojo.GameRequest;
import application.pojo.GameResponse;
import application.pojo.GameWithStockResponse;
import application.service.GameService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.RSQLParserException;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/game_aggregator")
@CrossOrigin(origins = "*")
public class GamesController {
    @Autowired
    private GameService gameService;

    /**
     * @param search поисковая строка, которая парсится на ноды и ищет игры в соотвествтии с фильтром
     *               если фильтра нет, возвращает все игры
     * @return список игр
     * Пример запросов:
     * /game_aggregator/game?search=minPlayersNumber==2;minPlayTime>50
     */
    @GetMapping(value = "/game",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<List<Game>> showAllGames(@RequestParam(value = "search", required = false) String search) {
        List<Game> games;
        if (search == null) {
            games = gameService.findAll();
        } else {
            try {
                Node rootNode = new RSQLParser().parse(search);
                Specification<Game> spec = rootNode.accept(new CustomRsqlVisitor<Game>());
                games = gameService.findAll(spec);
            } catch (RSQLParserException e) {
                return ResponseEntity.badRequest().body(null);
            }
        }
        return ResponseEntity.ok().body(games);
    }

    /**
     * @param gameId
     * @return информацию о конкретной игре, комментарии к ней
     */
    @GetMapping(value = "/game/{game_id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<GameResponse> showGameById(@PathVariable(value = "game_id") Integer gameId) {
        Game game = gameService.findById(gameId);
        if (game != null) {
            List<GameComment> comments = gameService.findAllGameComments(gameId);
            Set<GameShop> shopsHavingInStock = game.getShopsHavingInStock();
            List<GameWithStockResponse> prices = new ArrayList<>();
            for (GameShop gameShop : shopsHavingInStock) {
                prices.add(new GameWithStockResponse(gameShop.getShop(), gameShop.getPrice()));
            }
            GameResponse response = new GameResponse(game, comments, prices);
            return ResponseEntity.ok().body(response);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/game/{game_id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_USER"})
    private ResponseEntity<HttpStatus> addComment(@PathVariable(value = "game_id") Integer gameId, @RequestBody CommentRequest comment) {
        if (gameId != null) {
            GameComment gameComment = new GameComment(gameId, comment.getLogin(), comment.getDateTime(), comment.getContent());
            gameService.addComment(gameComment);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/game/{game_id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_ADMIN"})
    private ResponseEntity<HttpStatus> deleteComment(@PathVariable(value = "game_id") Integer gameId, @RequestBody Integer commentId) {
        if (gameId != null) {
            gameService.deleteComment(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/new_game",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_ADMIN"})
    private ResponseEntity<HttpStatus> addGame(@RequestBody GameRequest game) {
        Game new_game = new Game();
        new_game.setName(game.getName());
        new_game.setImage(game.getImage());
        new_game.setVideo(game.getVideo());
        new_game.setDescription(game.getDescription());
        new_game.setMinPlayersNumber(game.getMinPlayersNumber());
        new_game.setMaxPlayersNumber(game.getMaxPlayersNumber());
        new_game.setRecPlayersNumber(game.getRecPlayersNumber());
        new_game.setMinPlayTime(game.getMinPlayTime());
        new_game.setMaxPlayTime(game.getMaxPlayTime());
        new_game.setPublishYear(game.getPublishYear());
        new_game.setDesigners(game.getDesigners());
        new_game.setMinPlayAge(game.getMinPlayAge());
        new_game.setMaxPlayAge(game.getMaxPlayAge());
        new_game.setDifficultness(game.getDifficultness());
        new_game.setWeight(game.getWeight());

        new_game.setGenre(gameService.getGenreByName(game.getGenre()));
        new_game.setMechanics(gameService.getMechanicsByName(game.getMechanics()));
        new_game.setPublisher(gameService.getPublisherByName(game.getPublisher()));

        Set<GameTheme> themes = new HashSet<>();
        for (String theme: game.getThemes()) {
            themes.add(gameService.getThemeByName(theme));
        }
        new_game.setThemes(themes);

        new_game.setWishlistCount(0);
        new_game.setOwnCount(0);

        gameService.addGame(new_game);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
