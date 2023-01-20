package application.controller;

import application.domain.Game;
import application.domain.GameShop;
import application.domain.Shop;
import application.pojo.request.ShopGameRequest;
import application.pojo.request.NameRequest;
import application.pojo.response.ShopWithStockResponse;
import application.service.GameService;
import application.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
     * @param nameRequest имя сети магазинов
     * @return все игры в наличии во всех магазинах этой сети, магазины сети
     */
    @PostMapping(value = "/stock",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<List<ShopWithStockResponse>> showGamesInStock(@RequestBody NameRequest nameRequest) {
        List<ShopWithStockResponse> response = new ArrayList<>();
        List<Shop> shops = shopService.findAllByName(nameRequest.getName());
        for (Shop shop : shops) {
            ShopWithStockResponse shopWithStockResponse = new ShopWithStockResponse(shop, shop.getGamesInStock());
            response.add(shopWithStockResponse);
        }

        return ResponseEntity.ok().body(response);
    }

    /**
     * @param request shop id, game id, цена
     *                добавить в наличие в определенный магазин игру, которая есть на сайте
     */
    @PatchMapping(value = "/stock",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> addGameToStock(@RequestBody ShopGameRequest request) {
        Game game = gameService.findById(request.getGameId());
        Shop shop = shopService.findShopById(request.getShopId());
        if (game != null && shop != null) {
            GameShop gameShop = new GameShop();
            gameShop.setGame(game);
            gameShop.setShop(shop);
            gameShop.setPrice(request.getPrice());
            shop.addGameToStock(gameShop);
            shopService.saveShop(shop);
            shopService.addGameToShop(gameShop);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/stock",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> deleteGameFromStock(@RequestBody ShopGameRequest request) {
        Game game = gameService.findById(request.getGameId());
        Shop shop = shopService.findShopById(request.getShopId());
        if (game != null && shop != null) {
            GameShop gameShop = new GameShop();
            gameShop.setGame(game);
            gameShop.setShop(shop);

            shop.removeGameFromStock(gameShop);
            shopService.saveShop(shop);

            shopService.removeGameToShop(gameShop);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/stock",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> updateGamePrice(@RequestBody ShopGameRequest request) {
        Game game = gameService.findById(request.getGameId());
        Shop shop = shopService.findShopById(request.getShopId());
        if (game != null && shop != null) {
            GameShop gameShop = new GameShop();
            gameShop.setGame(game);
            gameShop.setShop(shop);
            gameShop.setPrice(request.getPrice());
            shop.addGameToStock(gameShop);
            shopService.saveShop(shop);
            shopService.addGameToShop(gameShop);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
