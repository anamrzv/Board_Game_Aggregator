package application.repository;

import application.domain.GameShop;
import application.domain.composite_keys.GameShopKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameShopRepository extends JpaRepository<GameShop, GameShopKey> {
}
