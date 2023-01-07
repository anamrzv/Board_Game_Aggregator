package application.controller;

import application.domain.*;
import application.domain.composite_keys.UserCartKey;
import application.pojo.request.CartRequest;
import application.pojo.request.FavRequest;
import application.pojo.request.ForumRequest;
import application.service.ForumService;
import application.service.GameService;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/game_aggregator/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ForumService forumService;
    @Autowired
    private GameService gameService;

    /**
     * @param login
     * @return все игры, добавленные в корзину пользователем
     */
    @GetMapping(value = "/cart",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<Set<UserCart>> showCart(@RequestBody String login) {
        Set<UserCart> games;
        User user = userService.findByLogin(login);
        if (user != null) {
            games = user.getGamesInCart();
            return ResponseEntity.ok().body(games);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/cart",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_USER"})
    private ResponseEntity<HttpStatus> addGameToCart(@RequestBody CartRequest cartRequest) {
        User user = userService.findByLogin(cartRequest.getLogin());
        Game game = gameService.findById(cartRequest.getGameId());
        if (game != null && user != null) {
            UserCartKey key = new UserCartKey(cartRequest.getGameId(), cartRequest.getLogin());
            UserCart cart = userService.getCartByKey(key);
            user.removeGameFromCart(cart);
            userService.updateUser(user);
            gameService.addGame(game);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * @param login
     * @return все игры, добавленные в избранное пользователем
     */
    @GetMapping(value = "/fav",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<Set<UserFav>> showFavourites(@RequestBody String login) {
        Set<UserFav> games;
        User user = userService.findByLogin(login);
        if (user != null) {
            games = user.getGamesInFavourites();
            return ResponseEntity.ok().body(games);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/fav",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured({"ROLE_USER"})
    private ResponseEntity<HttpStatus> deleteGameFromFav(@RequestBody FavRequest favRequest) {
        Game game = gameService.findById(favRequest.getGameId());
        User user = userService.findByLogin(favRequest.getLogin());
        if (game != null && user != null) {
            UserCartKey key = new UserCartKey(favRequest.getGameId(), favRequest.getLogin());
            UserFav fav = userService.getFavByKey(key);
            user.removeGameFromFav(fav);
            userService.updateUser(user);
            gameService.addGame(game);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * @param login
     * @return все игры, добавленные в избранное пользователем
     */
    @GetMapping(value = "/fav_forums",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<Set<Forum>> showFavouriteForums(@RequestBody String login) {
        Set<Forum> forums;
        User user = userService.findByLogin(login);
        if (user != null) {
            forums = user.getFavouriteForums();
            return ResponseEntity.ok().body(forums);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/fav_forums",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> deleteForumFromFav(@RequestBody ForumRequest forumRequest) {
        Forum forum = forumService.findById(forumRequest.getForum());
        User user = userService.findByLogin(forumRequest.getLogin());
        if (user != null && forum != null) {
            user.removeForumFromFav(forum);
            userService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

