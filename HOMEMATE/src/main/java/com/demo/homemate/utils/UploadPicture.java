package com.demo.homemate.utils;

import com.demo.homemate.dtos.image.ImageResponse;
import com.demo.homemate.dtos.notification.MessageObject;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadPicture {

    public ImageResponse uploadImage(MultipartFile multipartFile, String folderName) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (filename==null||filename.isEmpty()){
            return new ImageResponse("Error", new MessageObject("Error","There is an error in upload process!",null));

        }
        String uploadDir = "src/main/resources/static/assets/images/"+folderName;
        String url=folderName+"\\"+filename;
        String avatarURL = getImageUrl(url);
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(filename);
            System.out.println("filePath:"+filePath.toFile().getAbsolutePath());
            if(Files.exists(filePath)){
                return new ImageResponse(avatarURL, new MessageObject("Failed","This image is already exist in this folder !",null));
            }else{
                Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
             return new ImageResponse(avatarURL, new MessageObject("Success","Upload image successfully!",null));
            }

        }catch(IOException e){
         return new ImageResponse(null, new MessageObject("Error","There is an error in upload process!",null));
        }
    }
    public String getImageUrl(String url){
        if (url==null){
            return "default";
        }
        String uploadDir = "/assets/images/";
        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(url);
        String newString = StringUtils.replace(filePath.toFile().getPath(), "\\", "/");
        return newString;
    }
}
