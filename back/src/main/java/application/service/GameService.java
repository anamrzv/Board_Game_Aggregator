package application.service;

import application.domain.*;
import application.repository.*;
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

    @Autowired
    private GameThemeRepository gameThemeRepository;

    @Autowired
    private GameGenreRepository gameGenreRepository;

    @Autowired
    private GameMechanicsRepository gameMechanicsRepository;

    @Autowired
    private GamePublisherRepository gamePublisherRepository;

    @Transactional
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Transactional
    public List<Game> findAll(Specification<Game> spec) {
        return gameRepository.findAll(spec);
    }

    @Transactional
    public Game findById(int gameId) {
        return gameRepository.findById(gameId).orElse(null);
    }

    @Transactional
    public List<GameComment> findAllGameComments(int gameId) {
        return gameCommentRepository.getAllByGameId(gameId);
    }

    @Transactional
    public void addComment(GameComment comment) {
        gameCommentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(int commentId) {
        gameCommentRepository.deleteById(commentId);
    }

    @Transactional
    public void addGame(Game game) {
        gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }

    @Transactional
    public GameTheme getThemeByName(String name) {
        return gameThemeRepository.getGameThemeByName(name);
    }

    @Transactional
    public GameGenre getGenreByName(String name) {
        return gameGenreRepository.getGameGenreByName(name);
    }

    @Transactional
    public GameMechanics getMechanicsByName(String name) {
        return gameMechanicsRepository.getGameMechanicsByName(name);
    }

    @Transactional
    public GamePublisher getPublisherByName(String name) {
        return gamePublisherRepository.getGamePublisherByName(name);
    }

//    @Transactional
//    public int getGameCount() {
//        return gameRepository.getGameCount();
//    }
}
