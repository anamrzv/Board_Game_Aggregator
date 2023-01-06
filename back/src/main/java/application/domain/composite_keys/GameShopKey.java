package application.domain.composite_keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

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
}
