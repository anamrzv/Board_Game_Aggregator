package application.controller;

import application.domain.Forum;
import application.domain.ForumComment;
import application.pojo.ForumResponse;
import application.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class ForumController {
    @Autowired
    private ForumService forumService;

    /**
     * @return список всех форумов
     */
    @GetMapping(value = "/forums",
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
    @GetMapping(value = "/forums/{forum_id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    private ResponseEntity<ForumResponse> showGameById(@PathVariable(value = "forum_id") int forumId) {
        Forum forum = forumService.findById(forumId);
        if (forum != null) {
            List<ForumComment> comments = forumService.findAllForumComments(forumId);
            ForumResponse response = new ForumResponse(forum, comments);
            return ResponseEntity.ok().body(response);
        } else return new ResponseEntity("Проблемы на нашей стороне, попробуйте зайти позже", HttpStatus.NOT_FOUND);
    }
}
