package application.controller;

import application.domain.Shop;
import application.pojo.ShopWithStockResponse;
import application.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shop")
@CrossOrigin(origins = "*")
public class ShopController {
    @Autowired
    private ShopService shopService;

    /**
     * @param shopName имя сети магазинов
     * @return все игры в наличии во всех магазинах этой сети, магазины сети
     */
    @GetMapping(value = "/stock",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<List<ShopWithStockResponse>> showGamesInStock(@RequestBody String shopName) {
        List<ShopWithStockResponse> response = new ArrayList<>();
        List<Shop> shops = shopService.findAllByName(shopName);
        for (Shop shop: shops) {
            ShopWithStockResponse shopWithStockResponse = new ShopWithStockResponse(shop, shop.getGamesInStock());
            response.add(shopWithStockResponse);
        }

        return ResponseEntity.ok().body(response);
    }
}
