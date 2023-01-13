package application.controller;

import application.domain.*;
import application.domain.composite_keys.UserCartKey;
import application.parsing.CustomRsqlVisitor;
import application.pojo.request.CartRequest;
import application.pojo.request.CommentRequest;
import application.pojo.request.GameRequest;
import application.pojo.response.GameResponse;
import application.pojo.response.GameWithStockResponse;
import application.service.GameService;
import application.service.ShopService;
import application.service.UserService;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/game_aggregator")
@CrossOrigin(origins = "*")
public class GamesController {
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShopService shopService;

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
        int gameCount = gameService.getGameCount();
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
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_USER"})
    private ResponseEntity<HttpStatus> addComment(@PathVariable(value = "game_id") Integer gameId, @RequestBody CommentRequest comment) {
        if (gameId != null) {
            GameComment gameComment = new GameComment(gameId, comment.getLogin(), comment.getDateTime(), comment.getContent());
            gameService.addComment(gameComment);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/game/{game_id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_ADMIN"})
    private ResponseEntity<HttpStatus> deleteComment(@PathVariable(value = "game_id") Integer gameId, @RequestBody Integer commentId) {
        if (gameId != null) {
            gameService.deleteComment(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/game/{game_id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_ADMIN"})
    private ResponseEntity<HttpStatus> updateGame(@PathVariable(value = "game_id") Integer gameId, @RequestBody GameRequest game) {
        Game old_game = gameService.findById(gameId);
        if (old_game != null) {
            fillGame(game, old_game);
            gameService.addGame(old_game);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(value = "/game/{game_id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_ADMIN"})
    private ResponseEntity<HttpStatus> deleteGame(@PathVariable(value = "game_id") Integer gameId) {
        Game game = gameService.findById(gameId);
        if (game != null) {
            gameService.deleteGame(game);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * @param gameId
     * @param login
     * Добавить игру в избранное
     */
    @PostMapping(value = "/game/{game_id}/add_fav",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_USER"})
    private ResponseEntity<HttpStatus> addGameToFav(@PathVariable(value = "game_id") Integer gameId, @RequestBody String login) {
        Game game = gameService.findById(gameId);
        User user = userService.findByLogin(login);
        if (game != null && user != null) {
            UserCartKey key = new UserCartKey(gameId, login);
            UserFav fav = new UserFav(key, game, user);
            user.addGameToFav(fav);
            userService.updateUser(user);
            gameService.addGame(game);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * @param gameId
     * Добавить игру в корзину
     */
    @PostMapping(value = "/game/{game_id}/add_cart",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_USER"})
    private ResponseEntity<HttpStatus> addGameToCart(@PathVariable(value = "game_id") Integer gameId, @RequestBody CartRequest cartRequest) {
        Game game = gameService.findById(gameId);
        User user = userService.findByLogin(cartRequest.getLogin());
        if (game != null && user != null) {
            UserCartKey key = new UserCartKey(gameId, cartRequest.getLogin());
            UserCart cart = new UserCart(key, game, user, cartRequest.getShopId());
            user.addGameToCart(cart);
            userService.updateUser(user);
            gameService.addGame(game);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/new_game",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_ADMIN"})
    private ResponseEntity<HttpStatus> addGame(@RequestBody GameRequest game) {
        Game new_game = new Game();
        fillGame(game, new_game);

        new_game.setWishlistCount(0);
        new_game.setOwnCount(0);

        gameService.addGame(new_game);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void fillGame(@RequestBody GameRequest requestGame, Game game) {
        game.setName(requestGame.getName());
        game.setImage(requestGame.getImage());
        game.setVideo(requestGame.getVideo());
        game.setDescription(requestGame.getDescription());
        game.setMinPlayersNumber(requestGame.getMinPlayersNumber());
        game.setMaxPlayersNumber(requestGame.getMaxPlayersNumber());
        game.setRecPlayersNumber(requestGame.getRecPlayersNumber());
        game.setMinPlayTime(requestGame.getMinPlayTime());
        game.setMaxPlayTime(requestGame.getMaxPlayTime());
        game.setPublishYear(requestGame.getPublishYear());
        game.setDesigners(requestGame.getDesigners());
        game.setMinPlayAge(requestGame.getMinPlayAge());
        game.setMaxPlayAge(requestGame.getMaxPlayAge());
        game.setDifficultness(requestGame.getDifficultness());
        game.setWeight(requestGame.getWeight());

        game.setGenre(gameService.getGenreByName(requestGame.getGenre()));
        game.setMechanics(gameService.getMechanicsByName(requestGame.getMechanics()));
        game.setPublisher(gameService.getPublisherByName(requestGame.getPublisher()));

        Set<GameTheme> themes = new HashSet<>();
        for (String theme : requestGame.getThemes()) {
            themes.add(gameService.getThemeByName(theme));
        }
        game.setThemes(themes);
    }

}
