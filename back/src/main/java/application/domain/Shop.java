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
@Table(name = "shop")
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

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<GameShop> gamesInStock = new HashSet<>();

    @Override
    public int hashCode() {
        return 199;
    }

}

