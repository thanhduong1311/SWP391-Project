package com.app.task.controller;

import com.app.task.dto.media.request.UploadFileRequest;
import com.app.task.dto.media.response.UploadFileResponse;
import com.app.task.enums.ResponseCode;
import com.app.task.exception.ApiException;
import com.app.task.service.MediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<UploadFileResponse> uploadFile(@Valid @ModelAttribute UploadFileRequest request) {
        String mediaUrl = mediaService.uploadFile(request.getFile());
        if (mediaUrl == null || mediaUrl.isEmpty()) {
            throw new ApiException(ResponseCode.FILE_ERROR_UPLOAD_FAILED);
        }
        return ResponseEntity.ok(new UploadFileResponse(mediaUrl));
    }
}
