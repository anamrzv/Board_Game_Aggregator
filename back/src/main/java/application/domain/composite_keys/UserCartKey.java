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
public class UserCartKey implements Serializable {

    @Column(name = "game_id")
    Integer gameId;

    @Column(name = "user_login")
    String userLogin;

    @Override
    public int hashCode() {
        return 169;
    }
}
