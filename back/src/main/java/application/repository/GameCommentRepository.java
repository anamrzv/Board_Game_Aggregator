package application.repository;

import application.domain.GameComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameCommentRepository extends JpaRepository<GameComment, Integer> {
    List<GameComment> getAllByGameId(Integer gameId);
}
