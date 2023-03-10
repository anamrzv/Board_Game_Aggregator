package application.controller;

import application.domain.Forum;
import application.domain.ForumComment;
import application.domain.User;
import application.pojo.request.CommentRequest;
import application.pojo.request.ForumRequest;
import application.pojo.request.NameRequest;
import application.pojo.response.ForumResponse;
import application.service.ForumService;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game_aggregator/forum")
@CrossOrigin(origins = "*")
public class ForumController {
    @Autowired
    private ForumService forumService;
    @Autowired
    private UserService userService;

    /**
     * @return список всех форумов
     */
    @GetMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<List<Forum>> showAllForums() {
        List<Forum> forums;
        forums = forumService.findAll();
        if (forums != null) {
            return ResponseEntity.ok().body(forums);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NO_CONTENT);
    }

    /**
     * @param forumId
     * @return информацию о форуме и все комментарии в нем
     */
    @GetMapping(value = "/{forum_id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<ForumResponse> showForumById(@PathVariable(value = "forum_id") int forumId) {
        Forum forum = forumService.findById(forumId);
        if (forum != null) {
            List<ForumComment> comments = forumService.findAllForumComments(forumId);
            ForumResponse response = new ForumResponse(forum, comments);
            return ResponseEntity.ok().body(response);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/{forum_id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> addComment(@PathVariable(value = "forum_id") int forumId, @RequestBody CommentRequest comment) {
        try {
            ForumComment forumComment = new ForumComment(forumId, comment.getLogin(), java.time.LocalDateTime.now(), comment.getContent());
            forumService.saveForumComment(forumComment);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> addForum(@RequestBody NameRequest nameRequest) {
        if (nameRequest.getName() != null) {
            Forum forum = new Forum(nameRequest.getName());
            forumService.saveForum(forum);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{forum_id}/{comment_id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> deleteComment(@PathVariable(value = "forum_id") int forumId, @PathVariable(value = "comment_id") int commentId) {
        forumService.deleteCommentById(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> deleteForum(@PathVariable(value = "forum_id") int forumId) {
        forumService.deleteForumById(forumId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/fav_forums",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<HttpStatus> addFavForum(@RequestBody ForumRequest forumRequest) {
        Forum forum = forumService.findById(forumRequest.getForum());
        User user = userService.findByLogin(forumRequest.getLogin());
        if (user != null && forum != null) {
            user.addForumToFav(forum);
            userService.updateUser(user);
            forumService.saveForum(forum);
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
