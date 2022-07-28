package com.example.serviceoss.controller;

import com.example.commonutils.R;
import com.example.serviceoss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//@CrossOrigin
@RestController
@RequestMapping("/serviceoss/file")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @PostMapping("upload")
    public R upload(@RequestParam("file") MultipartFile file) {
        String uploadUrl = fileService.upload(file);
        return R.ok().message("文件上传成功").data("url", uploadUrl);
    }
}
