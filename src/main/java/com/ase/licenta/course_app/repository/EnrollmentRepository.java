package com.ase.licenta.course_app.repository;

import com.ase.licenta.course_app.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnrollmentRepository extends MongoRepository<Enrollment,String> {
}
