package com.ase.licenta.course_app.repository;

import com.ase.licenta.course_app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
