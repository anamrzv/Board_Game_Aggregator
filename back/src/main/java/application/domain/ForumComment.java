package application.domain;

import com.sun.istack.NotNull;
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
    @NotNull
    private Integer forum;
    @Column(name = "user_login")
    @NotNull
    private String user;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private String content;

    public ForumComment(Integer forum, String user, LocalDateTime date, String content) {
        this.content = content;
        this.date = date;
        this.forum = forum;
        this.user = user;
    }
}
