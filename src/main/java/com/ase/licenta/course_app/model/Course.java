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
    private List<String> userLikes;
    private Integer dislikes;
    private List<String> userDislikes;
    private Integer enrolmentCount;
    private String thumbnailUrl;
    private List<Comment> commentList;
    private List<Rating> ratingList;
    private float ratingMean;
    private List<Chapter> chapterList;
    private String pictureUrl;
    private List<String> jobTitle;
    private List<AuxiliaryFile> auxiliaryFiles;
    private boolean archived;

    public void addAuxiliaryFileUrl(AuxiliaryFile auxiliaryFile){
        auxiliaryFiles.add(auxiliaryFile);
    }
}
