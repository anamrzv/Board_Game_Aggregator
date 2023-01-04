package application.pojo;

import application.domain.Forum;
import application.domain.ForumComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumResponse {
    private Forum forum;
    private List<ForumComment> comments;
}
