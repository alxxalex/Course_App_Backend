package com.ase.licenta.course_app.repository;


import com.ase.licenta.course_app.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course,String> {
}
