package com.derrick.big_event.controller;

import com.derrick.big_event.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/api/v1/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        // Save file to local disk
        String fileName = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        file.transferTo(new File("files\\" + fileName));

        return Result.success(fileName);
    }
}
