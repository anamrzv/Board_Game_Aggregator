package application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "shop", indexes = {@Index(name = "Vip_Shop_Index", columnList = "Shop_Type_ID"), @Index(name = "City_Index", columnList = "city")})
public class Shop {
    @Id
    @NotNull
    private Integer id;
    private String name;
    @Column(name = "shop_type_id")
    private Integer shopType;
    private String address;
    private String city;
    private String phone;

    @OneToMany(mappedBy = "shop", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<GameShop> gamesInStock = new HashSet<>();

    public void addGameToStock(GameShop gameShop){
        this.gamesInStock.add(gameShop);
        gameShop.getGame().getShopsHavingInStock().add(gameShop);
    }

    public void removeGameFromStock(GameShop gameShop){
        this.gamesInStock.remove(gameShop);
        gameShop.getGame().getShopsHavingInStock().remove(gameShop);
    }

    @Override
    public int hashCode() {
        return 199;
    }

}

