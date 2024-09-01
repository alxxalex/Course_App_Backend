package com.ase.licenta.course_app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Comment {

    @Id
    private String id;
    private String text;
    private String author;
    private Integer likeCount;
    private Integer dislikeCount;

}
