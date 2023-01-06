package application.controller;

import application.domain.*;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/game_aggregator/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

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
}

