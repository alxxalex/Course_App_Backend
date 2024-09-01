package com.ase.licenta.course_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
public class AuxiliaryFile {
    private String name;
    private String auxiliaryFileUrl;
}
