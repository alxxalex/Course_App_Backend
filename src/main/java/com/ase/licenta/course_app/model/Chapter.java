package com.ase.licenta.course_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "Chapter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

    private String title;
    private List<Video> videoList;
}
