package application.controller;

import application.domain.Game;
import application.domain.Shop;
import application.pojo.ShopResponse;
import application.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
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
    private ResponseEntity<ShopResponse> showGamesInStock(@RequestBody String shopName) {
        List<Shop> shops = shopService.findAllByName(shopName);
        List<Game> inStock = shops.stream()
                .flatMap(shop -> shop.getGamesInStock().stream())
                .distinct()
                .collect(Collectors.toList());
        ShopResponse shopResponse = new ShopResponse(inStock, shops);
        return ResponseEntity.ok().body(shopResponse);
    }
}
