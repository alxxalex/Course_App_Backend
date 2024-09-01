package com.ase.licenta.course_app.controller;

import com.ase.licenta.course_app.dto.CourseDto;
import com.ase.licenta.course_app.model.AuxiliaryFile;
import com.ase.licenta.course_app.model.Course;
import com.ase.licenta.course_app.model.Enrollment;
import com.ase.licenta.course_app.model.Rating;
import com.ase.licenta.course_app.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final RestTemplate restTemplate;


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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Course getCourse(@PathVariable("id") String id){
        return courseService.getCourseById(id);
    }


    @GetMapping("/enrolledCourses")
    public List<Course> getEnrolledCourses(@RequestParam("email") String email){
        String getEnrollmentByIdEndpoint = "http://localhost:8080/api/enrollment?email=" + email;

        ResponseEntity<Enrollment> response = restTemplate.getForEntity(getEnrollmentByIdEndpoint, Enrollment.class);

        List<Course> enrolledCourses = new ArrayList<>();

        if (response.getStatusCode() == HttpStatus.OK) {
            Enrollment enrollment = response.getBody();
            if (enrollment != null) {
                for(String courseId : enrollment.getCourses()){
                    Course course = courseService.getCourseById(courseId);
                    enrolledCourses.add(course);
                }
            } else {
                System.out.println("Response body is empty");
            }
        } else {
            System.out.println("Failed to fetch enrollment: " + response.getStatusCode());
        }

        return enrolledCourses;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Course addCourse(@RequestBody Course course){
//        System.out.println(course);
        return courseService.saveCourse(course);
    }

    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam("file") MultipartFile file,@RequestParam("courseId")String courseId){
        return courseService.uploadThumbnail(file, courseId);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Course updateCourse(@RequestBody Course course,@RequestParam("courseId") String courseId){
        course.setId(courseId);
        return courseService.updateCourse(course);
    }

    @PutMapping("/rating")
    @ResponseStatus(HttpStatus.OK)
    public Course updateCourseRating(@RequestBody Course course,@RequestParam("courseId") String courseId){
        course.setId(courseId);
        List<Rating> ratings = course.getRatingList();
        if(ratings.size() == 1){
            course.setRatingMean(ratings.get(0).getStarCount());
        }else{
            float mean = 0;
            for(Rating rating : ratings){
                mean += rating.getStarCount();
            }
            mean = mean / ratings.size();

            course.setRatingMean(mean);
        }
        return courseService.updateCourse(course);
    }

    @GetMapping("/byAuthor")
    @ResponseStatus(HttpStatus.OK)
    public List<Course> getCoursesByAuthor(@RequestParam("author") String author){
        return courseService.getCoursesByAuthor(author);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCourse(@RequestParam("email")String email,@PathVariable("id")String id){
        String url = "http://localhost:8080/api/enrollment/delete?email=" + email + "&courseId=" + id;

        restTemplate.delete(url);

        courseService.deleteCourse(id);
    }


    @PostMapping("/auxiliaryFile")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAuxiliaryFile(@RequestParam("auxiliaryFile") MultipartFile file,@RequestParam("courseId")String courseId){
        System.out.println(file);
        System.out.println(file.getOriginalFilename());
        System.out.println(courseId);
        return courseService.uploaAuxiliaryFile(file, courseId);
    }

    @GetMapping("/containing")
    @ResponseStatus(HttpStatus.OK)
    public List<Course> getCoursesContaining(@RequestParam("title") String title){
        return courseService.getCoursesContaining(title);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Course>> filterCampaigns(Pageable pageable, @RequestParam("title") String title, @RequestParam("jobTitle") String jobTitle) {
        System.out.println(pageable);
        Page<Course> courses = courseService.filterCourses(pageable,title,jobTitle);
        System.out.println(courses);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
}

