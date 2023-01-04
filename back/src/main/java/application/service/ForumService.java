package application.service;

import application.domain.Forum;
import application.domain.ForumComment;
import application.domain.Game;
import application.domain.GameComment;
import application.repository.ForumCommentRepository;
import application.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ForumCommentRepository forumCommentRepository;

    @Transactional
    public List<Forum> findAll() {
        return forumRepository.findAll();
    }

    @Transactional
    public Forum findById(int forumId) { return forumRepository.findById(forumId).orElse(null); }

    @Transactional
    public List<ForumComment> findAllForumComments(int forumId) { return forumCommentRepository.getAllByForum(forumId); }
}
