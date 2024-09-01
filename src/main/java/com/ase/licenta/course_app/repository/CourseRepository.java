package com.ase.licenta.course_app.repository;


import com.ase.licenta.course_app.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course,String> {
    List<Course> findByAuthor(String author);
    List<Course> findByTitleContaining(String title);
}
