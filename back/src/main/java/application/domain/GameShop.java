package application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game_to_shop")
public class GameShop {
    @EmbeddedId
    GameShopKey id;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    Game game;
    @ManyToOne
    @MapsId("shopId")
    @JoinColumn(name = "shop_id")
    Shop shop;

    Float price;
}
