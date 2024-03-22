package com.ase.licenta.course_app.service;

import com.ase.licenta.course_app.model.Video;
import com.ase.licenta.course_app.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public List<Video> getVideos() {
        return videoRepository.findAll();
    }


    public String uploadVideo(MultipartFile multipartFile) {
        String videoUrl = s3Service.uploadFile(multipartFile);

//        var video = new Video();
//        video.setVideoUrl(videoUrl);
//
//        Video savedVideo = videoRepository.save(video);

        return videoUrl;
    }

}
