package com.sb.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.web.service.AmazonServiceClient;

@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

	@Autowired
    private AmazonServiceClient amazonServiceClient;	 
	   
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam(value="prefix")String prefix, @RequestPart(value = "file") MultipartFile file) {
        return this.amazonServiceClient.uploadFile(prefix, file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonServiceClient.deleteFileFromS3Bucket(fileUrl);
    }
}
