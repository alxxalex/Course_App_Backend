package com.ase.licenta.course_app.repository;

import com.ase.licenta.course_app.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video,String> {
}
