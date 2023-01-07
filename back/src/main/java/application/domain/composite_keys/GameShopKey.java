package application.domain.composite_keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameShopKey implements Serializable {
    @Column(name = "game_id")
    Integer gameId;
    @Column(name = "shop_id")
    Integer shopId;

    @Override
    public int hashCode() {
        return 157;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameShopKey that = (GameShopKey) o;
        return gameId.equals(that.gameId) && shopId.equals(that.shopId);
    }
}
