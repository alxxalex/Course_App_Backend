package com.ase.licenta.course_app.controller;

import com.ase.licenta.course_app.model.Enrollment;
import com.ase.licenta.course_app.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Enrollment getEnrollmentById(@RequestParam("email") String email){
        return enrollmentRepository.findById(email).orElse(null);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Enrollment addEnrollment(@RequestParam("email")String email,@RequestParam("courseId")String id){

        if(enrollmentRepository.findById(email).isPresent()){
            Enrollment enrollment = enrollmentRepository.findById(email).get();
            if(!enrollment.getCourses().contains(id)) {
                enrollment.getCourses().add(id);
            }else{
                return null;
            }

            return enrollmentRepository.save(enrollment);
        }else{
            List<String> courses = new ArrayList<>();
            courses.add(id);
            Enrollment enrollment = new Enrollment(email,courses);

            return enrollmentRepository.save(enrollment);
        }

    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCourseFromEnrollment(@RequestParam("email")String email,@RequestParam("courseId")String id){
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        for(Enrollment enrollment: enrollments){
            enrollment.getCourses().remove(id);
            enrollmentRepository.save(enrollment);
        }
    }
}
