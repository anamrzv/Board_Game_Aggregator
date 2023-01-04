package application.repository;

import application.domain.GameComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameCommentRepository extends JpaRepository<GameComment, Integer> {
    List<GameComment> getAllByGameId(Integer gameId);
}
