package application.service;

import application.domain.GameShop;
import application.domain.Shop;
import application.domain.composite_keys.GameShopKey;
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
    public void deleteGameFromShop(GameShopKey key) {
        gameShopRepository.deleteById(key);
    }

    @Transactional
    public GameShop getGameFromShop(GameShopKey key) {
        return gameShopRepository.findById(key).orElse(null);
    }

    @Transactional
    public GameShop findGameFromShop(GameShopKey key) {
        return gameShopRepository.findById(key).orElse(null);
    }



}
