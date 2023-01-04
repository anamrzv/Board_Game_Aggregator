package application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "topic_comment")
public class ForumComment {
    @Id
    private Integer id;
    @OneToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime date;
    private String content;
}
