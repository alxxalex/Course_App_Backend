package com.ase.licenta.course_app.service;

import com.ase.licenta.course_app.model.User;
import com.ase.licenta.course_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserByEmail(String email){
        return userRepository.findById(email).orElse(null);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public User updateUser(String email, MultipartFile file) {
        User user = userRepository.findById(email).get();
        String profilePictureUrl = s3Service.uploadFile(file);
        user.setProfilePictureUrl(profilePictureUrl);

        return userRepository.save(user);
    }
}
