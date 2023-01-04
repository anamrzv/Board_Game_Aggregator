package application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements GrantedAuthority {
    @Id
    private Integer id;
    private String name;

    public UserRole(Integer id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
