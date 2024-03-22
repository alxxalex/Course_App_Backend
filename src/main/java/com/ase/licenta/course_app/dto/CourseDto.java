package com.ase.licenta.course_app.dto;

import com.ase.licenta.course_app.model.Comment;
import com.ase.licenta.course_app.model.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private String id;
    private String title;
    private String description;
    private Integer likes;
    private Integer dislikes;
    private Integer enrolmentCount;
    private String thumbnailUrl;
    private List<Comment> commentList;
    private List<Video> videoList;
}
