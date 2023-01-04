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
    @Column(name = "forum_id")
    private Integer forum;
    @Column(name = "user_login")
    private String user;
    private LocalDateTime date;
    private String content;
}
