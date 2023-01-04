package application.repository;

import application.domain.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumCommentRepository extends JpaRepository<ForumComment, Integer> {
    List<ForumComment> getAllByForum(int forumId);
}
