package application.controller;

import application.domain.Game;
import application.domain.GameShop;
import application.domain.Shop;
import application.domain.composite_keys.GameShopKey;
import application.pojo.request.ShopGameRequest;
import application.pojo.response.ShopWithStockResponse;
import application.service.GameService;
import application.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ComponentScan
@RestController
@RequestMapping("/game_aggregator/shop")
@CrossOrigin(origins = "*")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private GameService gameService;

    /**
     * @param shopName имя сети магазинов
     * @return все игры в наличии во всех магазинах этой сети, магазины сети
     */
    @GetMapping(value = "/stock",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured("ROLE_SHOP")
    private ResponseEntity<List<ShopWithStockResponse>> showGamesInStock(@RequestBody String shopName) {
        List<ShopWithStockResponse> response = new ArrayList<>();
        List<Shop> shops = shopService.findAllByName(shopName);
        for (Shop shop : shops) {
            ShopWithStockResponse shopWithStockResponse = new ShopWithStockResponse(shop, shop.getGamesInStock());
            response.add(shopWithStockResponse);
        }

        return ResponseEntity.ok().body(response);
    }

    /**
     * @param request shop id, game id, цена
     * добавить в наличие в определенный магазин игру, которая есть на сайте
     */
    @PostMapping(value = "/stock",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured("ROLE_SHOP")
    private ResponseEntity<HttpStatus> addGameToStock(@RequestBody ShopGameRequest request) {
        Game game = gameService.findById(request.getGameId());
        Shop shop = shopService.findShopById(request.getShopId());
        if (game != null && shop != null) {
            GameShopKey key = new GameShopKey(request.getGameId(), request.getShopId());
            GameShop gameShop = new GameShop(key, game, shop, request.getPrice());
            shopService.addGameToShop(gameShop);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/stock",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured("ROLE_SHOP")
    private ResponseEntity<HttpStatus> deleteGameFromStock(@RequestBody ShopGameRequest request) {
        GameShopKey key = new GameShopKey(request.getGameId(), request.getShopId());
        shopService.deleteGameFromShop(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/stock",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Secured("ROLE_SHOP")
    private ResponseEntity<HttpStatus> updateGamePrice(@RequestBody ShopGameRequest request) {
            GameShopKey key = new GameShopKey(request.getGameId(), request.getShopId());
            GameShop gameShop = shopService.findGameFromShop(key);
        if (gameShop != null) {
            gameShop.setPrice(request.getPrice());
            shopService.addGameToShop(gameShop);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
