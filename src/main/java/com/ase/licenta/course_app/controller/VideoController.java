package com.ase.licenta.course_app.controller;

import com.ase.licenta.course_app.model.Video;
import com.ase.licenta.course_app.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Video> getVideos() {
        return videoService.getVideos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadVideo(@RequestParam("file") MultipartFile file){

        return videoService.uploadVideo(file);
    }

}
