package application.controller;

import application.domain.*;
import application.pojo.request.CartRequest;
import application.pojo.request.FavRequest;
import application.pojo.request.ForumRequest;
import application.pojo.request.UserRequest;
import application.pojo.response.ShopGameResponse;
import application.service.ForumService;
import application.service.GameService;
import application.service.ShopService;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @Autowired
    private ShopService shopService;

    /**
     * @param request
     * @return все игры, добавленные в корзину пользователем
     */
    @PostMapping(value = "/cart",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<List<ShopGameResponse>> showCart(@RequestBody UserRequest request) {
        Set<UserCart> games;
        User user = userService.findByLogin(request.getLogin());
        if (user != null) {
            games = user.getGamesInCart();

            List<ShopGameResponse> gamesWithPrice = new ArrayList<>();
            for (UserCart cart : games) {
                Game game = gameService.findById(cart.getGame().getId());
                Shop shop = shopService.findShopById(cart.getShop());
                GameShop gameShop = shopService.getGameFromShop(game, shop);
                gamesWithPrice.add(new ShopGameResponse(shop, cart.getGame(), gameShop.getPrice()));
            }
            return ResponseEntity.ok().body(gamesWithPrice);
        } else return new ResponseEntity("Пользователь с переданным логином не найден", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/cart",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<List<ShopGameResponse>> deleteGameFromCart(@RequestBody CartRequest cartRequest) {
        User user = userService.findByLogin(cartRequest.getLogin());
        Game game = gameService.findById(cartRequest.getGameId());
        if (game != null && user != null) {
            UserCart userCart = userService.getUserCartByUserAndGame(user, game);

            user.removeGameFromCart(userCart);
            userService.updateUser(user);

            userService.removeUserCartByUserAndGame(user, game);

            Set<UserCart> games = user.getGamesInCart();

            List<ShopGameResponse> gamesWithPrice = new ArrayList<>();
            for (UserCart cart : games) {
                Game user_game = gameService.findById(cart.getGame().getId());
                Shop shop = shopService.findShopById(cart.getShop());
                GameShop gameShop = shopService.getGameFromShop(user_game, shop);
                gamesWithPrice.add(new ShopGameResponse(shop, cart.getGame(), gameShop.getPrice()));
            }
            return ResponseEntity.ok().body(gamesWithPrice);
        } else return new ResponseEntity("Пользователь/игра с переданными ключами не найден/а",HttpStatus.BAD_REQUEST);
    }

    /**
     * @param request
     * @return все игры, добавленные в избранное пользователем
     */
    @PostMapping(value = "/fav",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<Set<Game>> showFavourites(@RequestBody UserRequest request) {
        Set<UserFav> games;
        Set<Game> return_games = new HashSet<>();
        User user = userService.findByLogin(request.getLogin());
        if (user != null) {
            games = user.getGamesInFavourites();
            for (UserFav fav : games) {
                Game user_game = gameService.findById(fav.getGame().getId());
                return_games.add(user_game);
            }
            return ResponseEntity.ok().body(return_games);
        } else return new ResponseEntity("Пользователь с переданным логином не найден", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/fav",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> deleteGameFromFav(@RequestBody FavRequest favRequest) {
        Game game = gameService.findById(favRequest.getGameId());
        User user = userService.findByLogin(favRequest.getLogin());
        if (game != null && user != null) {
            UserFav fav = userService.getUserFavByUserAndGame(user, game);

            user.removeGameFromFav(fav);
            userService.updateUser(user);

            userService.removeUserFav(fav);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity("Пользователь/игра с переданными ключами не найден/а",HttpStatus.BAD_REQUEST);
    }

    /**
     * @param request
     * @return все игры, добавленные в избранное пользователем
     */
    @PostMapping(value = "/fav_forums",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<Set<Forum>> showFavouriteForums(@RequestBody UserRequest request) {
        Set<Forum> forums;
        User user = userService.findByLogin(request.getLogin());
        if (user != null) {
            forums = user.getFavouriteForums();
            return ResponseEntity.ok().body(forums);
        } else return new ResponseEntity("Пользователь с переданным логином не найден", HttpStatus.NO_CONTENT);
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
        } else return new ResponseEntity("Пользователь/форум с переданными ключами не найден",HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/buy",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> buyGame(@RequestBody UserRequest request) {
        Set<UserCart> games;
        User user = userService.findByLogin(request.getLogin());
        if (user != null) {
            games = user.getGamesInCart();

            for (UserCart cart : games) {
                if (cart.getDateOfBuy() == null) {
                    user.removeGameFromCart(cart);
                    cart.setDateOfBuy(java.time.LocalDateTime.now());
                    user.addGameToCart(cart);
                    userService.updateUser(user);
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity("Пользователь с переданным логином не найден", HttpStatus.NO_CONTENT);
    }

}

