package com.ase.licenta.course_app.service;

import com.ase.licenta.course_app.dto.CourseDto;
import com.ase.licenta.course_app.model.AuxiliaryFile;
import com.ase.licenta.course_app.model.Course;
import com.ase.licenta.course_app.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public Course getCourseById(String courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(()-> new IllegalArgumentException("Cannot find course by id - " + courseId));
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByAuthor(String author){
        return courseRepository.findByAuthor(author);
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    public String uploaAuxiliaryFile(MultipartFile file, String courseId) {
        Course savedCourse = getCourseById(courseId);

        String auxiliaryFileUrl = s3Service.uploadFile(file);

        savedCourse.addAuxiliaryFileUrl(new AuxiliaryFile(file.getOriginalFilename(),auxiliaryFileUrl));

        courseRepository.save(savedCourse);

        return auxiliaryFileUrl;
    }

    public List<Course> getCoursesContaining(String title){
        return courseRepository.findByTitleContaining(title);
    }

    public Page<Course> filterCourses(Pageable pageable, String title,String jobTitle) {
        List<Course> courses = courseRepository.findAll();
        Collections.reverse(courses);

        List<Course> filteredByTitle = filterByTitle(courses,title);
        List<Course> filteredByJobTitleyAndTitle = filterByJobTitle(filteredByTitle,jobTitle);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredByJobTitleyAndTitle.size());
        List<Course> pagedCampaigns;

        if (start > filteredByJobTitleyAndTitle.size()) {
            pagedCampaigns = new ArrayList<>();
        } else {
            pagedCampaigns = filteredByJobTitleyAndTitle.subList(start, end);
        }

        return new PageImpl<>(pagedCampaigns, pageable, filteredByJobTitleyAndTitle.size());

    }

    private List<Course> filterByTitle(List<Course> courses, String title) {
        return courses.stream()
                .filter(course -> {
                    if (title != null && !title.trim().isEmpty()) {
                        return course.getTitle().toLowerCase().contains(title.toLowerCase().trim());

                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    private List<Course> filterByJobTitle(List<Course> courses, String jobTitle) {
        return courses.stream()
                .filter(course -> {
                    if (jobTitle != null && !jobTitle.trim().isEmpty()) {
                        return course.getJobTitle().contains(jobTitle);
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
