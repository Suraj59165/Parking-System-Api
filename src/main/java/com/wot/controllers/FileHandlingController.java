package com.wot.controllers;

import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.wot.services.FileHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "*")
public class FileHandlingController {

    @Autowired
    private FileHandlingService fileHandlingService;


    @GetMapping("/download/{filename}")
    public ResponseEntity<ByteArrayResource> downloadClientFiles(@PathVariable(value = "filename") String fileName) throws IOException {
       byte[] bytes= fileHandlingService.downloadFile(fileName);
       ByteArrayResource byteArrayResource=new ByteArrayResource(bytes);
       return  ResponseEntity.ok()
               .contentLength(bytes.length)
               .header("content-type","application/octet-stream")
               .header("content-disposition","attachment; filename=\"" +fileName+ "\"")
               .body(byteArrayResource);
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable(value = "filename") String filename)
    {
        return new ResponseEntity<>(fileHandlingService.deleteFile(filename), HttpStatus.OK);

    }



}
