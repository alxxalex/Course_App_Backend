package com.ase.licenta.course_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "Course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    private String id;
    private String author;
    private String title;
    private String description;
    private Integer likes;
    private Integer dislikes;
    private Integer enrolmentCount;
    private String thumbnailUrl;
    private List<Comment> commentList;
    private List<Chapter> chapterList;
}