package application.service;

import application.domain.Game;
import application.domain.GameComment;
import application.repository.GameCommentRepository;
import application.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameCommentRepository gameCommentRepository;

    @Transactional
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Transactional
    public List<Game> findAll(Specification<Game> spec) {
        return gameRepository.findAll(spec);
    }

    @Transactional
    public Game findById(int gameId) { return gameRepository.findById(gameId).orElse(null); }

    @Transactional
    public List<GameComment> findAllGameComments(int gameId) { return gameCommentRepository.getAllByGameId(gameId); }
}
