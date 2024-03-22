package com.ase.licenta.course_app.controller;

import com.ase.licenta.course_app.dto.CourseDto;
import com.ase.licenta.course_app.model.Course;
import com.ase.licenta.course_app.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

//    @PutMapping
//    @ResponseStatus(HttpStatus.OK)
//    public CourseDto editCourseMetadata(@RequestBody CourseDto courseDto){
//        return courseService.editCourse(courseDto);
//    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Course> getCourses(){
        return courseService.getCourses();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course addCourse(@RequestBody Course course){
        return courseService.saveCourse(course);
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam("file") MultipartFile file,@RequestParam("courseId")String courseId){
        return courseService.uploadThumbnail(file, courseId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Course updateCourse(@RequestBody Course course,@RequestParam("courseId") String courseId){

        course.setId(courseId);

        return courseService.updateCourse(course);
    }

}

