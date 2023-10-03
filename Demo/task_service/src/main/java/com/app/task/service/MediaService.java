package com.app.task.service;

import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    /**
     * Upload file to storage
     * @param file
     * @return path to file
     */
    String uploadFile(MultipartFile file);


}
