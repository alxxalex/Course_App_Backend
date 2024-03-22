package com.ase.licenta.course_app.service;

import com.ase.licenta.course_app.dto.CourseDto;
import com.ase.licenta.course_app.model.Course;
import com.ase.licenta.course_app.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.IconUIResource;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final S3Service s3Service;

    public Course updateCourse(Course course) {

        return courseRepository.save(course);

    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public String uploadThumbnail(MultipartFile file, String courseId) {
        Course savedCourse = getCourseById(courseId);

        String thumbnailUrl = s3Service.uploadFile(file);

        savedCourse.setThumbnailUrl(thumbnailUrl);

        courseRepository.save(savedCourse);

        return thumbnailUrl;
    }

    Course getCourseById(String courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(()-> new IllegalArgumentException("Cannot find course by id - " + courseId));
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
}
