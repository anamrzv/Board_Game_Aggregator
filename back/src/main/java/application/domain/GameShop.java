package application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    GameShopKey id;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    Game game;

    @ManyToOne
    @MapsId("shopId")
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    Shop shop;

    Float price;

    @Override
    public int hashCode() {
        return 319;
    }
}
