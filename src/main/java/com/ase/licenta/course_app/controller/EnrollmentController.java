package com.ase.licenta.course_app.controller;

import com.ase.licenta.course_app.model.Enrollment;
import com.ase.licenta.course_app.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;

    @PostMapping
    public Enrollment addEnrollment(@RequestBody Enrollment enrollment){
        return enrollmentRepository.save(enrollment);
    }
}
