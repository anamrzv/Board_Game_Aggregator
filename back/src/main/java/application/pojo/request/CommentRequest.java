package application.pojo.request;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequest {
    private String login;
    private String content;
    private LocalDateTime dateTime;
}
