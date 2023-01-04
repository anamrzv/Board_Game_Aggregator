package application.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "shop")
public class Shop {
    @Id
    @NotNull
    private Integer id;
    private String name;
    @OneToOne
    @JoinColumn(name = "shop_type_id")
    private ShopType shopType;
    private String address;
    private String city;
    private String phone;

    @ManyToMany
    private Set<Game> gamesInStock;
}
