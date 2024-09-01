package com.ase.licenta.course_app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Rating {

    @Id
    private String email;
    private int starCount;
}
