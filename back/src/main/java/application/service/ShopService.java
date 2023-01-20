package application.service;

import application.domain.Game;
import application.domain.GameShop;
import application.domain.Shop;
import application.repository.GameShopRepository;
import application.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private GameShopRepository gameShopRepository;
    @Transactional
    public void saveShop(Shop shop) { shopRepository.save(shop); }

    @Transactional
    public List<Shop> findAllByName(String name) {
        return shopRepository.getAllByName(name);
    }

    @Transactional
    public Shop findShopById(int id) {
        return shopRepository.getById(id);
    }

    @Transactional
    public void addGameToShop(GameShop gameShop) {
        gameShopRepository.save(gameShop);
    }

    @Transactional
    public void removeGameToShop(GameShop gameShop) {
        gameShopRepository.delete(gameShop);
    }

    @Transactional
    public GameShop getGameFromShop(Game game, Shop shop) {
        return gameShopRepository.getGameShopsByGameAndShop(game, shop);
    }


}
