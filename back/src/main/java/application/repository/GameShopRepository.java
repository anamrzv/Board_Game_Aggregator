package application.repository;

import application.domain.Game;
import application.domain.GameShop;
import application.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameShopRepository extends JpaRepository<GameShop, Integer> {
    GameShop getGameShopsByGameAndShop(Game game, Shop shop);
}
