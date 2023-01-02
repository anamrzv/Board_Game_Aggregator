package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BoardGameApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoardGameApplication.class, args);
    }
}