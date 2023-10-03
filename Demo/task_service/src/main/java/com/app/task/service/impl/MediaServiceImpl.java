package com.app.task.service.impl;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.app.task.config.AwsConfig;
import com.app.task.service.MediaService;
import com.app.task.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {
    private final AwsConfig awsConfig;

    @Override
    public String uploadFile(MultipartFile file) {
        File f = null;
        try {
            f = FileUtils.convertMultipartFileToFile(file);
            String fileName = FileUtils.generateUniqueFileName(file);
            uploadFile(fileName, f);
            return getObjectUrl(fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (f != null) {
                f.delete();
            }
        }
        return null;
    }

    private void uploadFile(String fileName, File file) {
        awsConfig.getS3Client().putObject(new PutObjectRequest(awsConfig.bucketName, fileName, file));
    }

    public String getObjectUrl(String fileName) {
        return "https://" + awsConfig.bucketName + ".s3." + awsConfig.region + ".amazonaws.com/" + fileName;
    }
}
