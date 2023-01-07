package application.pojo;

import lombok.Data;

import java.util.List;

@Data
public class GameRequest {
    private String name;
    private String image;
    private String video;
    private String description;
    private int minPlayersNumber;
    private int maxPlayersNumber;
    private int recPlayersNumber;
    private int minPlayTime;
    private int maxPlayTime;
    private int publishYear;
    private String designers;
    private int minPlayAge;
    private int maxPlayAge;
    private Float difficultness;
    private int weight;

    private String genre;
    private String mechanics;
    private String publisher;

    private List<String> themes;
}
