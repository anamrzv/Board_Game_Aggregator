package application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "forum_topic")
public class Forum {
    @Id
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "favouriteForums", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> usersWhoLiked = new HashSet<>();
}
